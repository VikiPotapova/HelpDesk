package com.potapova.helpdesk.domain;

import lombok.Getter;

@Getter
public enum Category {
    APPLICATION_AND_SERVICES("Application & Services"),
    BENEFITS_AND_PAPER_WORK("Benefits & Paper Work"),
    HARDWARE_AND_SOFTWARE("Hardware & Software"),
    PEOPLE_MANAGEMENT("People Management"),
    SECURITY_AND_ACCESS("Security & Access"),
    WORKPLACES_AND_FACILITIES("Workplaces & Facilities");

    private final String name;

    Category(String name) {
        this.name = name;
    }
}
