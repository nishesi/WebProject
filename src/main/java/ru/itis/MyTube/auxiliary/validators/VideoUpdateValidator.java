package ru.itis.MyTube.auxiliary.validators;

import javax.servlet.http.Part;
import java.util.Map;

public class VideoUpdateValidator extends VideoValidator {

    @Override
    protected void validateIconPart(Part part,Map<String, String> problems) {
        if (part.getSize() != 0) {
            super.validateIconPart(part, problems);
        }
    }

    @Override
    protected void validateVideoPart(Part part,Map<String, String> problems) {
        if (part.getSize() != 0) {
            super.validateVideoPart(part, problems);
        }
    }
}
