package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HelpUtils {

    public static String loadHtmlFromFile(String pathToLocalFile){
        String html = "";
        try {
            html = new Scanner(new File(pathToLocalFile)).useDelimiter("\\Z").next();
        } catch (FileNotFoundException e) {}
        return html;
    }
    public static String changeUrl(String mainUrl, String url){
        String value = mainUrl + url.replace("moscow/", "");
        return value.replace("//", "/" );
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
