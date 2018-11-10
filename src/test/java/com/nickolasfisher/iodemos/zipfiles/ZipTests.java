package com.nickolasfisher.iodemos.zipfiles;

import com.nickolasfisher.iodemos.Utils;
import com.nickolasfisher.iodemos.inputstreams.InputStreamsTests;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import static org.junit.Assert.assertEquals;

public class ZipTests {

    @Test
    public void writeZip() throws Exception {
        String pathToFile = Utils.pathToResources + "zip-output-ex.zip";
        String textToWrite = "something to zip up";
        writeAndZipText(pathToFile, textToWrite);

        String allTextWritten = readAllTextFromZip(pathToFile);

        assertEquals(textToWrite, allTextWritten);
    }

    private void writeAndZipText(String fullPath, String textToWrite) throws Exception {
        try (FileOutputStream fileOutputStream = new FileOutputStream(fullPath)) {
            try (ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream)) {
                ZipEntry zipEntry = new ZipEntry("some-single-file.txt");
                zipOutputStream.putNextEntry(zipEntry);

                for (int charIndex = 0; charIndex < textToWrite.length(); charIndex++) {
                    zipOutputStream.write(textToWrite.charAt(charIndex));
                }

                zipOutputStream.closeEntry();
            }
        }
    }

    private String readAllTextFromZip(String fullPath) throws Exception {
        StringBuilder builder = new StringBuilder();
        try (ZipFile zipFile = new ZipFile(fullPath)) {
            Enumeration<? extends ZipEntry> what = zipFile.entries();
            while (what.hasMoreElements()) {
                ZipEntry zipEntry = what.nextElement();
                try (InputStream inputStream = zipFile.getInputStream(zipEntry)) {
                    while (inputStream.available() != 0) {
                        builder.append((char) inputStream.read());
                    }
                }
            }
        }
        return builder.toString();
    }
}
