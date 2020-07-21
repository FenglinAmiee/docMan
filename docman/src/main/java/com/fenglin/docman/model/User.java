package com.fenglin.docman.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "t_user")
public class User {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;
    @Column(nullable = false,unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;
    @Column
    private String department;
    @Column(nullable = false)
    private int level;
    @Column(nullable = false)
    private String contact ;
    @Column
    private String remark ;
}
