package legoat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class LeGoatTest {
    @Test
    public void startTest(){
        InputStream sysInBackup = System.in;
        PrintStream sysOutBackup = System.out;
        PrintStream sysErrBackup = System.err;
        try {
            ByteArrayInputStream in = new ByteArrayInputStream("bye\n".getBytes());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ByteArrayOutputStream err = new ByteArrayOutputStream();
            System.setIn(in);
            System.setOut(new PrintStream(out));
            System.setErr(new PrintStream(err));

            LeGoat goat = new LeGoat();
            goat.start();

            String stdout = out.toString();
            assertTrue(stdout.contains("""
                       _      ___    ____    _____     ___    _______
                      | |    |  _|  / ___\\  / / \\ \\   / _ \\  |__   __|
                      | |    | |_  / / ___  | | | |  / /_\\ \\    | |
                      | |    |  _| \\ \\|_  \\ | | | | / /   \\ \\   | |
                      | |___ |_|_   \\ \\_| | | \\_/ | | |   | |   | |   _
                      |____/ |___|   \\___/  \\_____/ |_|   |_|   |_|  |_|
                      """));
            assertTrue(stdout.contains("What can I do for you?"));
            assertTrue(stdout.contains("LeGoat: LeGoat logging off!"));
        } finally {
            System.setIn(sysInBackup);
            System.setOut(sysOutBackup);
            System.setErr(sysErrBackup);
        }
    }
}
