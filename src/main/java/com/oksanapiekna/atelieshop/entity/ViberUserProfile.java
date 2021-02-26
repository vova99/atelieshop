package com.oksanapiekna.atelieshop.entity;

import com.viber.bot.profile.UserProfile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;
import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViberUserProfile {
    @Id
    private String id;
    private  String country;
    private  String language;
    private  Integer apiVersion;

    private  String name;
    private  String avatar;

    private String chatId;

}
