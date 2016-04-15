package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StationTimeDepartDB {

    private static StationTimeDepartDB sInstance;

    private StationTimeDepartDB(){}

    public static StationTimeDepartDB getInstance(){
        if(sInstance == null){
            sInstance = new StationTimeDepartDB();
        }
        return sInstance;
    }

    private String formTimeDepartSegment(List<Integer> listTimeDepartIds){
        String segment = "";
        for(int i = 0; i < listTimeDepartIds.size(); i++){
            if(i != 0){
                segment += " or (time_depart_id = " + listTimeDepartIds.get(i) + ")";
            }
            else{
                segment += " (time_depart_id = " + listTimeDepartIds.get(i) + ")";
            }
        }
        return segment;
    }

    private String formStationTimeDepartIdsSegment(List<Integer> listStationTimeDepartIds){
        String segment = "";
        for(int i = 0; i < listStationTimeDepartIds.size(); i++){
            if(i != 0){
                segment += ", " + listStationTimeDepartIds.get(i);
            }
            else{
                segment += " ( " +  listStationTimeDepartIds.get(i) ;
            }
            if(i == listStationTimeDepartIds.size() - 1){
                segment += ")";
            }
        }
        return segment;
    }

    private List<String> getListBusNames(List<Integer> listStationTimeDepartIds){
        List<String> listBusNames = new ArrayList<>();

        Connection connection = TransportDB.getConnection();
        try {
            Statement statement = connection.createStatement();

            final String sql = "select distinct Bus.name as bus_name from StationTimeDepart, BusStopList," +
                    " Rout, Bus where\n" +
                    "StationTimeDepart._id in " + formStationTimeDepartIdsSegment(listStationTimeDepartIds)
                    + " and\n" +
                    "(BusStopList._id = StationTimeDepart.station_list_id) and\n" +
                    "(BusStopList.rout_id = Rout._id) and\n" +
                    "(Bus._id = Rout.bus_id)";

            ResultSet rs = statement.executeQuery(sql);

            if (rs.next()) {
                String busName = rs.getString("bus_name");
                listBusNames.add(busName);
            }
            rs.close();
        }catch (Exception e){}
        return listBusNames;
    }


    public List<String> getListBusNameDriveInTime(List<Integer> listBusStopIds, List<Integer> listTimeDepartIds) {
        List<Integer> listStationTimeDepartIds = new ArrayList<>();

        String segment = formTimeDepartSegment(listTimeDepartIds);

        Connection connection = TransportDB.getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = null;

            for(int busStopId : listBusStopIds) {
                String sql = String.format(
                        "select _id from StationTimeDepart where\n" +
                                "station_list_id = %d \n" +
                                "and (\n" + segment + "\n)", busStopId);
                rs = statement.executeQuery(sql);

                if (rs.next()) {
                    int _id = rs.getInt("_id");
                    listStationTimeDepartIds.add(_id);
                }
                rs.close();
            }
        } catch (Exception e) {}
        return getListBusNames(listStationTimeDepartIds);
    }
}
