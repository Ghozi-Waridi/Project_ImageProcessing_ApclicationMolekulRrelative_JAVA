package Database;

import java.sql.SQLException;
import java.util.List;

public interface DbOperation <T>{
    List<T> fetchAll(String namaSenyawa) throws SQLException, ClassNotFoundException;
}
