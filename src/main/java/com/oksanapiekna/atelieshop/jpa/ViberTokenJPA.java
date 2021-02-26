package com.oksanapiekna.atelieshop.jpa;

import com.oksanapiekna.atelieshop.entity.ViberToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViberTokenJPA extends JpaRepository<ViberToken,Integer> {
}
