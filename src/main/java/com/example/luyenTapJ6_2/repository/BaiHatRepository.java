package com.example.luyenTapJ6_2.repository;

import com.example.luyenTapJ6_2.entity.BaiHat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaiHatRepository  extends JpaRepository<BaiHat,Integer> {

    @Query("select bh from BaiHat bh join fetch bh.caSi")
    List<BaiHat> getAll();

    @Query("select bh from BaiHat bh join fetch bh.caSi")
    Page<BaiHat> phanTrang(Pageable pageable);

    @Query("select bh from BaiHat bh join fetch bh.caSi " +
            "where (:donGiaMin is null or :donGiaMax is null or bh.gia between  :donGiaMin and :donGiaMax) " +
            "and (:keyWord is null or bh.tenBaiHat = :keyWord)")
    List<BaiHat> searchGia(@Param("keyWord") String keyWord ,
                           @Param("donGiaMin") Float donGiaMin,
                           @Param("donGiaMax") Float donGiaMax);

}
