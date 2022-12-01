package ru.itis.MyTube.auxiliary.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class ViewsTag extends TagSupport {

    private Long views;

    public void setViews(long views) {
        this.views = views;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            if (views >= 1_000_000_000) {
                double blnViews = (double) Math.round(((double) views) / 1_000_000_000 * 10) / 10;
                pageContext.getOut().print(blnViews + " billion views");

            } else if (views >= 1_000_000) {
                double mlnViews = (double) Math.round(((double) views) / 1_000_000 * 10) / 10;
                pageContext.getOut().print(mlnViews + " million views");

            } else if (views >= 1_000) {
                double thsViews = (double) Math.round(((double) views) / 1_000 * 10) / 10;
                pageContext.getOut().print(thsViews + " thousand views");

            } else {
                pageContext.getOut().print(views + " views");
            }

        } catch (IOException e) {
            throw new JspException(e);
        }

        return 0;
    }
}
