package com.example.project_management_system.controller;

import com.example.project_management_system.model.Chat;
import com.example.project_management_system.model.Invitation;
import com.example.project_management_system.model.Project;
import com.example.project_management_system.model.User;
import com.example.project_management_system.request.InviteRequest;
import com.example.project_management_system.response.MessageResponse;
import com.example.project_management_system.service.InvitationService;
import com.example.project_management_system.service.ProjectService;
import com.example.project_management_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;


    @Autowired
    private InvitationService invitationService;

    @GetMapping
   public ResponseEntity<List<Project>>getProjects(@RequestParam(required = false)String category,
                                                   @RequestParam(required = false)String tag,
                                                   @RequestHeader("Authorization")String jwt) throws Exception {
       User user = userService.findUserProfileByJwt(jwt);
       List<Project> projects = projectService.getProjectByTeam(user, category,tag);
       return new ResponseEntity<>(projects, HttpStatus.OK);
   }

    @GetMapping("/{projectId}")
    public ResponseEntity<Project>getProjectById(@PathVariable Long projectId,
                                                    @RequestHeader("Authorization")String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Project project = projectService.getProjectById(projectId);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

        @PostMapping
        public ResponseEntity<Project>createProject(
                                                        @RequestHeader("Authorization")String jwt,
                                                @RequestBody Project project
              )  throws Exception {
            User user = userService.findUserProfileByJwt(jwt);
            Project createdproject = projectService.createProject(project, user);
            return new ResponseEntity<>(createdproject, HttpStatus.OK);
        }

    @PatchMapping("/{projectId}")
    public ResponseEntity<Project>updateProject(@PathVariable Long projectId,
                                                    @RequestHeader("Authorization")String jwt,
                                            @RequestBody Project project
          )  throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Project updatedproject = projectService.updateProject(project,projectId);
        return new ResponseEntity<>(updatedproject, HttpStatus.OK);
    }
    @DeleteMapping("/{projectId}")
    public ResponseEntity<MessageResponse>deleteProject(@PathVariable Long projectId,
                                                    @RequestHeader("Authorization")String jwt

          )  throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
         projectService.deleteProject(projectId, user.getId());
         MessageResponse res = new MessageResponse("project deleted successfully");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Project>> searchProjects(
            @RequestParam(required = false) String keyword,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        List<Project> projects = projectService.searchProjects(keyword, user);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/{projectId}/chat")
    public ResponseEntity<Chat> getChatByProjectId(
            @PathVariable Long projectId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Chat chat = projectService.getChatByProjectId(projectId);
        return new ResponseEntity<>(chat, HttpStatus.OK);
    }


    @PostMapping("/invite")
    public ResponseEntity<MessageResponse>inviteProject(
            @RequestBody InviteRequest req,
            @RequestHeader("Authorization")String jwt,
            @RequestBody Project project
    )  throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        invitationService.sendInvitation(req.getEmail(), req.getProjectId());
        MessageResponse res= new MessageResponse("User invitation sent");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/accept_invitation")
    public ResponseEntity<Invitation>acceptInviteProject(
            @RequestParam String token,
            @RequestHeader("Authorization")String jwt,
            @RequestBody Project project
    )  throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Invitation invitation = invitationService.acceptInvitaiton(token, user.getId());
        projectService.addUserToProject(invitation.getProjectId(),user.getId());
        return new ResponseEntity<>(invitation, HttpStatus.ACCEPTED);
    }
}
