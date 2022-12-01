package ru.itis.MyTube.controllers.servlets.user;

import ru.itis.MyTube.auxiliary.Alert;
import ru.itis.MyTube.auxiliary.constants.Beans;
import ru.itis.MyTube.auxiliary.exceptions.ServiceException;
import ru.itis.MyTube.auxiliary.exceptions.ValidationException;
import ru.itis.MyTube.model.dto.forms.RegistrationForm;
import ru.itis.MyTube.model.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Queue;

import static ru.itis.MyTube.auxiliary.constants.Attributes.ALERTS;
import static ru.itis.MyTube.auxiliary.constants.UrlPatterns.AUTHENTICATION_PAGE;
import static ru.itis.MyTube.auxiliary.constants.UrlPatterns.REGISTRATION_PAGE;

@WebServlet(REGISTRATION_PAGE)
public class RegistrationServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() {
        userService = (UserService) getServletContext().getAttribute(Beans.USER_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setAttribute("form", new RegistrationForm());

        req.getRequestDispatcher("/WEB-INF/jsp/RegistrationPage.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        RegistrationForm registrationForm = RegistrationForm.builder()
                .username(req.getParameter("username"))
                .password(req.getParameter("password"))
                .passwordRepeat(req.getParameter("passwordRepeat"))
                .firstName(req.getParameter("firstName"))
                .lastName(req.getParameter("lastName"))
                .birthdate(req.getParameter("birthdate"))
                .country(req.getParameter("country"))
                .agreement(req.getParameter("agreement"))
                .build();

        req.setAttribute("form", registrationForm);
        Queue<? super Alert> alerts = (Queue<? super Alert>) req.getSession().getAttribute(ALERTS);

        try {
            userService.save(registrationForm);
            alerts.add(new Alert(Alert.alertType.SUCCESS, "You registered."));
            resp.sendRedirect(getServletContext().getContextPath() + AUTHENTICATION_PAGE);

        } catch (ValidationException e) {
            req.setAttribute("problems", e.getProblems());
            req.getRequestDispatcher("/WEB-INF/jsp/RegistrationPage.jsp").forward(req, resp);

        } catch (ServiceException e) {
            alerts.add(new Alert(Alert.alertType.DANGER, e.getMessage()));
            resp.sendRedirect(getServletContext().getContextPath());
        }
    }
}
