package ru.itis.MyTube.auxiliary.validators;

import lombok.RequiredArgsConstructor;
import ru.itis.MyTube.auxiliary.exceptions.ValidationException;
import ru.itis.MyTube.model.dao.UserRepository;
import ru.itis.MyTube.model.dto.forms.RegistrationForm;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class RegistrationValidator extends AbstractValidator{
    private final UserRepository userRepository;

    public void validate(RegistrationForm form) throws ValidationException {
        Map<String, String> problems = new HashMap<>();

        validateUsername(form.getUsername(), problems);
        validatePassword(form.getPassword(), form.getPasswordRepeat(), problems);

        validateFirstName(form.getFirstName(), problems);
        validateLastName(form.getLastName(), problems);

        validateBirthDate(form.getBirthdate(), problems);
        validateCountry(form.getCountry(), problems);

        validateAgreement(form.getAgreement(), problems);

        if (!problems.isEmpty()) {
            throw new ValidationException(problems);
        }
    }

    protected void validateUsername(String username, Map<String, String> problems) {
        if (username == null || username.equals("")) {
            problems.put("username", "Username can not be empty.");

        } else if (!onlyLettersPat.matcher(username).matches()) {
            problems.put("username", "Username should have only letters.");

        } else if (userRepository.isPresent(username)) {
            problems.put("username", "This username is exist.");
        }
    }

    protected void validatePassword(String password, String passRepeat, Map<String, String> problems) {
        if (password == null || password.equals("")) {
            problems.put("password", "Password can not be empty.");
        } else if (password.length() <= 10) {
            problems.put("password", "Password is too small.");
        } else if (!password.equals(passRepeat)) {
            problems.put("password", "Password and it's repeat is not equal.");
        }
    }

    protected void validateAgreement(String agreement, Map<String, String> problems) {
        if (agreement == null || agreement.equals("")) {
            problems.put("agreement", "You should agree with agreement.");

        } else if (!agreement.equals("on")) {
            problems.put("error", "something go wrong");
        }
    }

}
