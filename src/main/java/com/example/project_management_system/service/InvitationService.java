package com.example.project_management_system.service;

import com.example.project_management_system.model.Invitation;
import jakarta.mail.MessagingException;

public interface InvitationService {
  public void sendInvitation (String email , Long protectId) throws MessagingException;
  public Invitation acceptInvitaiton(String token, Long userId) throws Exception;

  public String getTokenByUserMail(String userEmail);

  void deleteToken(String token);
}
