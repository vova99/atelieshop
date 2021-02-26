package com.oksanapiekna.atelieshop.jpa;

import com.oksanapiekna.atelieshop.entity.ViberUserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViberUserProfileJPA extends JpaRepository<ViberUserProfile,String> {
}
