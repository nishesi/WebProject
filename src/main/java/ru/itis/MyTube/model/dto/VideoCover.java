package ru.itis.MyTube.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Builder
@Data
public class VideoCover {
    private UUID uuid;
    private String name;
    private String watchUrl;
    private String videoCoverImgUrl;
    private ChannelCover channelCover;
    private LocalDateTime addedDate;
    private LocalTime duration;
    private Long views;
}
