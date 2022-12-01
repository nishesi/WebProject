package ru.itis.MyTube.model.dao;

import ru.itis.MyTube.model.dto.Channel;
import ru.itis.MyTube.model.dto.ChannelCover;

import java.util.List;
import java.util.Optional;

public interface ChannelRepository {
    List<ChannelCover> getSubscribedChannels(String username);

    Optional<Channel> get(Long id);
    Optional<ChannelCover> get(String owner_username);
    void create(Channel channel);
}
