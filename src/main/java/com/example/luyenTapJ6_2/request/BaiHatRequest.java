package com.example.luyenTapJ6_2.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BaiHatRequest {

    private Integer id;

    private Integer idCaSi;

    @NotBlank(message = "Khong duoc de trong")
    private String tenBaiHat;

    private String tenTacGia;

    private Integer thoiLuong;

    private Date ngaySanXuat;

    private Float gia;

    private String tenCaSi;

    private String queQuanCaSi;

    private Date ngayRaMat;

}
