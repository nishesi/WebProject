package ru.itis.MyTube.model.storage;

import ru.itis.MyTube.auxiliary.enums.FileType;
import ru.itis.MyTube.auxiliary.exceptions.StorageException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class FileStorageImpl implements Storage {

    private static final String VIDEO_TYPE = ".mp4";
    private static final String IMAGE_TYPE = ".jpg";

    private static final Path REPOSITORY = Paths.get("D://MyTube");

    private static final Path VIDEO_STORAGE = Paths.get("videoStorage");
    private static final Path VIDEO_ICON_STORAGE = Paths.get("imageStorage/videoIcons");
    private static final Path CHANNEL_ICON_STORAGE = Paths.get("imageStorage/channelIcons");
    private static final Path USER_ICON_STORAGE = Paths.get("imageStorage/userIcons");
    private static final File USER_DEFAULT_ICON =
            REPOSITORY.resolve(USER_ICON_STORAGE).resolve("default" + IMAGE_TYPE).toFile();
    private static final File CHANNEL_DEFAULT_ICON =
            REPOSITORY.resolve(CHANNEL_ICON_STORAGE).resolve("default" + IMAGE_TYPE).toFile();

    @Override
    public InputStream get(FileType fileType, String id) {
        if (fileType == null || id == null) {
            throw new StorageException("fileType" + fileType + ", id" + id);
        }

        File file = getFile(fileType, id, false);

        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new StorageException("file not found");
        }
    }

    @Override
    public void save(FileType fileType, String id, InputStream resource) {
        if (fileType == null || id == null || resource == null) {
            throw new StorageException("fileType = " + fileType + ", id = " + id + ", name = " + resource);
        }

        File file = getFile(fileType, id, true);

        try  {
            file.createNewFile();
            OutputStream outputStream = Files.newOutputStream(file.toPath());
            resource.transferTo(outputStream);

            outputStream.flush();
            outputStream.close();

        } catch (IOException e) {
            throw new RuntimeException("internal problem");
        }
    }

    @Override
    public void delete(FileType fileType, String id) {
        if (Objects.isNull(fileType) || Objects.isNull(id)) {
            throw new StorageException("fileType = " + fileType + ", id = " + id);
        }
        File file = getFile(fileType, id, true);

        file.delete();
    }

    private File getFile(FileType fileType, String id, boolean real) {

        switch (fileType) {
            case VIDEO:
                return REPOSITORY.resolve(VIDEO_STORAGE).resolve(id + VIDEO_TYPE).toFile();
            case VIDEO_ICON:
                return REPOSITORY.resolve(VIDEO_ICON_STORAGE).resolve(id + IMAGE_TYPE).toFile();
            case CHANNEL_ICON:
                File file = REPOSITORY.resolve(CHANNEL_ICON_STORAGE).resolve(id + IMAGE_TYPE).toFile();
                return (file.exists() || real) ? file : CHANNEL_DEFAULT_ICON;
            case USER_ICON:
                File icon = REPOSITORY.resolve(USER_ICON_STORAGE).resolve(id + IMAGE_TYPE).toFile();
                return (icon.exists() || real) ? icon : USER_DEFAULT_ICON;
            default:
                throw new StorageException("unknown fileType");
        }
    }
}
