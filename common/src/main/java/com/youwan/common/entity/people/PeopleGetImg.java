package com.youwan.common.entity.people;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "we_PeopleGetImg", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class PeopleGetImg implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String deviceKey;//设备唯一标识码
    private String personId;//  人员 id
    private String time;//  时间戳
    private String imgPath;//  照片本地路径
    private String faceId;// 照片 id
    private String ip;// 设备 IP 地址

}
