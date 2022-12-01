package ru.itis.MyTube.model.dao.implementations;

import lombok.RequiredArgsConstructor;
import ru.itis.MyTube.model.dao.ReactionRepository;
import ru.itis.MyTube.model.dto.Reaction;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

@RequiredArgsConstructor
public class ReactionRepositoryJdbcImpl extends AbstractRepository implements ReactionRepository {
    private static final String SQL_UPDATE_REACTION = "update viewing set reaction = ? where video_uuid = ? and username = ?";
    private static final String SQL_IS_VIEWING_EXISTS = "select * from viewing where video_uuid = ? and username = ?";
    private static final String SQL_CREATE_VIEWING = "insert into viewing (username, video_uuid, reaction) VALUES (?, ?, ?)";
    private static final String SQL_GET_VIEWING = "select * from viewing where username = ? and video_uuid = ?";

    private static final Function<ResultSet, Reaction> REACTION_MAPPER = set -> {
        try {
            return Reaction.builder()
                    .videoUuid((UUID) set.getObject("video_uuid"))
                    .username(set.getString("username"))
                    .reaction(set.getByte("reaction"))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    };
    private static final String SQL_GET_VIDEO_REACTIONS = "select likes, dislikes from videos_inf where uuid = ?";
    private final DataSource dataSource;

    @Override
    public void setReaction(Reaction reaction) throws RuntimeException {
        if (isReactionExists(reaction.getVideoUuid(), reaction.getUsername())) {
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_REACTION)) {

                preparedStatement.setByte(1, reaction.getReaction());
                preparedStatement.setObject(2, reaction.getVideoUuid());
                preparedStatement.setString(3, reaction.getUsername());

                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            createReaction(reaction);
        }
    }

    @Override
    public boolean isReactionExists(UUID videoUuid, String username) throws RuntimeException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_IS_VIEWING_EXISTS)) {

            preparedStatement.setObject(1, videoUuid);
            preparedStatement.setString(2, username);

            return preparedStatement.executeQuery().next();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createReaction(Reaction reaction) throws RuntimeException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE_VIEWING)) {

            preparedStatement.setString(1, reaction.getUsername());
            preparedStatement.setObject(2, reaction.getVideoUuid());
            preparedStatement.setByte(3, reaction.getReaction());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Byte> getReaction(UUID videoUuid, String username) throws RuntimeException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_VIEWING)) {

            preparedStatement.setString(1, username);
            preparedStatement.setObject(2, videoUuid);

            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next() ?
                    Optional.of(resultSet.getByte("reaction")) :
                    Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<String, Long> getVideoReactions(UUID videoUuid) throws RuntimeException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_VIDEO_REACTIONS)) {

            preparedStatement.setObject(1, videoUuid);

            ResultSet resultSet = preparedStatement.executeQuery();
            Map<String, Long> reactions = new HashMap<>();

            if (resultSet.next()) {
                reactions.put("likes", resultSet.getLong("likes"));
                reactions.put("dislikes", resultSet.getLong("dislikes"));
            }
            return reactions;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
