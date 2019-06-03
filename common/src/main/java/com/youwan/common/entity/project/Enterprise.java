package com.youwan.common.entity.project;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "we_enterprise", uniqueConstraints = @UniqueConstraint(columnNames = "corpCode"))
//@JsonIgnoreProperties(value = {"id"}, ignoreUnknown = true)
public class Enterprise implements Serializable {
    // 主键ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // 企业统一社会信用代码
    private String corpCode;
    // 企业名称
    private String corpName;
    // 企业注册地区编码
    private String areaCode;
    // 企业营业地址
    private String address;
    // 注册时间
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JSONField(format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date registerDate;
    // 联系人姓名
    private String linkman;
    // 联系人办公电话
    private String linkTel;
    // 省
    private String sheng;
    // 市
    private String shi;
    // 区
    private String qu;
}
