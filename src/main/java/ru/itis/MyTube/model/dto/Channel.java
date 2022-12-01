package ru.itis.MyTube.model.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Channel {
    private Long id;
    private ChannelCover channelCover;
    private User owner;
    private Long countOfSubscribers;
    private String info;
}
