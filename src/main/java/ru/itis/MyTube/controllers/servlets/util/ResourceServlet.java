package ru.itis.MyTube.controllers.servlets.util;

import ru.itis.MyTube.auxiliary.constants.Beans;
import ru.itis.MyTube.auxiliary.enums.FileType;
import ru.itis.MyTube.auxiliary.exceptions.StorageException;
import ru.itis.MyTube.model.storage.Storage;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.UUID;

import static ru.itis.MyTube.auxiliary.constants.UrlPatterns.RESOURCE;

@WebServlet(RESOURCE)
public class ResourceServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init() {
        storage = (Storage) getServletContext().getAttribute(Beans.STORAGE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        String type = req.getParameter("fileType");

         if (!validateParameters(type, id, resp)) {
             return;
         }

        FileType fileType1 = null;

        try {
            switch (type) {
                case "v":
                    UUID.fromString(id);
                    fileType1 = FileType.VIDEO;
                    resp.setContentType("video/mp4");
                    break;

                case "vi":
                    UUID.fromString(id);
                    fileType1 = FileType.VIDEO_ICON;
                    resp.setContentType("image/jpg");
                    break;

                case "ci":
                    Long.parseLong(id);
                    fileType1 = FileType.CHANNEL_ICON;
                    resp.setContentType("image/jpg");
                    break;

                case "ui":
                    fileType1 = FileType.USER_ICON;
                    resp.setContentType("image/jpg");
                    break;

                default:
                    resp.sendError(405, "unknown type = " + type);
                    break;
            }
        } catch (IllegalArgumentException ex) {
            resp.sendError(405 , "invalid id = " + id);
            return;
        }



        try (InputStream resource = storage.get(fileType1, id)) {

            resource.transferTo(resp.getOutputStream());

        } catch (StorageException ex) {
            resp.setStatus(200);
            resp.getWriter().println(ex.getMessage());

        } catch (RuntimeException ex) {
            resp.setStatus(500);
            resp.getWriter().println(ex.getMessage());
        }
    }

    private boolean validateParameters(String type, String id, HttpServletResponse resp) throws IOException {

        if (Objects.isNull(id) || Objects.isNull(type)) {
            resp.sendError(405, "id = " + id + ", type = " + type);
            return false;
        }
        return true;
    }
}
