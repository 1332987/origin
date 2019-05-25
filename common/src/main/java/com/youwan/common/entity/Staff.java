package com.youwan.common.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Staff {
    @Id
    private Long id;

    @Column
    private String dcardNum;

    @Column
    private String name;

}
