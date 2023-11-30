package com.potapova.helpdesk.domain;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;

public enum Status {
    DRAFT,
    NEW,
    APPROVED,
    DECLINED,
    CANCELLED,
    IN_PROGRESS,
    DONE
}
