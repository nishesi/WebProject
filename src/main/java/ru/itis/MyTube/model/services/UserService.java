package ru.itis.MyTube.model.services;

import ru.itis.MyTube.auxiliary.exceptions.ServiceException;
import ru.itis.MyTube.auxiliary.exceptions.ValidationException;
import ru.itis.MyTube.model.dto.User;
import ru.itis.MyTube.model.dto.forms.AuthenticationForm;
import ru.itis.MyTube.model.dto.forms.RegistrationForm;
import ru.itis.MyTube.model.dto.forms.SubscribeForm;
import ru.itis.MyTube.model.dto.forms.UserUpdateForm;

import java.util.UUID;

public interface UserService {
    void save(RegistrationForm form) throws ServiceException, ValidationException;

    User get(AuthenticationForm form) throws ServiceException, ValidationException;

    void update(UserUpdateForm form, User user) throws ServiceException, ValidationException;

    boolean isSubscribed(User user, Long channelId) throws ServiceException;

    Byte getUserReaction(UUID videoUuid, String username) throws ServiceException;

    void userChannel(SubscribeForm subscribeForm) throws ServiceException;
}
