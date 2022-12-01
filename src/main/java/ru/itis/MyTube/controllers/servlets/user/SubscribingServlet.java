package ru.itis.MyTube.controllers.servlets.user;

import ru.itis.MyTube.auxiliary.Alert;
import ru.itis.MyTube.auxiliary.exceptions.ServiceException;
import ru.itis.MyTube.model.dto.User;
import ru.itis.MyTube.model.dto.forms.SubscribeForm;
import ru.itis.MyTube.model.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Queue;

import static ru.itis.MyTube.auxiliary.constants.Attributes.ALERTS;
import static ru.itis.MyTube.auxiliary.constants.Attributes.USER;
import static ru.itis.MyTube.auxiliary.constants.Beans.USER_SERVICE;
import static ru.itis.MyTube.auxiliary.constants.UrlPatterns.CHANNEL;
import static ru.itis.MyTube.auxiliary.constants.UrlPatterns.PRIVATE_SUBSCRIBE;

@WebServlet(PRIVATE_SUBSCRIBE)
public class SubscribingServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = (UserService) getServletContext().getAttribute(USER_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(getServletContext().getContextPath());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        SubscribeForm subscribeForm = SubscribeForm.builder()
                .user((User) req.getSession().getAttribute(USER))
                .toSubscribe(req.getParameter("type"))
                .channelId(req.getParameter("channelId"))
                .build();

        try {
            userService.userChannel(subscribeForm);
        } catch (ServiceException ex) {
            ((Queue<? super Alert>)req.getSession().getAttribute(ALERTS))
                    .add(new Alert(Alert.alertType.DANGER, ex.getMessage()));
        }
        resp.sendRedirect(getServletContext().getContextPath() + CHANNEL + "?id=" + subscribeForm.getChannelId());
    }
}
