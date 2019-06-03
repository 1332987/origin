package com.youwan.common.entity.device;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author 小懒虫
 * @date 2019/05/05
 */
@Entity
@Data
@Table(name = "we_Management", uniqueConstraints = @UniqueConstraint(columnNames = "ip"))
@EntityListeners(AuditingEntityListener.class)
public class Management implements Serializable {
    // 主键ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String ip;
    private String sn;
    private String pas;
    private String type;
    private String model;
}