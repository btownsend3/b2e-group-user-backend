package net.yorksolutions.userbackend.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
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

//    @OneToMany
//    List<HashMap> assignments;

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

    }

    public UUID getId(){
        return this.id;
    }

}
