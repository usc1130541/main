import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class testing {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    private Player p;


    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);


    }

    /**
     *
     */
    @Test
    public void out() {
//Testing the game by starting and making sure the test says its not over
        Game gaze = new Game();
        boolean a = gaze.isGameOver();

        assertFalse(a);
    }

//Testing different function by sending values
    @Test
    public void out1() {

        Game gaze = new Game();
       gaze.setIsInGame(true);

        assertTrue(gaze.isInGame());


        }



    /**
     * response testing
     * Made to fail
     */

    @Test
    void testCreate() {

        Game gaze = new Game();
        boolean a = gaze.isGameOver();

        assertTrue(a);    }

    @Test
    /**
     * This object is for checking different pre provided values
     *
     */
    void testCreate1() {
        Game gaze = new Game();

        assertEquals(gaze.stamina,12 );


    }

    @Test
    /**
     * This object is for checking basic configurations
     *
     */

    void testCreate2() {
        Game gaze = new Game();

        assertEquals(gaze.level,0 );

    }
}