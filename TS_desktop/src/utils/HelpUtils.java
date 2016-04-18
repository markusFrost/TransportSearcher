package utils;

import database.BusStopListDB;
import database.DbHelper;
import database.StationTimeDepartDB;
import loaders.BusesNamesLoader;
import models.Bus;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HelpUtils {

    public static void  testQuery(){
        final String strTime1 = "13:25";
        final String strTime2  = "14:35";

        final String stationInName = "Педагогическая ул.";
        final String stationOutName = "Ст. переливания крови";

        final long timeMin = HelpUtils.convertStringToLong(strTime1);
        final long timeMax = HelpUtils.convertStringToLong(strTime2);
        final int dayType = 1;

        DbHelper dbHelper = DbHelper.getInstance();

        // LOAD TIME IDS
        List<Integer> listTimeDepartIds =
                dbHelper.getTimeDepartSegment(timeMin, timeMax, dayType);

        // LOAD BUS IDS
        final int stationInId = dbHelper.getStationByName(stationInName);
        final int stationOutId = dbHelper.getStationByName(stationOutName);

        List<Integer> listBusIds =
                dbHelper.getBusesByStationsIds(stationInId, stationOutId);

        //LOAD ROUTS IDS
        List<Integer> listRoutIds =
                dbHelper.getRoutsByBusIds(listBusIds);

        //LOAD LIST BUS STOP IDS
        BusStopListDB busStopListDB = BusStopListDB.getInstance();
        List<Integer> listBusStopIds =
                busStopListDB.getBusStopListIds(listBusIds, stationInId, stationOutId);


        // LOAD BUS NAMES WHICH COME IN THE TIME SEGMENT
        StationTimeDepartDB stationTimeDepartDB = StationTimeDepartDB.getInstance();

        List<String> listBusNameDriveInTime =
                stationTimeDepartDB.getListBusNameDriveInTime(listBusStopIds, listTimeDepartIds);

        listTimeDepartIds.size();
    }

    public static String loadHtmlFromFile(String pathToLocalFile){
        String html = "";
        try {
            html = new Scanner(new File(pathToLocalFile)).useDelimiter("\\Z").next();
        } catch (FileNotFoundException e) {}
        return html;
    }
    public static String changeUrl(String url){
        String value = BusesNamesLoader.MAIN_URL + url.replace("moscow/", "");
        return value.replace("//", "/" );
    }

    public static String convertBusName(Bus bus){
        final String busName = bus.getName();

        String reg = "[\\d\\w]+";
        Pattern p = Pattern.compile( reg );
        Matcher m = p.matcher(busName);

        if(m.find()){
            return m.group();
        }
        return "NONE";

    }

    public static long convertStringToLong(String timeString){

        final long fixTime = 1460636025804l;
        int hour = getHour(timeString);
        int minute = getMinute(timeString);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(fixTime);

        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        return calendar.getTimeInMillis();
    }

    private static int getHour(String date){
        String reg = "\\d+\\:";
        Pattern p = Pattern.compile( reg );
        Matcher m = p.matcher( date );

        if(m.find()){
            return Integer.parseInt(m.group().replace(":", ""));
        }
        return 0;
    }

    private static int getMinute(String date){
        String reg = "\\:\\d+";
        Pattern p = Pattern.compile( reg );
        Matcher m = p.matcher( date );

        if(m.find()){
            return Integer.parseInt(m.group().replace(":", ""));
        }
        return 0;
    }



}
