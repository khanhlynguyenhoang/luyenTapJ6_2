package com.example.luyenTapJ6_2.repository;

import com.example.luyenTapJ6_2.entity.CaSi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaSiRepository extends JpaRepository<CaSi,Integer> {
}
