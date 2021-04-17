package com.hung.pojo;

import java.util.Objects;

/**
 * 账户类
 * @author Hung
 */
public class Account {
    Integer id;
    String name;
    String password;
    Integer roleId;

    public Account() {
    }

    public Account(Integer id, String name, String password, Integer roleId) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.roleId = roleId;
    }

    public Account(String name, String password) {
        this.name = name;
        this.password = password;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", roleId=" + roleId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Account account = (Account) o;
        return Objects.equals(id, account.id) && Objects.equals(name, account.name) && Objects.equals(password, account.password) && Objects.equals(roleId, account.roleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, password, roleId);
    }
}
