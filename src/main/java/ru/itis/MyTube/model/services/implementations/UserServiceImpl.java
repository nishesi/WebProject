package ru.itis.MyTube.model.services.implementations;

import lombok.RequiredArgsConstructor;
import ru.itis.MyTube.auxiliary.PassPerformer;
import ru.itis.MyTube.auxiliary.UrlCreator;
import ru.itis.MyTube.auxiliary.enums.FileType;
import ru.itis.MyTube.auxiliary.exceptions.ServiceException;
import ru.itis.MyTube.auxiliary.exceptions.ValidationException;
import ru.itis.MyTube.auxiliary.validators.AuthenticationValidator;
import ru.itis.MyTube.auxiliary.validators.RegistrationValidator;
import ru.itis.MyTube.auxiliary.validators.UserUpdateValidator;
import ru.itis.MyTube.model.dao.ReactionRepository;
import ru.itis.MyTube.model.dao.UserRepository;
import ru.itis.MyTube.model.dto.User;
import ru.itis.MyTube.model.dto.forms.AuthenticationForm;
import ru.itis.MyTube.model.dto.forms.RegistrationForm;
import ru.itis.MyTube.model.dto.forms.SubscribeForm;
import ru.itis.MyTube.model.dto.forms.UserUpdateForm;
import ru.itis.MyTube.model.services.UserService;
import ru.itis.MyTube.model.storage.Storage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ReactionRepository reactionRepository;
    private final Storage storage;
    private final UrlCreator urlCreator;
    private final UserUpdateValidator userUpdateValidator;
    private final RegistrationValidator registrationValidator;
    private final AuthenticationValidator authenticationValidator;

    @Override
    public void save(RegistrationForm form) throws ValidationException {
        registrationValidator.validate(form);
        User user = User.builder()
                .username(form.getUsername())
                .password(PassPerformer.hash(form.getPassword()))
                .firstName(form.getFirstName())
                .lastName(form.getLastName())
                .birthdate(LocalDate.parse(form.getBirthdate()))
                .country(form.getCountry())
                .build();
        try {
            userRepository.save(user);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new ServiceException("Something go wrong, please try again later.");
        }
    }

    @Override
    public User get(AuthenticationForm form) throws ServiceException, ValidationException {
        authenticationValidator.validate(form);
        Optional<User> userOpt;

        try {
            userOpt = userRepository.get(
                    form.getUsername(),
                    PassPerformer.hash(form.getPassword())
            );
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new ServiceException("Something go wrong, please try again later.");
        }
        
        User user = userOpt.orElseThrow(() -> new ServiceException("User not found."));
        user.setUserImgUrl(urlCreator.createResourceUrl(FileType.USER_ICON, user.getUsername()));
        return user;
    }

    @Override
    public void update(UserUpdateForm form, User user) throws ValidationException, ServiceException {
        userUpdateValidator.validate(form);

        User updatedUser = User.builder()
                .username(user.getUsername())
                .password(PassPerformer.hash(form.getPassword()))
                .firstName(form.getFirstName())
                .lastName(form.getLastName())
                .birthdate(LocalDate.parse(form.getBirthdate()))
                .country(form.getCountry())
                .build();
        try {
            userRepository.update(updatedUser);
            if (form.getIconPart().getSize() != 0) {
                storage.save(FileType.USER_ICON, user.getUsername(), form.getIconPart().getInputStream());
            }
        } catch (RuntimeException | IOException ex) {
            ex.printStackTrace();
            throw new ServiceException("Something go wrong, please try again later.");
        }
    }

    @Override
    public boolean isSubscribed(User user, Long channelId) {
        if (Objects.isNull(user) || Objects.isNull(user.getUsername()) || Objects.isNull(channelId)) {
            return false;
        }

        try {
            return userRepository.isSubscribed(user.getUsername(), channelId);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new ServiceException("Something go wrong, please try again later.");
        }
    }

    @Override
    public Byte getUserReaction(UUID videoUuid, String username) {
        try {
            return reactionRepository.getReaction(videoUuid, username).orElse((byte) 0);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new ServiceException("Something go wrong, please try again later.");
        }
    }

    @Override
    public void userChannel(SubscribeForm form) throws ServiceException {
        if (Objects.isNull(form.getUser()) ||
                Objects.isNull(form.getToSubscribe()) ||
                form.getToSubscribe().equals("") ||
                Objects.isNull(form.getChannelId())
        ) {
            throw new ServiceException("Can not to subscribe");
        }
        long channelId;
        boolean toSubscribe;
        try {
            channelId = Long.parseLong(form.getChannelId());
            toSubscribe = Boolean.parseBoolean(form.getToSubscribe());

        } catch (RuntimeException ex) {
            throw new ServiceException("Can not to subscribe");
        }

        if (toSubscribe) {
            subscribe(form.getUser(), channelId);
        } else {
            unsubscribe(form.getUser(), channelId);
        }
    }

    public void subscribe(User user, long channelId) throws ServiceException {

        try {
            userRepository.subscribe(user.getUsername(), channelId);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new ServiceException("Something go wrong, please try again later.");
        }
    }

    public void unsubscribe(User user, long channelId) throws ServiceException {

        try {
            userRepository.unsubscribe(user.getUsername(), channelId);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new ServiceException("Something go wrong, please try again later.");
        }
    }
}
