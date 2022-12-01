package ru.itis.MyTube.auxiliary;

import lombok.RequiredArgsConstructor;
import ru.itis.MyTube.auxiliary.enums.FileType;

import static ru.itis.MyTube.auxiliary.constants.UrlPatterns.*;

@RequiredArgsConstructor
public class UrlCreator {
    private final String contextPath;


    public String createResourceUrl(FileType fileType, String id) {
        String url = contextPath + RESOURCE + "?id=" + id + "&fileType=";

        return url + fileType.getType();
    }

    public String createWatchUrl(String videoId) {
        return contextPath + VIDEO + "?uuid=" + videoId;
    }

    public String createChannelUrl(String channelId) {
        return contextPath + CHANNEL + "?id=" + channelId;
    }
}
