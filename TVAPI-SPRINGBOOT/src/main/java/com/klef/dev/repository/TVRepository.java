package com.klef.dev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.klef.dev.model.TV;

import java.util.List;

@Repository
public interface TVRepository extends JpaRepository<TV, Integer> {
    List<TV> findByBrand(String brand);
    List<TV> findBySmart(String smart);
}
