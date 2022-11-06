package ru.itis.MyTube.auxiliary;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Alert {
    private alertType type;
    private String message;

    public enum alertType {
        PRIMARY("alert-primary"),
        SECONDARY("alert-secondary"),
        SUCCESS("alert-danger"),
        DANGER("alert-danger"),
        WARNING("alert-warning"),
        INFO("alert-info"),
        LIGHT("alert-light"),
        DARK("alert-dark");

        private final String alertType;

        alertType(String str) {
            alertType = str;
        }

        public String getAlertType() {
            return alertType;
        }
    }
}