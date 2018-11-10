package com.nickolasfisher.iodemos.creatingandtraversingio;

import com.nickolasfisher.iodemos.Utils;
import org.junit.Test;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class CreateAndTraverseIOTests {

    @Test
    public void files_inputStreaming() throws Exception {
        Path pathToExampleFile = Paths.get(Utils.simpleExampleFilePath);

        try (InputStream inputStream = Files.newInputStream(pathToExampleFile)) {
            int readValue = inputStream.read();

            assertEquals('t', readValue);
        }
    }

    @Test
    public void creatingDirsAndFiles_ex() throws Exception {
        Path pathToNewDir = Paths.get(Utils.pathToResources + "new-directory-to-create");

        Files.deleteIfExists(pathToNewDir);

        assertFalse(Files.isDirectory(pathToNewDir));

        Files.createDirectories(pathToNewDir);

        assertTrue(Files.isDirectory(pathToNewDir));
    }

    @Test
    public void creatingDirectories_intermediateParentDirectories() throws Exception {
        Path newChainedPath = Paths.get(Utils.pathToResources + "parent-dir/sub-dir");

        Files.deleteIfExists(newChainedPath);

        assertFalse(Files.isDirectory(newChainedPath));

        Files.createDirectories(newChainedPath);

        assertTrue(Files.isDirectory(newChainedPath));
    }

    @Test
    public void files_getAttributes() throws Exception {
        Path toExistingFile = Paths.get(Utils.simpleExampleFilePath);

        BasicFileAttributes attributes = Files.readAttributes(toExistingFile, BasicFileAttributes.class);

        assertTrue(attributes.isRegularFile());
        assertEquals(17, attributes.size());
    }

    @Test
    public void visitingDirectories_ex() throws Exception {
        try (Stream<Path> entries = Files.list(Paths.get(Utils.pathToResources))) {
            System.out.println("counting via list");
            long count = entries.peek(System.out::println).count();
            assertTrue(count > 0);
        }
    }

    @Test
    public void visitingDirectories_walkSubDirectories() throws Exception {
        try (Stream<Path> entries  = Files.walk(Paths.get(Utils.pathToResources))) {
            System.out.println("counting via walk");
            long count = entries.peek(System.out::println).count();
            assertTrue(count > 0);
        }
    }
}
