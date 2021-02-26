package com.oksanapiekna.atelieshop.serviceImpl;

import com.oksanapiekna.atelieshop.entity.ViberToken;
import com.oksanapiekna.atelieshop.jpa.ViberTokenJPA;
import com.oksanapiekna.atelieshop.service.ViberTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class ViberTokenServiceImpl implements ViberTokenService {
    private ViberTokenJPA tokenJPA;
    @Override
    public UUID getToken() {
        ViberToken viberToken = tokenJPA.findById(1).orElse(null);
        if(viberToken==null){
            return generateNewToken();
        }
        return viberToken.getUuid();
    }

    @Override
    public UUID generateNewToken() {
        ViberToken viberToken = new ViberToken(1,UUID.randomUUID());
        tokenJPA.save(viberToken);
        return viberToken.getUuid();
    }
}
