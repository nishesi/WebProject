package ru.itis.MyTube.auxiliary.constants;

public class UrlPatterns {
    public static final String PRIVATE = "/private";
    public static final String VIDEO = "/video";
    public static final String PRIVATE_VIDEO = PRIVATE + VIDEO;
    public static final String PRIVATE_VIDEO_DELETE = PRIVATE_VIDEO + "/delete";
    public static final String PRIVATE_VIDEO_UPDATE = PRIVATE_VIDEO + "/update";
    public static final String CHANNEL = "/channel";
    public static final String PRIVATE_CHANNEL_CREATE = PRIVATE + CHANNEL + "/create";
    public static final String PRIVATE_USER_UPDATE = PRIVATE + "/user/update";
    public static final String PRIVATE_USER_EXIT = PRIVATE + "/user/exit";
    public static final String AUTHENTICATION_PAGE = "/authenticate";
    public static final String REGISTRATION_PAGE = "/register";
    public static final String REACTION = "/reaction";
    public static final String SEARCH_PAGE = "/search";
    public static final String RESOURCE = "/resource";
}
