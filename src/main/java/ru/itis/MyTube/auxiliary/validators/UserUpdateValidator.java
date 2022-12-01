package ru.itis.MyTube.auxiliary.validators;

import ru.itis.MyTube.auxiliary.exceptions.ValidationException;
import ru.itis.MyTube.model.dto.forms.UserUpdateForm;

import javax.servlet.http.Part;
import java.util.HashMap;
import java.util.Map;

public class UserUpdateValidator extends AbstractValidator {

    public void validate(UserUpdateForm form) throws ValidationException {
        Map<String, String> problems = new HashMap<>();
        validateIconPart(form.getIconPart(), problems);
        validatePassword(form.getPassword(), problems);
        validateFirstName(form.getFirstName(), problems);
        validateLastName(form.getLastName(), problems);
        validateBirthDate(form.getBirthdate(), problems);
        validateCountry(form.getCountry(), problems);

        if (!problems.isEmpty()) {
            throw new ValidationException(problems);
        }
    }

    @Override
    protected void validateIconPart(Part icon, Map<String, String> problems) {
        if (icon.getSize() != 0) {
            super.validateIconPart(icon, problems);
        }
    }
}
