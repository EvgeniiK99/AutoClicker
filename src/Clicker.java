import java.awt.*;
import java.awt.event.InputEvent;

public class Clicker {

    public static void clicker(int count, int sleepTime, boolean isInfinite) {
        try {

            Robot r = new Robot();
            if (isInfinite) {


                while (Main.isNotStop) {
                    int button = InputEvent.BUTTON1_DOWN_MASK;
                    r.mousePress(button);
                    Thread.sleep(sleepTime);
                    r.mouseRelease(button);
                    Thread.sleep(sleepTime);
                }
            } else {
                for (int i = 0; Main.isNotStop && i < count; i++) {
                    int button = InputEvent.BUTTON1_DOWN_MASK;
                    r.mousePress(button);
                    Thread.sleep(10);
                    r.mouseRelease(button);
                    Thread.sleep(sleepTime);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
