package com.example.demo_work.model;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "Person")
public class Person {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min=2, max = 100,message = "Имя должно быть от 2 до 100 символов длиной")
    @Column(name = "username")
    private String username;

    @Min(value = 1900,message = "Год рождения должен быть больше, 1900")
    @Column(name = "year_of_birth")
    private Integer  year_of_birth;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;
    @Override
    public String toString() {
        return "Person{"+
                "id="+id+
                ", username='" + username+'\'' +
                ", year_of_birth='" + year_of_birth  +
                ", password='" + password  + '\'' +
                '}';
    }



}
