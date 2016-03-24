package database;

import java.sql.Connection;
import java.sql.DriverManager;

public class TransportDB {
    private static Connection sConnection;
    private static final String dbPath = "C:\\Java Projects\\TransportSearcher\\db\\Transport.db";

    public static Connection getConnection(){
        if (sConnection == null){
            try {
                Class.forName("org.sqlite.JDBC");
                sConnection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return sConnection;
    }
}
