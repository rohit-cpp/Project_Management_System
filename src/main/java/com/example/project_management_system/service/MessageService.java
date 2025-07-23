package com.example.project_management_system.service;

import com.example.project_management_system.model.Message;

import java.util.List;

public interface MessageService {

    Message sendMessage(Long senderId, Long chatId, String content) throws Exception;

    List<Message> getMessagesByProjectId(Long project) throws Exception;
}
