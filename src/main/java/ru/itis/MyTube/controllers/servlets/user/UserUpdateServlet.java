package ru.itis.MyTube.controllers.servlets.user;

import ru.itis.MyTube.auxiliary.Alert;
import ru.itis.MyTube.auxiliary.exceptions.ServiceException;
import ru.itis.MyTube.auxiliary.exceptions.ValidationException;
import ru.itis.MyTube.model.dto.User;
import ru.itis.MyTube.model.dto.forms.UserUpdateForm;
import ru.itis.MyTube.model.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Queue;

import static ru.itis.MyTube.auxiliary.constants.Attributes.ALERTS;
import static ru.itis.MyTube.auxiliary.constants.Beans.USER_SERVICE;
import static ru.itis.MyTube.auxiliary.constants.UrlPatterns.PRIVATE_USER_EXIT;
import static ru.itis.MyTube.auxiliary.constants.UrlPatterns.PRIVATE_USER_UPDATE;

@WebServlet(PRIVATE_USER_UPDATE)
@MultipartConfig
public class UserUpdateServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() {
        userService = (UserService) getServletContext().getAttribute(USER_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/UserPage.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserUpdateForm form = UserUpdateForm.builder()
                .iconPart(req.getPart("icon"))
                .password(req.getParameter("password"))
                .firstName(req.getParameter("firstName"))
                .lastName(req.getParameter("lastName"))
                .birthdate(req.getParameter("birthdate"))
                .country(req.getParameter("country")).build();

        Queue<? super Alert> alerts = (Queue<? super Alert>)req.getSession().getAttribute(ALERTS);
        try {
            userService.update(form,(User)req.getSession().getAttribute("user"));

            alerts.add(new Alert(Alert.alertType.SUCCESS, "Your account information updated."));
            alerts.add(new Alert(Alert.alertType.INFO, "Please do reauthorization."));

            resp.sendRedirect(getServletContext().getContextPath() + PRIVATE_USER_EXIT);
            return;
        } catch (ValidationException e) {
            req.setAttribute("problems", e.getProblems());

        } catch (ServiceException e) {
            alerts.add(new Alert(Alert.alertType.DANGER, e.getMessage()));
        }
        req.getRequestDispatcher("/WEB-INF/jsp/UserPage.jsp").forward(req, resp);
    }
}
