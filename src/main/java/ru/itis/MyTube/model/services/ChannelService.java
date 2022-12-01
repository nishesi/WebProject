package ru.itis.MyTube.model.services;

import ru.itis.MyTube.auxiliary.exceptions.ServiceException;
import ru.itis.MyTube.auxiliary.exceptions.ValidationException;
import ru.itis.MyTube.model.dto.Channel;
import ru.itis.MyTube.model.dto.forms.ChannelForm;

public interface ChannelService {
    Channel getChannel(String formId) throws ServiceException;

    Long create(ChannelForm form) throws ServiceException, ValidationException;
}
