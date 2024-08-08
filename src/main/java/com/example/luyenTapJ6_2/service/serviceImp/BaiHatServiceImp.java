package com.example.luyenTapJ6_2.service.serviceImp;

import com.example.luyenTapJ6_2.entity.BaiHat;
import com.example.luyenTapJ6_2.entity.CaSi;
import com.example.luyenTapJ6_2.repository.BaiHatRepository;
import com.example.luyenTapJ6_2.repository.CaSiRepository;
import com.example.luyenTapJ6_2.request.BaiHatRequest;
import com.example.luyenTapJ6_2.response.BaiHatResponse;
import com.example.luyenTapJ6_2.service.BaiHatService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BaiHatServiceImp implements BaiHatService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BaiHatRepository baiHatRepository;

    @Autowired
    private CaSiRepository caSiRepository;

    @Override
    public List<BaiHatResponse> getAll() {
        List<BaiHat> listBaiHat = baiHatRepository.getAll();
        return listBaiHat.stream().map(baiHat -> modelMapper.map(baiHat, BaiHatResponse.class)).collect(Collectors.toList());
    }

    @Override
    public Page<BaiHatResponse> phanTrang(Pageable pageable) {
        Page<BaiHat> baiHatPage = baiHatRepository.phanTrang(pageable);
        List<BaiHatResponse> baiHatResponseList = baiHatPage.getContent().stream().map(baiHat ->
                modelMapper.map(baiHat, BaiHatResponse.class)).collect(Collectors.toList());
        return new PageImpl<>(baiHatResponseList, pageable, baiHatPage.getTotalElements());
    }

    @Override
    public BaiHatRequest add(BaiHatRequest baiHatRequest) {
//        CaSi caSi = caSiRepository.findById(baiHatRequest.getIdCaSi()).orElseThrow(()
//                -> new IllegalArgumentException("Khong tim thay id"));
        BaiHat baiHat = modelMapper.map(baiHatRequest, BaiHat.class);
//        baiHat.setCaSi(caSi);
        BaiHat baiHatSave = baiHatRepository.save(baiHat);
        return modelMapper.map(baiHatSave, BaiHatRequest.class);
    }

    @Override
    public void deleted(Integer id) {
        baiHatRepository.deleteById(id);
    }

    @Override
    public List<BaiHatResponse> search(String keyWord, Float donGiaMin, Float donGiaMax) {
        List<BaiHat> listSeacrh = baiHatRepository.searchGia(keyWord, donGiaMin, donGiaMax);
        return listSeacrh.stream().map(baiHat ->
                modelMapper.map(baiHat, BaiHatResponse.class)).collect(Collectors.toList());
    }

    @Override
    public List<BaiHatResponse> timBaiHatDaiNhat() {
        List<BaiHatResponse> list = baiHatRepository.findAll().stream()
                .filter(baiHat -> baiHat.getThoiLuong() != null || baiHat.getGia() != null)
                .map(baiHat -> modelMapper.map(baiHat, BaiHatResponse.class))
                .collect(Collectors.toList());
        // TIm gia max va thoi gian max
        Optional<Float> maxGia = list.stream().map(BaiHatResponse::getGia).max(Float::compareTo);
        Optional<Integer> maxTime = list.stream().map(BaiHatResponse::getThoiLuong).max(Integer::compareTo);
        return list.stream().filter(bh -> maxGia.isPresent() && bh.getGia().equals(maxGia.get()) ||
                maxTime.isPresent() && bh.getThoiLuong().equals(maxTime.get())
                || bh.getGia().equals(maxGia.get()) && bh.getThoiLuong().equals(maxTime.get())
        ).collect(Collectors.toList());
    }

    @Override
    public Map<CaSi, Integer> groupByCaSiAndTongThoiGian() {
        return baiHatRepository.findAll().stream()
                .filter(baiHat -> baiHat.getCaSi() != null) //Loc bai hat khong co ca si
                .collect(Collectors.groupingBy(BaiHat::getCaSi,
                        Collectors.summingInt(bh ->bh.getThoiLuong() != null ? bh.getThoiLuong() : 0)));
    }

}
