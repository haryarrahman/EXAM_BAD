package Util;

import java.sql.SQLException;

public interface DaoServiceVendor<T> extends DaoService<T> {
    T findById(int t) throws SQLException, ClassNotFoundException;
}

