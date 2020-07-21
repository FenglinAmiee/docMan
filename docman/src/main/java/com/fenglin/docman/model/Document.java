package com.fenglin.docman.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "T_DOCUMENT")
public class Document {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false,unique = true)
    private String keyword;
    @Column(nullable = false)
    private String publisher;
    @Column(name = "affect_date",nullable = false)
    private String affectDate;
    @Column(nullable = false)
    private int level;
    @Column
    private String remark;
    @Column(nullable = false)
    private String path;
    @Column(name = "real_name",nullable = false)
    private String realName;
}
