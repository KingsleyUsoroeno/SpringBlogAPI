package com.kingtech.main.data.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class CreateUserRequestModel {

    @NotNull(message = "Username cannot be null")
    private String userName;

    @NotNull(message = "First Name cannot be null")
    @Size(min = 2, max = 50, message = "First name cannot be lesser than two characters")
    private String firstName;

    @NotNull(message = "password cannot be null")
    @Size(min = 8, max = 16, message = "password must be equal or greater than 8 characters and less than 16 characters")
    private String password;

    @NotNull(message = "Last name cannot be null")
    @Size(min = 2, message = "Last name cannot be lesser than two characters")
    private String lastName;

    @Email
    @NotNull(message = "Email cannot be null")
    private String email;

    public CreateUserRequestModel() {
    }

    public CreateUserRequestModel(@NotNull String userName, String firstName, @NotNull String password, String lastName, String email) {
        this.userName = userName;
        this.firstName = firstName;
        this.password = password;
        this.lastName = lastName;
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
