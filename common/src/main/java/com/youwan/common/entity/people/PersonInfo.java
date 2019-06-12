package com.youwan.common.entity.people;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "we_Person_Info", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class PersonInfo {
    // 主键ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String pid;//人员id
    private String name;//姓名，识别通过后会在屏幕上显示该名字
    private String idcardNum;//IC卡卡号，注册时可以不填；若填写了IC卡号，可直接刷对应卡号的卡进行识别，也显示与该卡号对应的人员的名字
}
