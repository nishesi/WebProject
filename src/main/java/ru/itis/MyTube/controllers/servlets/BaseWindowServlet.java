package ru.itis.MyTube.controllers.servlets;

import ru.itis.MyTube.auxiliary.Alert;
import ru.itis.MyTube.auxiliary.constants.Attributes;
import ru.itis.MyTube.auxiliary.constants.Beans;
import ru.itis.MyTube.auxiliary.exceptions.ServiceException;
import ru.itis.MyTube.model.dto.User;
import ru.itis.MyTube.model.dto.VideoCover;
import ru.itis.MyTube.model.services.VideoService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Queue;

import static ru.itis.MyTube.auxiliary.constants.Attributes.ALERTS;
import static ru.itis.MyTube.auxiliary.constants.Attributes.USER;


@WebServlet("")
public class BaseWindowServlet extends HttpServlet {

    private VideoService videoService;

    @Override
    public void init() {
        videoService = (VideoService) getServletContext().getAttribute(Beans.VIDEO_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String listType = req.getParameter("listType");
        List<VideoCover> list = null;

        try {
            if ("subs".equals(listType)) {
                list = videoService.getSubscriptionsVideos(((User) req.getSession().getAttribute(USER)));
            } else {
                list = videoService.getRandomVideos();
            }

        } catch (ServiceException ex) {
            ((Queue<? super Alert>) req.getSession().getAttribute(ALERTS))
                    .add(new Alert(Alert.alertType.DANGER, ex.getMessage()));
        }

        req.setAttribute(Attributes.VIDEO_COVER_LIST, list);
        req.setAttribute("listType", req.getParameter("listType"));
        req.getRequestDispatcher("/WEB-INF/jsp/BaseWindow.jsp").forward(req, resp);
    }
}
