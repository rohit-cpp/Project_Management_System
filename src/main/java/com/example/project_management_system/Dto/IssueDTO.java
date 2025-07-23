package com.example.project_management_system.Dto;

import com.example.project_management_system.model.Project;
import com.example.project_management_system.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueDTO {

     private Long id;
     private String title;
     private String description;
     private String status;
     private Long projectId;
     private String priority;
     private LocalDate dueDate;
     private List<String> tags = new ArrayList<>();
     private Project project;

     private User assigned;
}
