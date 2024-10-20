package com.example.crud.ProductCategory;

import jakarta.persistence.*;

@Entity
@Table
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String name;

//    public Category() {
//    }
//
//    public Category(Integer id, String name) {
//        this.id = id;
//        this.name = name;
//    }
//
//    public Category(String name) {
//        this.name = name;
//    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

