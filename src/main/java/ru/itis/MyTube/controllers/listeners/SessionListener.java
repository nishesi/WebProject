package ru.itis.MyTube.controllers.listeners;

import ru.itis.MyTube.auxiliary.Alert;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.LinkedList;
import java.util.Queue;

import static ru.itis.MyTube.auxiliary.constants.Attributes.ALERTS;

@WebListener
public class SessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        Queue<Alert> alertQueue = new LinkedList<>();

        se.getSession().setAttribute(ALERTS, alertQueue);
    }
}
