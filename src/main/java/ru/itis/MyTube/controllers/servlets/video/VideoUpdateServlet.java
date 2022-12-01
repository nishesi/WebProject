package ru.itis.MyTube.controllers.servlets.video;

import ru.itis.MyTube.auxiliary.Alert;
import ru.itis.MyTube.auxiliary.constants.Beans;
import ru.itis.MyTube.auxiliary.exceptions.ServiceException;
import ru.itis.MyTube.auxiliary.exceptions.ValidationException;
import ru.itis.MyTube.model.dto.User;
import ru.itis.MyTube.model.dto.forms.VideoForm;
import ru.itis.MyTube.model.services.VideoService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Queue;

import static ru.itis.MyTube.auxiliary.constants.Attributes.ALERTS;
import static ru.itis.MyTube.auxiliary.constants.Attributes.FORM;
import static ru.itis.MyTube.auxiliary.constants.UrlPatterns.CHANNEL;
import static ru.itis.MyTube.auxiliary.constants.UrlPatterns.PRIVATE_VIDEO_UPDATE;

@WebServlet(PRIVATE_VIDEO_UPDATE)
@MultipartConfig
public class VideoUpdateServlet extends HttpServlet {

    private VideoService videoService;

    @Override
    public void init() throws ServletException {
        videoService = (VideoService) getServletContext().getAttribute(Beans.VIDEO_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("pageType", "Update");
        req.setAttribute("url", getServletContext().getContextPath() + PRIVATE_VIDEO_UPDATE + "?uuid=" + req.getParameter("uuid"));
        req.getRequestDispatcher("/WEB-INF/jsp/UtilVideoPage.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        VideoForm videoForm = VideoForm.builder()
                .videoUuid(req.getParameter("uuid"))
                .channelId(((User)req.getSession().getAttribute("user")).getChannelId())
                .name(req.getParameter("name"))
                .info(req.getParameter("info"))
                .iconPart(req.getPart("icon"))
                .videoPart(req.getPart("video"))
                .build();

        Queue<? super Alert> alerts = (Queue<? super Alert>) req.getSession().getAttribute(ALERTS);

        try {
            videoService.updateVideo(videoForm);
            alerts.add(new Alert(Alert.alertType.SUCCESS, "Video updated."));
            resp.sendRedirect(getServletContext().getContextPath() + CHANNEL + "?id=" + videoForm.getChannelId());
            return;

        } catch (ValidationException e) {
            req.setAttribute("problems", e.getProblems());
            req.setAttribute(FORM, videoForm);

        } catch (ServiceException ex) {
            alerts.add(new Alert(Alert.alertType.DANGER, ex.getMessage()));
        }

        req.setAttribute("pageType", "Update");
        req.getRequestDispatcher("/WEB-INF/jsp/UtilVideoPage.jsp").forward(req, resp);
    }
}
