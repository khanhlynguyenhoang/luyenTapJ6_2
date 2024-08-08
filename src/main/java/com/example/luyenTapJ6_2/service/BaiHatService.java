package com.example.luyenTapJ6_2.service;

import com.example.luyenTapJ6_2.entity.BaiHat;
import com.example.luyenTapJ6_2.entity.CaSi;
import com.example.luyenTapJ6_2.request.BaiHatRequest;
import com.example.luyenTapJ6_2.response.BaiHatResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BaiHatService {

    List<BaiHatResponse> getAll();
    Page<BaiHatResponse> phanTrang(Pageable pageable);
    BaiHatRequest add(BaiHatRequest baiHatRequest);
    void deleted(Integer id);
    List<BaiHatResponse> search(String keyWord,Float donGiaMin , Float donGiaMax);
    List<BaiHatResponse> timBaiHatDaiNhat();
    Map<CaSi, Integer> groupByCaSiAndTongThoiGian();
}
