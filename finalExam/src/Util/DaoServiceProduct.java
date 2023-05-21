package Util;

import java.sql.SQLException;

public interface DaoServiceProduct<T> extends DaoService<T> {
    T findById(int t) throws SQLException, ClassNotFoundException;

    int updateData(T t) throws  SQLException, ClassNotFoundException;
}
