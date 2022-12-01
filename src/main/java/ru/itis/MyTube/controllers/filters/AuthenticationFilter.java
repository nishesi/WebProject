package ru.itis.MyTube.controllers.filters;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ru.itis.MyTube.auxiliary.constants.Attributes.USER;
import static ru.itis.MyTube.auxiliary.constants.UrlPatterns.*;

@WebFilter(PRIVATE + "/*")
public class AuthenticationFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {

        if (req.getSession().getAttribute(USER) == null) {
            req.getSession().setAttribute("requestUrl", req.getRequestURI());
            res.sendRedirect(getServletContext().getContextPath() + AUTHENTICATION_PAGE);

        } else {
            chain.doFilter(req, res);
        }
    }
}
