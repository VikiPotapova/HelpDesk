package com.potapova.helpdesk.service;

import com.potapova.helpdesk.domain.User;

public interface AccessService {
    Boolean isUserBelongToTicket(User user, Long ticketId);
}
