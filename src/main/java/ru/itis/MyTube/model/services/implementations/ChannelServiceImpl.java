package ru.itis.MyTube.model.services.implementations;

import lombok.RequiredArgsConstructor;
import ru.itis.MyTube.auxiliary.UrlCreator;
import ru.itis.MyTube.auxiliary.enums.FileType;
import ru.itis.MyTube.auxiliary.exceptions.ServiceException;
import ru.itis.MyTube.auxiliary.exceptions.ValidationException;
import ru.itis.MyTube.auxiliary.validators.ChannelCreateValidator;
import ru.itis.MyTube.model.dao.ChannelRepository;
import ru.itis.MyTube.model.dao.UserRepository;
import ru.itis.MyTube.model.dto.Channel;
import ru.itis.MyTube.model.dto.ChannelCover;
import ru.itis.MyTube.model.dto.User;
import ru.itis.MyTube.model.dto.forms.ChannelForm;
import ru.itis.MyTube.model.services.ChannelService;
import ru.itis.MyTube.model.storage.Storage;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {

    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;

    private final Storage storage;

    private final UrlCreator urlCreator;
    private final ChannelCreateValidator channelCreateValidator;


    @Override
    public Channel getChannel(String formId) {
        long id;
        try {
            id = Long.parseLong(formId);
        } catch (NumberFormatException | NullPointerException ex) {
            throw new ServiceException("Illegal channel id.");
        }

        Optional<Channel> channelOpt;
        try {
            channelOpt = channelRepository.get(id);
            channelOpt.ifPresent(channel -> channel.getChannelCover()
                    .setChannelImgUrl(
                            urlCreator.createResourceUrl(
                                    FileType.CHANNEL_ICON,
                                    channel.getId().toString()
                            )
                    )
            );

        } catch (RuntimeException ex) {
            throw new ServiceException(ex.getMessage());
        }
        return channelOpt.orElseThrow(() -> new ServiceException("Channel not found."));
    }

    @Override
    public Long create(ChannelForm form) throws ValidationException {
        channelCreateValidator.validate(form);

        Channel channel = Channel.builder()
                .channelCover(ChannelCover.builder()
                        .name(form.getName())
                        .build())
                .info(form.getInfo())
                .owner(form.getUser())
                .build();

        Optional<ChannelCover> channel1;
        User user = form.getUser();

        try {
            channelRepository.create(channel);


            channel1 = channelRepository.get(user.getUsername());
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new ServiceException("Something go wrong, please try again later.");
        }

        Long channelId = channel1.orElseThrow(() -> new ServiceException("Сan not create channel.")).getId();

        try {
            user.setChannelId(channelId);

            userRepository.update(user);

            storage.save(FileType.CHANNEL_ICON, channelId.toString(), form.getIconPart().getInputStream());
        } catch (RuntimeException | IOException ex) {
            ex.printStackTrace();
            throw new ServiceException("Something go wrong, please try again later.");
        }
        return channelId;
    }
}
