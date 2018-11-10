package com.nickolasfisher.iodemos.inputstreams;

import org.junit.Test;

import java.io.*;

import static com.nickolasfisher.iodemos.Utils.simpleExampleFilePath;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class InputStreamsTests {


    @Test
    public void fileInputStream_ex() throws Exception {
        try (InputStream fileInputStream = new FileInputStream(simpleExampleFilePath)) {
            assertEquals('t', fileInputStream.read());
            assertEquals('h', fileInputStream.read());
            assertEquals('i', fileInputStream.read());
            assertEquals('s', fileInputStream.read());
        }
    }

    @Test
    public void dataInputStream_ex() throws Exception {
        try (InputStream fileInputStream = new FileInputStream(simpleExampleFilePath);
             DataInputStream dataInputStream = new DataInputStream(fileInputStream)) {
                // "This method is suitable for reading bytes written by the writeDouble method of interface DataOutput"
                // namely--this is not the right application
                double readValue = dataInputStream.readDouble();
                assertTrue(readValue > 0);
        }
    }

    @Test
    public void bufferingData_ex() throws Exception {
        try (InputStream fileInputStream = new FileInputStream(simpleExampleFilePath)) {
            try (BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream)) {

                final int totalAvailable = bufferedInputStream.available();
                String expectedText = "this is some text";
                for (int i = 0; i < totalAvailable; i++) {
                    int read = bufferedInputStream.read();
                    System.out.println((char)read);
                    assertEquals(expectedText.charAt(i), read);
                }

            }
        }
    }

    @Test
    public void pushbackInputStream_ex() throws Exception {
        try (InputStream fileInputStream = new FileInputStream(simpleExampleFilePath)) {
            try (DataInputStream dataInputStream = new DataInputStream(fileInputStream)) {
                try (PushbackInputStream pushbackInputStream = new PushbackInputStream(dataInputStream)) {
                    assertEquals('t', pushbackInputStream.read());
                    pushbackInputStream.unread('t');
                    assertEquals('t', pushbackInputStream.read());
                }
            }
        }
    }


}
