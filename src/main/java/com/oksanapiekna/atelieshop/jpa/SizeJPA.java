package com.oksanapiekna.atelieshop.jpa;


import com.oksanapiekna.atelieshop.entity.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SizeJPA extends JpaRepository<Size,Integer> {
}
