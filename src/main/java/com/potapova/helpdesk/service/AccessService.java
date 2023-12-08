package com.potapova.helpdesk.service;

public interface AccessService {

    Boolean checkIfUserBelongToTicket(Long userId, Long ticketId);
}
