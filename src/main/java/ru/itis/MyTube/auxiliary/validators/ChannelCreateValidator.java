package ru.itis.MyTube.auxiliary.validators;

import ru.itis.MyTube.auxiliary.exceptions.ValidationException;
import ru.itis.MyTube.model.dto.forms.ChannelForm;

import java.util.HashMap;
import java.util.Map;

public class ChannelCreateValidator extends VideoValidator{
    public void validate(ChannelForm form) throws ValidationException {
        Map<String, String> problems = new HashMap<>();

        validateName(form.getName(), problems);
        validateInfo(form.getInfo(), problems);
        validateIconPart(form.getIconPart(), problems);

        if (!problems.isEmpty()) {
            throw new ValidationException(problems);
        }
    }

    @Override
    protected void validateName(String name, Map<String, String> problems) {
        super.validateName(name, problems);
        if (name.length() > 20) {
            problems.put("name", "Name length more then 20 symbols");
        }
    }
}
