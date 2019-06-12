package com.youwan.common.entity.people;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author 小懒虫
 * @date 2019/05/13
 */
@Data
@Entity
@Table(name = "we_projectTeam")
public class ProjectTeam implements Serializable {
    // 主键ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // 班组名称，同一个项目和参建单位下面不能重复
    private String teamName;
    // 班组所在企业 负责人姓名
    private String responsiblePersonName;
    // 责任人联系电话
    private String responsiblePersonPhone;
    // 责任人证件类型。
    private String responsiblePersonIDCardType;
    // 责任人证件号码。AES
    private String responsiblePersonIDNumber;
    // 备注
    private String remark;
    // 进场日期，yyyy-MM-dd
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date entryTime;
    // 退场日期，yyyy-MM-dd
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date exitTime;
    // 进场附件，有进场日期时，
    private String entryAttachments;
    // 退场附件，有退场日期时，
    private String exitAttachments;
}