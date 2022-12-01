package ru.itis.MyTube.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class ChannelCover {
    //name of the icon file
    private Long id;
    private String channelUrl;
    private String name;
    private String channelImgUrl;
}
