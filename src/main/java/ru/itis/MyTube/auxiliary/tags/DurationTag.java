package ru.itis.MyTube.auxiliary.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DurationTag extends TagSupport {
    private LocalTime duration;

    public void setDuration(LocalTime duration) {
        this.duration = duration;
    }

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("mm:ss");

    @Override
    public int doStartTag() throws JspException {
        try {
            if (duration.getHour() == 0) {
                pageContext.getOut().print(duration.format(formatter));
            } else {
                pageContext.getOut().print(duration);
            }
        } catch (IOException e) {
            throw new JspException(e);
        }
        return 0;
    }
}
