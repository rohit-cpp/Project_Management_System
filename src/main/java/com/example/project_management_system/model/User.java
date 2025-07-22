package com.example.project_management_system.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long fullName;
    private String email;
    private String password;
    @JsonIgnore
    @OneToMany(mappedBy = "assigned", cascade = CascadeType.ALL)
    private List<Issue>assignedIssue = new ArrayList<>();
    private int projectSize;
}
