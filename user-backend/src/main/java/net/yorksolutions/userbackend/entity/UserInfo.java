package net.yorksolutions.userbackend.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Entity
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;

    @JsonProperty
    String username;

    @JsonProperty
    String password;

    @JsonProperty
    @ElementCollection
    List<String> permissions;

    @JsonProperty
    String role;

    @ElementCollection
    @JsonProperty
    List<HashMap> assignments;

    @JsonProperty
    @ElementCollection
    List<HashMap> grades;

    public UserInfo() {
    }

    public UserInfo(String username, String password, List<String> permissions, String role) {
        this.username = username;
        this.password = password;
        this.permissions = permissions;
        this.role = role;
        this.assignments =  new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<HashMap> getGrades() {
        return grades;
    }

    public void setGrades(List<HashMap> grades) {
        this.grades = grades;
    }

    public UUID getId(){
        return this.id;
    }

}
