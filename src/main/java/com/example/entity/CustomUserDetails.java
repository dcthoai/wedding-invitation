package com.example.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class CustomUserDetails extends BaseEntity {

    @Column(name = "username", length = 25, nullable = false)
    private String username;
    @Column(name = "email", length = 255, nullable = true)
    private String email;
    @Column(name = "password", length = 255, nullable = false)
    private String password;
    @Column(name = "role", length = 50, nullable = false)
    private String role;

    public CustomUserDetails() {}

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }
}
