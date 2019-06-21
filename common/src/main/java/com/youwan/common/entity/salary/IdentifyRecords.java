package com.youwan.common.entity.salary;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "we_IdentifyRecords")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"id"}, ignoreUnknown = true)
public class IdentifyRecords implements Serializable {
    // 主键ID
    @Id
    @Column(name = "WE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ids;
    private String id;//识别记录 id，由设备自动生成并分配
    private String personId;//人员 id，陌生人/识别失败显示 STRANGERBABY
    private String path;//现场照保存路径，保存在设备中，当设备内存储现场照满 3G 时，会自动删
    private Integer state;//回调结果，0：回调失败，1：回调成功或未设置回调地址
    private long time;//识别出人员的时间，Unix 时间戳
    private Integer type;//识别出的人员类型，0：时间段内，1：时间段外，2：陌生人/识别失败

}
