package ru.itis.MyTube.auxiliary.tags;

import ru.itis.MyTube.auxiliary.Alert;

import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Queue;

public class AlertHandler extends TagSupport {
    private Queue<? extends Alert> alerts;

    public void setAlerts(Queue<? extends Alert> alerts) {
        this.alerts = alerts;
    }

    @Override
    public int doStartTag() {
        try {
            printShell();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    private void printShell() throws IOException {
        pageContext.getOut().print("<div class=\"toast-container position-fixed top-0 end-0 me-5 p-3\">");
        printGut();
        pageContext.getOut().print("</div>");
    }

    private void printGut() throws IOException {
        while (alerts.peek() != null) {
            Alert alert = alerts.poll();
            pageContext.getOut().print("<div class=\"toast show align-items-center border-0 mb-1\" role=\"alert\" aria-live=\"assertive\" aria-atomic=\"true\">\n");
            pageContext.getOut().print("<div class=\"d-flex alert " + alert.getType().getAlertType() + " p-0\">\n");
            pageContext.getOut().print("<div class=\"toast-body\">" + alert.getMessage() + "</div>\n");
            pageContext.getOut().print("<button type=\"button\" class=\"btn-close btn-close me-2 m-auto\" data-bs-dismiss=\"toast\" aria-label=\"Close\"></button>\n");
            pageContext.getOut().print("    </div>\n</div>");
        }
    }
}
