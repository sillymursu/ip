package legoat.handlers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import legoat.LeGoat;

public class TaskHandlerTest {
    @Test
    public void deadlineInvalidDateTest() {
        InputStream sysInBackup = System.in;
        PrintStream sysOutBackup = System.out;
        PrintStream sysErrBackup = System.err;
        try {
            ByteArrayInputStream in = new ByteArrayInputStream("deadline test1 /by today\nbye".getBytes());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ByteArrayOutputStream err = new ByteArrayOutputStream();
            System.setIn(in);
            System.setOut(new PrintStream(out));
            System.setErr(new PrintStream(err));

            LeGoat goat = new LeGoat();
            goat.start();

            String stdout = out.toString();
            assertTrue(stdout.contains("Added Deadline"));
            assertTrue(stdout.contains("[D][ ] test1 (by: today)"));
            assertTrue(stdout.contains("yyyy mm dd <24h time>"));
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
    public void deadlineValidDateTest() {
        InputStream sysInBackup = System.in;
        PrintStream sysOutBackup = System.out;
        PrintStream sysErrBackup = System.err;
        try {
            ByteArrayInputStream in = new ByteArrayInputStream("deadline test1 /by 2026 12 23 1000\nbye".getBytes());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ByteArrayOutputStream err = new ByteArrayOutputStream();
            System.setIn(in);
            System.setOut(new PrintStream(out));
            System.setErr(new PrintStream(err));

            LeGoat goat = new LeGoat();
            goat.start();

            String stdout = out.toString();
            assertTrue(stdout.contains("Added Deadline"));
            assertTrue(stdout.contains("23rd Dec 2026"));
            assertTrue(stdout.contains("10:00"));
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
    public void deadlineInvalidNameTest() {
        InputStream sysInBackup = System.in;
        PrintStream sysOutBackup = System.out;
        PrintStream sysErrBackup = System.err;
        try {
            ByteArrayInputStream in = new ByteArrayInputStream("deadline /by today\nbye".getBytes());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ByteArrayOutputStream err = new ByteArrayOutputStream();
            System.setIn(in);
            System.setOut(new PrintStream(out));
            System.setErr(new PrintStream(err));

            LeGoat goat = new LeGoat();
            goat.start();

            String stderr = err.toString();
            assertTrue(stderr.contains("The correct format is: \"deadline <eventName> /by <deadline>\"!"));
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
    public void deadlineInvalidDeadlineTest() {
        InputStream sysInBackup = System.in;
        PrintStream sysOutBackup = System.out;
        PrintStream sysErrBackup = System.err;
        try {
            ByteArrayInputStream in = new ByteArrayInputStream("deadline test1 /by \nbye".getBytes());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ByteArrayOutputStream err = new ByteArrayOutputStream();
            System.setIn(in);
            System.setOut(new PrintStream(out));
            System.setErr(new PrintStream(err));

            LeGoat goat = new LeGoat();
            goat.start();

            String stderr = err.toString();
            assertTrue(stderr.contains("The correct format is: \"deadline <eventName> /by <deadline>\"!"));
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
    public void eventInvalidDateTest() {
        InputStream sysInBackup = System.in;
        PrintStream sysOutBackup = System.out;
        PrintStream sysErrBackup = System.err;
        try {
            ByteArrayInputStream in = new ByteArrayInputStream("event test1 /from today /to tomorrow\nbye".getBytes());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ByteArrayOutputStream err = new ByteArrayOutputStream();
            System.setIn(in);
            System.setOut(new PrintStream(out));
            System.setErr(new PrintStream(err));

            LeGoat goat = new LeGoat();
            goat.start();

            String stdout = out.toString();
            assertTrue(stdout.contains("Added Event"));
            assertTrue(stdout.contains("[E][ ] test1 (from: today | to: tomorrow)"));
            assertTrue(stdout.contains("yyyy mm dd <24h time>"));
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
    public void eventValidDateTest() {
        InputStream sysInBackup = System.in;
        PrintStream sysOutBackup = System.out;
        PrintStream sysErrBackup = System.err;
        try {
            ByteArrayInputStream in = new ByteArrayInputStream("event test1 /from 2026 12 23 1000 /to 2026 12 23 1200\nbye".getBytes());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ByteArrayOutputStream err = new ByteArrayOutputStream();
            System.setIn(in);
            System.setOut(new PrintStream(out));
            System.setErr(new PrintStream(err));

            LeGoat goat = new LeGoat();
            goat.start();

            String stdout = out.toString();
            assertTrue(stdout.contains("Added Event"));
            assertTrue(stdout.contains("[E][ ] test1"));
            assertTrue(stdout.contains("23rd Dec 2026"));
            assertTrue(stdout.contains("10:00"));
            assertTrue(stdout.contains("12:00"));
            assertTrue(stdout.contains("am"));
            assertTrue(stdout.contains("pm"));
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
    public void eventInvalidNameTest() {
        InputStream sysInBackup = System.in;
        PrintStream sysOutBackup = System.out;
        PrintStream sysErrBackup = System.err;
        try {
            ByteArrayInputStream in = new ByteArrayInputStream("event /from 2026 12 23 1000 /to 2026 12 23 1200\nbye".getBytes());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ByteArrayOutputStream err = new ByteArrayOutputStream();
            System.setIn(in);
            System.setOut(new PrintStream(out));
            System.setErr(new PrintStream(err));

            LeGoat goat = new LeGoat();
            goat.start();

            String stderr = err.toString();
            assertTrue(stderr.contains("The correct format is: \"event <eventName> /from <begin> /to <end>\"!"));
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
