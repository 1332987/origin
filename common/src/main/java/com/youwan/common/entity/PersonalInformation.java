package com.youwan.common.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class PersonalInformation {
    @Id
    private long id;
    private int isImgDeleted;
    @Column
    private String path;
    @Column
    private String personId;
    private int state;
    private long time;
    private int type;
}
