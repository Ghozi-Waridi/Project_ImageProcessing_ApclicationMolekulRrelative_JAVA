package Database.OperationDatabase;

import Database.DbOperation;
import Database.MyConnection;
import Util.Senyawa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AmbilData  implements DbOperation<Senyawa> {
    List<Senyawa> dataSenyawa = new ArrayList<>();
    @Override
    public List fetchAll(String namaSenyawa) throws SQLException, ClassNotFoundException {
        String QUERY = "SELECT * FROM senyawa WHERE label = ?";
        try(Connection conn = MyConnection.connnect() /* Object (Connection)*/){
            try(PreparedStatement ps = conn.prepareStatement(QUERY) /* Object (PreparedStatemnt)*/ ){
                ps.setString(1, namaSenyawa);
                try(ResultSet rs = ps.executeQuery() /* Object (ResultSet)*/ ){
                    while (rs.next()){
                        // Object (Senyawa)
                        Senyawa senyawa = new Senyawa();
                        senyawa.setId(rs.getInt("id"));
                        senyawa.setArSimbol(rs.getNString("label"));
                        senyawa.setArValue(rs.getDouble("value"));
                        dataSenyawa.add(senyawa);
                    }
                }
            }
        }
        return dataSenyawa;
    }
}
