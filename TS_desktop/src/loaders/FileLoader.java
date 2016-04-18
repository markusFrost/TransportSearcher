package loaders;

import models.Bus;
import utils.HelpUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileLoader {
    private static FileLoader ourInstance = new FileLoader();

    public static FileLoader getInstance() {
        return ourInstance;
    }

    private  static final String PATH_TO_BUSES_FOLDER = "C:\\Users\\andrey.vystavkin\\Documents\\GitProjects\\TS_desktop\\assets\\buses";

    private static final String ALL_BUSES_NAME = "allBuses";

    private FileLoader() {}

    public void writeToFileHtmlCodeFromBusPage(final Bus bus){
        try {
            final String getPapth = PATH_TO_BUSES_FOLDER +
                    File.separator + HelpUtils.convertBusName(bus) + ".txt";
            PrintWriter printWriter = new PrintWriter(getPapth);

            printWriter.println(bus.getHtmlCode());
            printWriter.close();
        } catch (FileNotFoundException e) {
            e.getMessage();
        }
    }

    public void writeToFileHtmlCodeFromBusesListPage(String htmlCode){
        try {
            PrintWriter printWriter = new PrintWriter(PATH_TO_BUSES_FOLDER +
                    File.separator + ALL_BUSES_NAME + ".txt");

            printWriter.println(htmlCode);
            printWriter.close();
        } catch (FileNotFoundException e) {
            e.getMessage();
        }
    }

    public String getHtmlCodeFromMainPageTextFile(){
        String html = "";
        try {
            html = new Scanner(new File(PATH_TO_BUSES_FOLDER +
                    File.separator + ALL_BUSES_NAME + ".txt")).useDelimiter("\\Z").next();
        } catch (FileNotFoundException e) {}
        return html;
    }

    public String getHtmlCodeFromBusPageTextFile(Bus bus){
        final String fileBusName = bus.getName();
        String html = "";
        try {
            html = new Scanner(new File(PATH_TO_BUSES_FOLDER +
                    File.separator + fileBusName + ".txt")).useDelimiter("\\Z").next();
        } catch (FileNotFoundException e) {}
        return html;
    }
}
