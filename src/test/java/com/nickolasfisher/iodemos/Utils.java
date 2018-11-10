package com.nickolasfisher.iodemos;

import java.nio.file.Path;
import java.util.Scanner;

public class Utils {
    public static String pathToResources = "src/test/resources/";
    public static String simpleExampleFilePath = pathToResources + "simple_example_file.txt";

    public static String readFileAsText(Path pathToFile) throws Exception {
        return new Scanner(pathToFile).useDelimiter("\\Z").next();
    }
}
