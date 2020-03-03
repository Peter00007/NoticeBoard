package com.board.model;

import javax.validation.constraints.*;
import java.util.List;
import java.util.Objects;

public class User {
    private int id;
    @NotNull
    @Size(min = 2, max = 20, message = "Name should be between 2 than 20 characters.")
    @Pattern(regexp = "[A-Z][a-z]{1,19}")
    private String name;
    @NotNull
    @Size(min = 2, max = 20, message = "Last Name should be between 2 than 20 characters.")
    @Pattern(regexp = "[A-Z][a-z]{1,19}")
    private String lastName;
    @NotNull
    @Size(min = 2, max = 20, message = "Password should be between 8 than 25 characters and contains at least one Upper character, one lower character and one number.")
    @Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,25}")
    private String password;
    @NotNull
    @Size(min = 2, max = 20, message = "Email should be between 10 than 50 characters.")
    private String email;
    private UserStatus status;
    /*@NotNull*/
    private List<Role> roles;
    @NotNull
    @Size(min = 6, max = 6, message = "Code must contains 6 characters.")
    private String confirmActivationCode;

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserStatus getUserStatus() {
        return status;
    }

    public void setUserStatus(UserStatus status) {
        this.status = status;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getConfirmActivationCode() {
        return confirmActivationCode;
    }

    public void setConfirmActivationCode(String confirmActivationCode) {
        this.confirmActivationCode = confirmActivationCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(name, user.name) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(password, user.password) &&
                Objects.equals(email, user.email) &&
                status == user.status &&
                Objects.equals(roles, user.roles) &&
                Objects.equals(confirmActivationCode, user.confirmActivationCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, lastName, password, email, status, roles, confirmActivationCode);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", userStatus=" + status +
                '}';
    }
}
