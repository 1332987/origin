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
    private int age;


    protected User() {
    }

    public User(String username, int age) {
        this.username = username;
        this.age = age;
    }
}
