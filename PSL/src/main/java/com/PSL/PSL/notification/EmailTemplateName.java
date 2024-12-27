package com.PSL.PSL.notification;


import lombok.Getter;

@Getter
public enum EmailTemplateName {

    PHARMACY_NOTIFICATION("pharmacy-notification"),
    GROSSISTE_NOTIFICATION("grossiste-notification");

    private final String name;

    EmailTemplateName(String name) {
        this.name = name;
    }
}
