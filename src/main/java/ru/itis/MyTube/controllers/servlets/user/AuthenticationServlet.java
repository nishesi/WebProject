package ru.itis.MyTube.controllers.servlets.user;

import ru.itis.MyTube.auxiliary.Alert;
import ru.itis.MyTube.auxiliary.constants.Beans;
import ru.itis.MyTube.auxiliary.constants.UrlPatterns;
import ru.itis.MyTube.auxiliary.exceptions.ServiceException;
import ru.itis.MyTube.auxiliary.exceptions.ValidationException;
import ru.itis.MyTube.model.dto.User;
import ru.itis.MyTube.model.dto.forms.AuthenticationForm;
import ru.itis.MyTube.model.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.Queue;

import static ru.itis.MyTube.auxiliary.constants.Attributes.*;

@WebServlet(UrlPatterns.AUTHENTICATION_PAGE)
public class AuthenticationServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() {
        userService = (UserService) getServletContext().getAttribute(Beans.USER_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("form", new AuthenticationForm());

        req.getRequestDispatcher("WEB-INF/jsp/AuthenticationPage.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AuthenticationForm form = AuthenticationForm.builder()
                .username(req.getParameter("username"))
                .password(req.getParameter("password"))
                .build();

        Queue<? super Alert> alerts = (Queue<? super Alert>) req.getSession().getAttribute(ALERTS);

        try {
            User user = userService.get(form);
            req.getSession().setAttribute(USER, user);

            alerts.add(new Alert(Alert.alertType.SUCCESS, "You authorized."));

            String url = (String) req.getSession().getAttribute("requestUrl");

            if (Objects.nonNull(url)) {
                req.getSession().removeAttribute("requestUrl");
                resp.sendRedirect(url);

            } else {
                resp.sendRedirect(getServletContext().getContextPath());
            }
            return;

        } catch (ValidationException e) {
            req.setAttribute(FORM, form);
            req.setAttribute(PROBLEMS, e.getProblems());

        } catch (ServiceException ex) {
            alerts.add(new Alert(Alert.alertType.DANGER, ex.getMessage()));
        }

        req.getRequestDispatcher("WEB-INF/jsp/AuthenticationPage.jsp").forward(req, resp);
    }
}
