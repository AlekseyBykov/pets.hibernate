package com.alekseybykov.examples.hibernate.entities;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "IdentityType")
@Data
public class IdentityType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;
}