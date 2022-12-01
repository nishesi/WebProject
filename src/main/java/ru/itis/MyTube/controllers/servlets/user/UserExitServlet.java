package ru.itis.MyTube.controllers.servlets.user;

import ru.itis.MyTube.auxiliary.Alert;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Queue;

import static ru.itis.MyTube.auxiliary.constants.Attributes.ALERTS;
import static ru.itis.MyTube.auxiliary.constants.Attributes.USER;
import static ru.itis.MyTube.auxiliary.constants.UrlPatterns.PRIVATE_USER_EXIT;

@WebServlet(PRIVATE_USER_EXIT)
public class UserExitServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.getSession().removeAttribute(USER);
        ((Queue<? super Alert>) req.getSession().getAttribute(ALERTS))
                .add(new Alert(Alert.alertType.INFO, "You have logged out."));
        resp.sendRedirect(getServletContext().getContextPath());
    }
}
