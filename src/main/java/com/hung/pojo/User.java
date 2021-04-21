package com.hung.pojo;

import java.util.Objects;

/**
 * 用户类
 *
 * @author Hung
 */
public class User {
    Integer id;
    String name;
    String gender;
    String team;
    String major;
    String introduction;
    Integer accountId;

    public User() {
    }

    public User(Integer id, String name, String gender, String team, String major, String introduction, Integer accountId) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.team = team;
        this.major = major;
        this.introduction = introduction;
        this.accountId = accountId;
    }

    public User(String name, String gender, String team, String major, String introduction, Integer accountId) {
        this.name = name;
        this.gender = gender;
        this.team = team;
        this.major = major;
        this.introduction = introduction;
        this.accountId = accountId;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public User(Integer id, String name, String gender, String team, String introduction, Integer accountId) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.team = team;
        this.introduction = introduction;
        this.accountId = accountId;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", team='" + team + '\'' +
                ", major='" + major + '\'' +
                ", introduction='" + introduction + '\'' +
                ", accountId=" + accountId +
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
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(gender, user.gender) && Objects.equals(team, user.team) && Objects.equals(major, user.major) && Objects.equals(introduction, user.introduction) && Objects.equals(accountId, user.accountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, gender, team, major, introduction, accountId);
    }
}
