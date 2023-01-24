package com.motiur.consumer.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.GeneratedValue;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GenerationType;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @NotEmpty
    private String name;

    // @NotEmpty
    // private String username;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Size(min = 6)
    @JsonProperty(access = Access.WRITE_ONLY)
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Objection> objections;

    public User() {

    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // public String getUsername() {
    //     return this.username;
    // }

    // public void setUsername(String username) {
    //     this.username = username;
    // }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Objection> getObjections() {
        return this.objections;
    }

    public void setObjections(List<Objection> objections) {
        this.objections = objections;
    }
}
