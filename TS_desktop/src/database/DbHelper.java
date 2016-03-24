package database;

import models.Bus;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbHelper {
    private static DbHelper sDbHelper;
    private DbHelper(){}

    public static DbHelper getInstance(){
        if (sDbHelper == null){
            sDbHelper = new DbHelper();
        }
        return sDbHelper;
    }

    public static void addBus(Bus item){
        Connection connection = null;
        Statement statement = null;

        connection = TransportDB.getConnection();
        try {
            statement = connection.createStatement();

            final String query = "insert into Bus (name, url) values('" +
                    item.getName() + "', '" + item.getUrl() + "')";

            statement.executeUpdate(query);

        } catch (Exception e) {
            e.printStackTrace();
        }

        finally {
            try {
                statement.close();
                connection.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }


    }
}
