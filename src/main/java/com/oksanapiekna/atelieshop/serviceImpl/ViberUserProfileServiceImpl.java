package com.oksanapiekna.atelieshop.serviceImpl;

import com.oksanapiekna.atelieshop.entity.ViberUserProfile;
import com.oksanapiekna.atelieshop.jpa.ViberUserProfileJPA;
import com.oksanapiekna.atelieshop.service.ViberUserProfileService;
import com.viber.bot.api.MessageDestination;
import com.viber.bot.profile.UserProfile;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ViberUserProfileServiceImpl implements ViberUserProfileService {
    private ViberUserProfileJPA profileJPA;

    @Override
    public ViberUserProfile save(ViberUserProfile viberUserProfile) {
        return profileJPA.save(viberUserProfile);
    }

    @Override
    public ViberUserProfile save(UserProfile user,String chatId) {
        ViberUserProfile userProfile =  new ViberUserProfile();
        userProfile.setId(user.getId());
        userProfile.setCountry(user.getCountry());
        userProfile.setLanguage(user.getLanguage());
        userProfile.setApiVersion(user.getApiVersion());
        userProfile.setName(user.getName());
        userProfile.setAvatar(user.getAvatar());

        userProfile.setChatId(chatId);

        return profileJPA.save(userProfile);
    }

    public MessageDestination convertUser(ViberUserProfile user)  {
        try {
            Class userProfileClass = null;
            UserProfile userProfile = null;
            userProfileClass = Class.forName("com.viber.bot.profile.UserProfile");
            Constructor<UserProfile> constructor = userProfileClass.getDeclaredConstructor(String.class, String.class, String.class, Integer.class, String.class, String.class);
            constructor.setAccessible(true);
            userProfile = constructor.newInstance(user.getId(), user.getCountry(), user.getLanguage(),
                    user.getApiVersion(), user.getName(), user.getAvatar());

            return new MessageDestination(userProfile);
        }catch (Exception e){
            return null;
        }
    }



    @Override
    public ViberUserProfile findById(String id) {
        return profileJPA.findById(id).orElse(null);
    }

    @Override
    public List<ViberUserProfile> findAll() {
        return profileJPA.findAll();
    }

    @Override
    public List<MessageDestination> findAllAndConvert() {
        return findAll().stream()
                .map(this::convertUser)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteByID(String id) {
        profileJPA.deleteById(id);
    }
}
