package com.youwan.common.entity.project;


import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author 小懒虫
 * @date 2019/05/06
 */
@Entity
@Data
@Table(name = "we_ProjectUnit")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"id"}, ignoreUnknown = true)
public class ProjectUnit implements Serializable {
    // 主键ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // 创建时间

    // 项目编码
    private String projectCode;
    // 统一社会信用代码
    private String corpCode;
    // 企业名称
    private String corpName;
    // 参建类型。参考参建单位类型字典 表
    private String corpType;
    // 进场时间。格式 yyyy-MM-dd HH:mm:ss
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date entryTime;
    // 退场时间。格式 yyyy-MM-dd HH:mm:ss
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date exitTime;
    // 发放工资的银行。JSON 数组
    @OneToMany(mappedBy = "projectUnit", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // 级联保存、更新、删除、刷新;延迟加载。当删除用户，会级联删除该用户的所有文章
    // 拥有mappedBy注解的实体类为关系被维护端
    // mappedBy="author"中的author是Article中的author属性
    private List<BankInfos> bankInfos;
    // 项目经理名称
    private String pmName;
    // 项目经理证件类型。参考人员证件 类型字典表
    private String pmIDCardType;
    // 项目经理证件号码。AES
    private String pmIDCardNumber;
    // 项目经理电话
    private String pmPhone;
}