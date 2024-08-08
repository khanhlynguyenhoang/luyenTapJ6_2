package com.example.luyenTapJ6_2.controller;

import com.example.luyenTapJ6_2.entity.CaSi;
import com.example.luyenTapJ6_2.request.BaiHatRequest;
import com.example.luyenTapJ6_2.response.BaiHatResponse;
import com.example.luyenTapJ6_2.service.BaiHatService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("bai-hat")
public class BaiHatController {

    @Autowired
    private BaiHatService baiHatService;

    @GetMapping("hien-thi")
    public ResponseEntity<?> getAll() {
        List<BaiHatResponse> list = baiHatService.getAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("phan-trang")
    public ResponseEntity<?> phanTrang(@RequestParam(defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<BaiHatResponse> baiHatResponsePage = baiHatService.phanTrang(pageable);
        return ResponseEntity.ok(baiHatResponsePage);
    }

    @PostMapping("add")
    public ResponseEntity<?> add(@Valid @RequestBody BaiHatRequest baiHatRequest, BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.ok(list);
        }
        BaiHatRequest baiHatSave = baiHatService.add(baiHatRequest);
        return ResponseEntity.ok(baiHatSave);
    }

    @DeleteMapping("deleted/{id}")
    public ResponseEntity<?> deleted(@PathVariable("id") Integer id) {
        baiHatService.deleted(id);
        return ResponseEntity.ok("Xoa thanh cong");
    }

    @GetMapping("searchGia")
    public ResponseEntity<?> search(@RequestParam(required = false) String keyWord,
                                    @RequestParam(required = false) Float donGiaMin
            , @RequestParam(required = false) Float donGiaMax) {
        List<BaiHatResponse> list = baiHatService.search(keyWord,donGiaMin, donGiaMax);
        return ResponseEntity.ok(list);
    }

    @GetMapping("searchMax")
    public ResponseEntity<?> searchMax() {
        List<BaiHatResponse> baiHatDaiNhat = baiHatService.timBaiHatDaiNhat();
        if(baiHatDaiNhat != null){
            return ResponseEntity.ok(baiHatDaiNhat);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("groupByCaSi")
    public ResponseEntity<Map<CaSi, Integer>> groupByCaSi(){
        return ResponseEntity.ok( baiHatService.groupByCaSiAndTongThoiGian());
    }

}
