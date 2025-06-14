package dev.sivakova.core.model;

import java.util.Objects;

public class UserDto {
    private String name;
    private String password;

    public UserDto() {
    }

    public UserDto(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean equals(Object o) {
        if (o != null && this.getClass() == o.getClass()) {
            User user = (User) o;
            return Objects.equals(this.name, user.getName()) && Objects.equals(this.password, user.getPassword());
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.name, this.password});
    }

    public String toString() {
        return "User{name='" + this.name + "', password='" + this.password + "'}";
    }
}
