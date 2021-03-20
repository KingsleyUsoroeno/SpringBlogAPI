package com.kingtech.main.data.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "USERS")
public class CacheUserModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, name = "USERNAME")
    private String username;

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @Column(name = "EMAIL", unique = true)
    private String email;

    public CacheUserModel(String username, String firstName, String password, String lastName, String email) {
        this.username = username;
        this.firstName = firstName;
        this.password = password;
        this.lastName = lastName;
        this.email = email;
    }

    public CacheUserModel(int id, String username, String firstName, String password, String lastName, String email) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.password = password;
        this.lastName = lastName;
        this.email = email;
    }

    public CacheUserModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

