package com.example.project_management_system.service;

import com.example.project_management_system.model.Issue;
import com.example.project_management_system.model.User;
import com.example.project_management_system.request.IssueRequest;

import java.util.List;
import java.util.Optional;

public interface IssueService {
    Issue getIssueById(Long issueId) throws Exception;

    List<Issue> getIssueByProjectId(Long projectId) throws Exception;

    Issue createIssue(IssueRequest issue, User user)
            throws Exception;


    String deleteIssue(Long issueId, Long userId)
            throws Exception;


    Issue addUserToIssue(Long issueId, Long userId)
            throws Exception;

    Issue updateStatus(Long issueId, String status) throws Exception;


}
