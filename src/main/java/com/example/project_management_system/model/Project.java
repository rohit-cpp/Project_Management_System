package com.example.project_management_system.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String description;
    private String category;


    private List<String> tags= new ArrayList<>();

    @JsonIgnore
    @OneToOne(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Chat chat;

    @ManyToOne
    private User owner;


    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Issue>isssues = new ArrayList<>();

    @ManyToMany
    private List<User>team = new ArrayList<>();
}
