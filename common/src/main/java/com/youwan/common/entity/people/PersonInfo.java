package com.youwan.common.entity.people;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "we_Person_Info", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class PersonInfo implements Serializable {
    // 主键ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String pid;//人员id实为id
    private String name;//姓名，识别通过后会在屏幕上显示该名字
    private String idcardNum;//IC卡卡号，注册时可以不填；若填写了IC卡号，可直接刷对应卡号的卡进行识别，也显示与该卡号对应的人员的名字


    // 班组编号
    private Integer teamSysNo;
    // 工人姓名
    private String workerName;
    // 是否为组长
    private Integer isTeamLeader;
    // 证件类型。
    private String idCardType;
    // 证件号码
    private String idCardNumber;
    //当前工种
    private String workType;
    // 工人类型
    private Integer workRole;
    // 发卡时间。格式 yyyy-MM-dd
    private String issueCardDate;
    // 办卡采集相片。
    private String issueCardPic;
    // 考勤卡号
    private String cardNumber;
    // 发放工资银行卡号。AES
    private String payRollBankCardNumber;
    // 发放工资银行名称
    private String payRollBankName;
    // 发放工资卡银行联号。
    private String bankLinkNumber;
    // 发放工资卡银行
    private String payRollTopBankCode;
    // 是否购买工伤或意外伤害保险 。
    private Integer hasBuyInsurance;
    //民族
    private String nation;
    // 住址
    private String address;
    // 头像
    private String headImage;
    // 政治面貌
    private String politicsType;
    // 加入工会时间
    private String joinedTime;
    // 手机号码
    private String cellPhone;
    // 文化程度
    private String cultureLevelType;
    // 特长
    private String Specialty;
    // 是否有重大病史
    private Integer hasBadMedicalHistory;
    // 紧急联系人姓名
    private String urgentLinkMan;
    // 紧急联系方式
    private String urgentLinkManPhone;
    // 开始工作日期。格式：    yyyy-MM-dd
    private String workDate;
    // 婚姻状况
    private String maritalStatus;
    // 发证机关
    private String grantOrg;
    // 正面照。
    private String positiveIDCardImage;
    // 反面照
    private String negativeIDCardImage;
    // 证件有效期开始日期。格式    yyyy-MM-dd
    private String startDate;
    // 证件有效期结束日期。格式    yyyy-MM-dd
    private String expiryDate;
}
