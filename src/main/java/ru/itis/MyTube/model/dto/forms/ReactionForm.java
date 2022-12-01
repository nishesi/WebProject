package ru.itis.MyTube.model.dto.forms;

import lombok.Builder;
import lombok.Data;
import ru.itis.MyTube.model.dto.User;

@Data
@Builder
public class ReactionForm {
    String videoUuid;
    String reaction;
    User user;
}
