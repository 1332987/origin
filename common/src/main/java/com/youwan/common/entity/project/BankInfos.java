package com.youwan.common.entity.project;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author 小懒虫
 * @date 2019/05/05
 */
@Entity
@Getter
@Setter
@Table(name = "we_bankInfos", uniqueConstraints = @UniqueConstraint(columnNames = "bankNumber"))
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"id"}, ignoreUnknown = true)
public class BankInfos implements Serializable {
    // 主键ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 银行代码。参考银行代码字典表
    private String bankCode;
    // 银行支行名称
    private String bankName;
    // 银行卡号。AES
    private String bankNumber;
    // 银行联号
    private String bankLinkNumber;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
//可选属性optional=false,表示author不能为空。删除文章，不影响用户
    @JoinColumn(name = "project_id")//设置在article表中的关联字段(外键)
    private ProjectUnit projectUnit;

}