package ru.itis.MyTube.auxiliary.validators;

import ru.itis.MyTube.auxiliary.exceptions.ValidationException;
import ru.itis.MyTube.model.dto.forms.AuthenticationForm;

import java.util.HashMap;
import java.util.Map;

public class AuthenticationValidator {

    public void validate(AuthenticationForm form) throws ValidationException {
        Map<String, String> problems = new HashMap<>();

        validateUsername(form.getUsername(), problems);
        validatePassword(form.getPassword(), problems);

        if (!problems.isEmpty()) {
            throw new ValidationException(problems);
        }
    }

    protected void validateUsername(String username, Map<String, String> problems) {
        if (username == null || username.equals("")) {
            problems.put("username", "Username can not be empty.");
        }
    }

    protected void validatePassword(String password, Map<String, String> problems) {
        if (password == null || password.equals("")) {
            problems.put("password", "Password can not be empty.");
        }
    }
}
