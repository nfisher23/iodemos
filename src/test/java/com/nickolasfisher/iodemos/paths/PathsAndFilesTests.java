package com.nickolasfisher.iodemos.paths;

import com.nickolasfisher.iodemos.Utils;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Scanner;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

public class PathsAndFilesTests {

    @Test
    public void findWorkingDirectory_absolutePath() {
        Path relativePath = Paths.get("");
        Path absolutePath = relativePath.toAbsolutePath();

        System.out.println("absolute path: " + absolutePath.toString());
        assertNotNull(absolutePath);
    }

    @Test
    public void someUsefulStuff() {
        Path absolutePath = Paths.get("").toAbsolutePath();

        Path parentOfAbsolutePath = absolutePath.getParent();
        System.out.println(parentOfAbsolutePath);
        assertNotNull(parentOfAbsolutePath);

        Path fileNameOfPath = parentOfAbsolutePath.getFileName();
        System.out.println(fileNameOfPath);
        assertNotNull(fileNameOfPath);

        Path root = absolutePath.getRoot();
        System.out.println(root); // different on windows vs unix
        assertNotNull(root);
    }

    @Test
    public void getScannerFromPath() throws Exception {
        Path pathToExampleFile = Paths.get(Utils.simpleExampleFilePath);

        try (Scanner scanner = new Scanner(pathToExampleFile)) {
            System.out.println(scanner.useDelimiter("\\Z").next());
            assertNotNull(scanner);
        }
    }

    @Test
    public void files_readAllBytes_thenStrings() throws Exception {
        Path pathToExampleFile = Paths.get(Utils.simpleExampleFilePath);

        byte[] bytes = Files.readAllBytes(pathToExampleFile);
        String content = new String(bytes, StandardCharsets.UTF_8);

        assertEquals(content, "this is some text");
    }

    @Test
    public void files_sequenceOfLines() throws Exception {
        Path pathToExampleFile = Paths.get(Utils.simpleExampleFilePath);

        List<String> allLines = Files.readAllLines(pathToExampleFile);

        assertEquals("this is some text", allLines.get(0));
    }

    @Test
    public void files_writeStringToFile() throws Exception {
        String stuffToWrite = "some new text";

        Path newFilePath = Paths.get(Utils.pathToResources + "new-file-with-text.txt");

        Files.write(newFilePath, stuffToWrite.getBytes(StandardCharsets.UTF_8));

        assertEquals(stuffToWrite, Utils.readFileAsText(newFilePath));
    }

    @Test
    public void files_appendToFile() throws Exception {
        String beginnings = "some next text\n";

        Path newFilePath = Paths.get(Utils.pathToResources, "append-string-ex.txt");
        Files.write(newFilePath, beginnings.getBytes(StandardCharsets.UTF_8));

        // append
        for (int i = 0; i < 5; i++) {
            Files.write(newFilePath, beginnings.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
        }

        String writtenText = Utils.readFileAsText(newFilePath);
        assertTrue(writtenText.startsWith(beginnings + beginnings));
    }
}
