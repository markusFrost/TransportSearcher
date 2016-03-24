package utils;

public class HelpUtils {
    public static String changeUrl(String mainUrl, String url){
        String value = mainUrl + url;
        return value.replace("//", "/" );
    }
}
