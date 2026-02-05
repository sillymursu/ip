package legoat.handlers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import legoat.LeGoat;

public class DataHandlerTest {
    @Test
    public void saveDataTest(){
        InputStream sysInBackup = System.in;
        PrintStream sysOutBackup = System.out;
        PrintStream sysErrBackup = System.err;
        try {
            ByteArrayInputStream in = new ByteArrayInputStream("todo test1\nbye".getBytes());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ByteArrayOutputStream err = new ByteArrayOutputStream();
            System.setIn(in);
            System.setOut(new PrintStream(out));
            System.setErr(new PrintStream(err));

            LeGoat goat = new LeGoat();
            goat.start();

            String stdout = out.toString();
            assertTrue(stdout.contains("Added Task"));
            assertTrue(stdout.contains("[T][ ] test1"));
            assertTrue(stdout.contains("Saved successfully!"));
            java.io.File savePath = new java.io.File("data");
            java.io.File saveFile = new java.io.File("data/LeGoatData.txt");       
            assertTrue(savePath.exists() && savePath.isDirectory());
            assertTrue(saveFile.exists() && saveFile.isFile());
        } finally {
            System.setIn(sysInBackup);
            System.setOut(sysOutBackup);
            System.setErr(sysErrBackup);
            java.io.File saveFile = new java.io.File("data/LeGoatData.txt");
            if (saveFile.exists()) {
                saveFile.delete();
            }
            java.io.File saveFolder = new java.io.File("data");
            if (saveFolder.exists() && saveFolder.isDirectory()) {
                saveFolder.delete();
            }
        }
    }

    @Test
    public void loadDataTest(){
        InputStream sysInBackup = System.in;
        PrintStream sysOutBackup = System.out;
        PrintStream sysErrBackup = System.err;
        try {
            java.io.File saveFolder = new java.io.File("data");
            saveFolder.mkdirs();
            java.io.File saveFile = new java.io.File("data/LeGoatData.txt");
            try (java.io.FileWriter fw = new java.io.FileWriter(saveFile)) {
                fw.write("T |   | test1\n");
                fw.write("D |   | test2 | now\n");
                fw.write("E |   | test3 | now | later\n");
            } catch (IOException e) {
                System.err.println("What? How did the test fail?");
            }
            ByteArrayInputStream in = new ByteArrayInputStream("list\nbye".getBytes());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ByteArrayOutputStream err = new ByteArrayOutputStream();
            System.setIn(in);
            System.setOut(new PrintStream(out));
            System.setErr(new PrintStream(err));

            LeGoat goat = new LeGoat();
            goat.start();

            String stdout = out.toString();
            assertTrue(stdout.contains("[T][ ] test1"));
            assertTrue(stdout.contains("[D][ ] test2 (by: now)"));
            assertTrue(stdout.contains("[E][ ] test3 (from: now | to: later)"));      
            assertTrue(saveFolder.exists() && saveFolder.isDirectory());
            assertTrue(saveFile.exists() && saveFile.isFile());
        } finally {
            System.setIn(sysInBackup);
            System.setOut(sysOutBackup);
            System.setErr(sysErrBackup);
            java.io.File saveFile = new java.io.File("data/LeGoatData.txt");
            if (saveFile.exists()) {
                saveFile.delete();
            }
            java.io.File saveFolder = new java.io.File("data");
            if (saveFolder.exists() && saveFolder.isDirectory()) {
                saveFolder.delete();
            }
        }
    }
}
