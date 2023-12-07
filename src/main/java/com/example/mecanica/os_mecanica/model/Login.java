package com.example.mecanica.os_mecanica.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@Entity
@Table(name = "admin")
public class Login {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String senha;

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Object getId() {
        return id;
    }
}