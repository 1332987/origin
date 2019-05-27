package com.youwan.common.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String type;


    protected User() {
    }

    public User(String username, String password ,String type) {
        this.username = username;
        this.password = password;
        this.type = type;
    }
}
