package ru.itis.MyTube.model.dao.implementations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public abstract class AbstractRepository {
    protected <T> List<T> transfer(ResultSet set, Function<ResultSet, T> mapper) throws SQLException {
        List<T> list = new ArrayList<>(set.getFetchSize());

        while (set.next()) {
            list.add(mapper.apply(set));
        }

        return list;
    }
}
