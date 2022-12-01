package ru.itis.MyTube.controllers.servlets;

import ru.itis.MyTube.auxiliary.Alert;
import ru.itis.MyTube.auxiliary.exceptions.ServiceException;
import ru.itis.MyTube.auxiliary.exceptions.ValidationException;
import ru.itis.MyTube.model.dto.User;
import ru.itis.MyTube.model.dto.forms.ChannelForm;
import ru.itis.MyTube.model.services.ChannelService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Queue;

import static ru.itis.MyTube.auxiliary.constants.Attributes.*;
import static ru.itis.MyTube.auxiliary.constants.Beans.CHANNEL_SERVICE;
import static ru.itis.MyTube.auxiliary.constants.UrlPatterns.CHANNEL;
import static ru.itis.MyTube.auxiliary.constants.UrlPatterns.PRIVATE_CHANNEL_CREATE;

@WebServlet(PRIVATE_CHANNEL_CREATE)
@MultipartConfig
public class ChannelCreateServlet extends HttpServlet {

    private ChannelService channelService;

    @Override
    public void init() throws ServletException {
        channelService = (ChannelService) getServletContext().getAttribute(CHANNEL_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/ChannelCreatePage.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ChannelForm channelForm = ChannelForm.builder()
                .user((User) req.getSession().getAttribute(USER))
                .name(req.getParameter("name"))
                .iconPart(req.getPart("icon"))
                .info(req.getParameter("info"))
                .build();
        Queue<? super Alert> alerts = (Queue<? super Alert>) req.getSession().getAttribute(ALERTS);

        try {
            Long channelId = channelService.create(channelForm);
            resp.sendRedirect(getServletContext().getContextPath() + CHANNEL + "?id=" + channelId);
            return;

        } catch (ServiceException ex) {
            alerts.add(new Alert(Alert.alertType.DANGER, ex.getMessage()));

        } catch (ValidationException ex) {
            req.setAttribute(FORM, channelForm);
            req.setAttribute(PROBLEMS, ex.getProblems());
        }
        req.getRequestDispatcher("/WEB-INF/jsp/ChannelCreatePage.jsp").forward(req, resp);
    }
}
