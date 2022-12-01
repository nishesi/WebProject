package ru.itis.MyTube.auxiliary.tags;

import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;

public class WhenAddedTag extends TagSupport {
    private LocalDateTime addedDate;

    public void setAddedDate(LocalDateTime addedDate) {
        this.addedDate = addedDate;
    }

    private static final SimpleDateFormat yearsFormat = new SimpleDateFormat("dd.MM.yyyy 'at' mm:hh");

    private static final SimpleDateFormat monthsFormat = new SimpleDateFormat(" dd.MM 'at' mm:hh");

    @Override
    public int doStartTag() {
        Duration t = Duration.between(addedDate, LocalDateTime.now());
        long sec = t.getSeconds();
        int minutes = (int) sec / 60;
        int hours = minutes / 60;
        int days = hours / 24;
        int months = days / 30;
        int years = months / 12;

        try {
            if (years >= 1) {
                pageContext.getOut().print(yearsFormat.format(addedDate));

            } else if (months >= 1) {
                pageContext.getOut().print(monthsFormat.format(addedDate));

            } else if (days >= 1) {
                pageContext.getOut().print(days + " days ago");

            } else if (hours >= 1) {
                pageContext.getOut().print(hours + " hours ago");

            } else if (minutes >= 1){
                pageContext.getOut().print(minutes + " minutes ago");
            } else {
                pageContext.getOut().print("Added just now");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Tag can not be executed.");
        }
        return 0;
    }
}
