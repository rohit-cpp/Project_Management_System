package com.example.project_management_system.service;

import com.example.project_management_system.model.Chat;
import com.example.project_management_system.model.Project;
import com.example.project_management_system.model.User;
import com.example.project_management_system.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class ProjectServiceImpl implements ProjectService{

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ChatService chatService;


    @Override
    public Project createProject(Project project, User user) throws Exception {
        Project createdProject = new Project();
        createdProject.setOwner(user);
        createdProject.setTags(project.getTags());
        createdProject.setName(project.getName());
        createdProject.setCategory(project.getCategory());
        createdProject.setDescription(project.getDescription());
        createdProject.getTeam().add(user);

        Project savedProject = projectRepository.save(createdProject);

        Chat chat = new Chat();
        chat.setProject(savedProject);

        Chat projectChat = chatService.createChat(chat);
        savedProject.setChat(projectChat);
        return savedProject;
    }

    @Override
    public List<Project> getProjectByTeam(User user, String category, String tag) throws Exception {
        List<Project> projects = projectRepository.findByTeamContainingOrOwner(user,user);

          if(category!=null){
              projects= projects.stream().filter(project ->
                      project.getCategory().equals(category))
                      .collect(Collectors.toList());
          } if(tag!=null){
              projects= projects.stream().filter(project ->
                      project.getTags().contains(tag))
                      .collect(Collectors.toList());
          }
        return projects;
    }

    @Override
    public Project getProjectById(Long projectId) throws Exception {
        Optional<Project>optionalProject=projectRepository.findById(projectId);
        if(optionalProject.isEmpty()){
            throw new Exception("project not found");
        }
        return optionalProject.get();
    }

    @Override
    public void deleteProject(Long projectId, Long UserId) throws Exception {
        getProjectById(projectId);
//        userService.findUserById(userId);

        projectRepository.deleteById(projectId);
    }

    @Override
    public Project updateProject(Project updatedProject, Long id) throws Exception {
        Project project=getProjectById(id);
        project.setName(updatedProject.getName());
        project.setDescription(updatedProject.getDescription());
        project.setTags(updatedProject.getTags());
        return projectRepository.save(project);
    }

    @Override
    public void addUserToProject(Long projectId, Long userId) throws Exception {
    Project project= getProjectById(projectId);
    User user = userService.findUserById(userId);
    if(!project.getTeam().contains(user)){
        project.getChat().getUsers().add(user);
        project.getTeam().add(user);
    }
    projectRepository.save(project);
    }

    @Override
    public void removeUserFromProject(Long projectId, Long userId) throws Exception {
        Project project= getProjectById(projectId);
        User user = userService.findUserById(userId);
        if(!project.getTeam().contains(user)){
            project.getChat().getUsers().remove(user);
            project.getTeam().remove(user);
        }
        projectRepository.save(project);
    }

    @Override
    public Chat getChatByProjectId(Long projectId) throws Exception {
        Project project= getProjectById(projectId);
        return project.getChat();
    }

    @Override
    public List<Project> searchProjects(String keyword, User user) throws Exception {

        String particularName= "%"+ keyword + "%%";

        return projectRepository.findByNameContainingAndTeamContains(keyword,user);
    }
}
