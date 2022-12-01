package ru.itis.MyTube.model.services;

import ru.itis.MyTube.auxiliary.exceptions.ServiceException;
import ru.itis.MyTube.auxiliary.exceptions.ValidationException;
import ru.itis.MyTube.model.dto.forms.ReactionForm;

public interface ReactionService {
    String updateReaction(ReactionForm form) throws ServiceException, ValidationException;
    String getReaction(ReactionForm form) throws ServiceException, ValidationException;
}
