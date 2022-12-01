package ru.itis.MyTube.model.services.implementations;

import lombok.RequiredArgsConstructor;
import ru.itis.MyTube.auxiliary.UrlCreator;
import ru.itis.MyTube.auxiliary.enums.FileType;
import ru.itis.MyTube.auxiliary.exceptions.ServiceException;
import ru.itis.MyTube.auxiliary.exceptions.ValidationException;
import ru.itis.MyTube.auxiliary.validators.SearchValidator;
import ru.itis.MyTube.auxiliary.validators.VideoUpdateValidator;
import ru.itis.MyTube.auxiliary.validators.VideoValidator;
import ru.itis.MyTube.model.dao.VideoRepository;
import ru.itis.MyTube.model.dto.ChannelCover;
import ru.itis.MyTube.model.dto.User;
import ru.itis.MyTube.model.dto.Video;
import ru.itis.MyTube.model.dto.VideoCover;
import ru.itis.MyTube.model.dto.forms.VideoForm;
import ru.itis.MyTube.model.services.VideoService;
import ru.itis.MyTube.model.storage.Storage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;
    private final Storage storage;
    private final UrlCreator urlCreator;
    private final SearchValidator searchValidator;
    private final VideoValidator videoValidator;
    private final VideoUpdateValidator videoUpdateValidator;

    @Override
    public void addVideo(VideoForm form) throws ValidationException {
        videoValidator.validate(form);

        String uuidStr = UUID.randomUUID().toString();
        Video video1 = transferToVideo(form, uuidStr);

        try {
            videoRepository.addVideo(video1);

            storage.save(FileType.VIDEO_ICON, uuidStr, form.getIconPart().getInputStream());
            storage.save(FileType.VIDEO, uuidStr, form.getVideoPart().getInputStream());

        } catch (IOException | RuntimeException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    private Video transferToVideo(VideoForm form, String uuidStr) {
        ChannelCover channelCover = ChannelCover.builder().id(form.getChannelId()).build();

        VideoCover videoCover = VideoCover.builder()
                .name(form.getName())
                .videoCoverImgUrl(urlCreator.createResourceUrl(FileType.VIDEO_ICON, uuidStr))
                .channelCover(channelCover)
                .duration(LocalTime.of(0, 0, 0))
                .addedDate(LocalDateTime.now())
                .build();

        return Video.builder()
                .uuid(UUID.fromString(uuidStr))
                .videoCover(videoCover)
                .videoUrl(urlCreator.createResourceUrl(FileType.VIDEO, uuidStr))
                .info(form.getInfo())
                .build();
    }

    @Override
    public void updateVideo(VideoForm form) throws ValidationException {
        videoUpdateValidator.validate(form);

        try {
            UUID.fromString(form.getVideoUuid());
        } catch (RuntimeException ex) {
            throw new ServiceException("invalid video id");
        }

        Video video1 = transferToVideo(form, form.getVideoUuid());

        try {
            videoRepository.updateVideo(video1);

            if (form.getIconPart().getSize() != 0) {
                storage.save(FileType.VIDEO_ICON, form.getVideoUuid(), form.getIconPart().getInputStream());
            }
            if (form.getVideoPart().getSize() != 0) {
                storage.save(FileType.VIDEO, form.getVideoUuid(), form.getVideoPart().getInputStream());
            }

        } catch (IOException | RuntimeException e) {
            e.printStackTrace();
            throw new ServiceException("Something go wrong, please try again later.");
        }
    }

    @Override
    public void deleteVideo(String videoUuid, Long channelId) {
        UUID uuid;
        try {
            uuid = UUID.fromString(videoUuid);
        } catch (IllegalArgumentException | NullPointerException ex) {
            throw new ServiceException("Invalid video id.");
        }
        try {
            Video video = videoRepository.getVideo(uuid).orElseThrow(() -> new ServiceException("Video not found."));

            if (!video.getUuid().equals(uuid)) {
                throw new ServiceException("You can't delete this video.");
            }

            videoRepository.deleteVideo(uuid);
            storage.delete(FileType.VIDEO, uuid.toString());
            storage.delete(FileType.VIDEO_ICON, uuid.toString());

        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new ServiceException("Something go wrong, please try again later.");
        }
    }

    @Override
    public Video getVideo(String videoId) {
        UUID uuid;
        try {
            uuid = UUID.fromString(videoId);
        } catch (IllegalArgumentException | NullPointerException ex) {
            throw new ServiceException("Couldn't find or load a video.");
        }

        try {
            Optional<Video> videoOpt = videoRepository.getVideo(uuid);
            Video video = videoOpt.orElseThrow(() -> new ServiceException("Video not found."));

            video.setVideoUrl(urlCreator.createResourceUrl(
                    FileType.VIDEO,
                    video.getUuid().toString())
            );
            ChannelCover channelCover = video.getVideoCover().getChannelCover();

            channelCover.setChannelImgUrl(urlCreator.createResourceUrl(
                    FileType.CHANNEL_ICON,
                    channelCover.getId().toString()));

            return video;

        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new ServiceException("Something go wrong, please try again later.");
        }
    }

    @Override
    public List<VideoCover> getRandomVideos() {
        try {
            List<VideoCover> videoCovers = videoRepository.getRandomVideos();
            setUrls(videoCovers);

            return videoCovers;

        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new ServiceException("Something go wrong, please try again later.");
        }
    }

    @Override
    public List<VideoCover> getSubscriptionsVideos(User user) {
        if (Objects.isNull(user)) {
            throw new ServiceException("You not authorized.");
        }
        try {
            List<VideoCover> videoCovers = videoRepository.getSubscribedChannelsVideos(user.getUsername());
            setUrls(videoCovers);

            return videoCovers;

        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new ServiceException("Something go wrong, please try again later.");
        }
    }

    @Override
    public List<VideoCover> getVideosByNameSubstring(String substring) {
        try {
            searchValidator.validate(substring);
        } catch (IllegalArgumentException ex) {
            throw new ServiceException(ex.getMessage());
        }

        try {
            List<VideoCover> videoCovers = videoRepository.getVideosByName(substring);

            setUrls(videoCovers);

            return videoCovers;

        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new ServiceException("Something go wrong, please try again later.");
        }
    }

    @Override
    public List<VideoCover> getChannelVideoCovers(Long channelId) {

        try {
            List<VideoCover> channelVideos = videoRepository.getChannelVideos(channelId);
            setUrls(channelVideos);

            return channelVideos;
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new ServiceException("Something go wrong, please try again later.");
        }
    }

    private void setUrls(List<VideoCover> videoCovers) {
        videoCovers.forEach(videoCover -> {

            videoCover.setVideoCoverImgUrl(
                    urlCreator.createResourceUrl(
                            FileType.VIDEO_ICON,
                            videoCover.getUuid().toString()));
            videoCover.setWatchUrl(urlCreator.createWatchUrl(videoCover.getUuid().toString()));

            ChannelCover channelCover = videoCover.getChannelCover();
            channelCover.setChannelImgUrl(
                    urlCreator.createResourceUrl(
                            FileType.CHANNEL_ICON,
                            channelCover.getId().toString()));
            channelCover.setChannelUrl(urlCreator.createChannelUrl(channelCover.getId().toString()));
        });
    }
}
