package com.hung.pojo;

import java.util.Objects;

/**
 * @author Hung
 */
public class Menu {
    private Integer id;
    private String name;
    private String url;
    private Integer parentId;

    public Menu() {
    }

    public Menu(String name, String url, Integer parentId) {
        this.name = name;
        this.url = url;
        this.parentId = parentId;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Menu menu = (Menu) o;
        return Objects.equals(id, menu.id) && Objects.equals(name, menu.name) && Objects.equals(url, menu.url) && Objects.equals(parentId, menu.parentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, url, parentId);
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":\"" + id +
                "\", \"name\":\"" + name + '\"' +
                ", \"url\":\"" + url + '\"' +
                ", \"parentId\":\"" + parentId + '\"' +
                '}';
    }
}
