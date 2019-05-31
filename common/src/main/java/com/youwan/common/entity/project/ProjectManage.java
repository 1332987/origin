package com.youwan.common.entity.project;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author 小懒虫
 * @date 2019/05/05
 */
@Data
@Entity
@Table(name = "we_projectManage", uniqueConstraints = @UniqueConstraint(columnNames = "projectCode"))
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"id"}, ignoreUnknown = true)
public class ProjectManage implements Serializable {
    // 主键ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // 项目编码
    private String projectCode;
    // 项目简介
    private String description;
    // 建设单位名称
    private String buildCorpName;
    // 建设单位统一社会信用代码
    private String buildCorpCode;
    // 建设用地规划许可证编号。AES
    private String buildPlanNum;
    // 建设工程规划许可证编号。AES
    private String prjPlanNum;
    // 总投资，单位：（万元）
    private Double invest;
    // 总面积，单位：平方米
    private Double buildingArea;
    // 总长度，单位：米
    private Double buildingLength;
    // 联系人姓名
    private String linkman;
    // 联系人办公电话
    private String linkPhone;
    // 项目状态。参考项目状态字典表
    private String prjStatus;
    // WGS84 经度
    private Double lat;
    // WGS84 纬度
    private Double lng;
    // 项目地点
    private String address;
    // 立项文号
    private String approvalNum;
    // 立项级别
    private String approvalLevelNum;
    // 建设规模。参考建设规模字典表
    private String prjSize;
    // 建设性质。参考建设性质分类字典
    private String propertyNum;
    // 工程用途。参考工程用途字典表
    private String functionNum;
    // 国籍或地区。参考国籍及地区字典
    private Integer nationNum;
}