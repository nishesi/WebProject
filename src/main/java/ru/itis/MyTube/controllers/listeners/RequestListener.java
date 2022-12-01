package ru.itis.MyTube.controllers.listeners;

import ru.itis.MyTube.auxiliary.Alert;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;

import static ru.itis.MyTube.auxiliary.constants.Attributes.ALERTS;

@WebListener
public class RequestListener implements ServletRequestListener {

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        HttpServletRequest req =(HttpServletRequest) sre.getServletRequest();

        if (req.getSession().getAttribute(ALERTS) == null) {
            req.getSession().setAttribute(ALERTS, new LinkedList<Alert>());
        }

        try {
            req.setCharacterEncoding(StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        req.setAttribute("regPageCss", req.getContextPath() + "/css/reg-page.css");
    }
}
