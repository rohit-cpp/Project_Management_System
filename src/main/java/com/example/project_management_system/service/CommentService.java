package com.example.project_management_system.service;

import com.example.project_management_system.model.Comment;

import java.util.List;

public interface CommentService {


    Comment createComment(Long issueId, Long userId, String comment) throws Exception;

    void deleteComment(Long commentId, Long userId) throws Exception;

    List<Comment> findCommentByIssueId(Long issueId);
}
