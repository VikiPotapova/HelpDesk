package com.potapova.helpdesk.service;

import com.potapova.helpdesk.domain.User;

public interface AccessService {
    Boolean checkIfUserBelongToTicket(User user, Long ticketId);
}
