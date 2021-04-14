import org.testng.annotations.Test;
import java.util.Random;

public class Tests {


    public void threadsafeDemoOwnKey() throws InterruptedException {

        /*
        We will try changing the same hasmap in a parallel test
        the following way:
        Each thread will access their own key and one value

        You may increase this to 1 million so to get 16 million access events
        (Disable random sleep though)
         */


        int max = 100;
        for (int i=1; i <= max; i++) {
            String key = "Thread " + String.valueOf((Long) Thread.currentThread().getId()) + " - " + String.valueOf(i);
            int value = i;
            TestVars.setVar(key, value);
            Random rnd = new Random();
            Thread.sleep((long) rnd.nextInt(200));
            TestVars.setVar(key, value * (-1));
            System.out.println(key + "\t"+ TestVars.getVar(key));
        }

        System.out.println("Thread " + Thread.currentThread().getId() + " says size is : " + TestVars.testVarsMap.size());

        boolean anyGreaterThan0 = false;
        for (int i=1; i <= max; i++) {
            int value = (int) TestVars.getVar("Thread " + String.valueOf((Long) Thread.currentThread().getId()) + " - " + String.valueOf(i));
            if (value > 0) {
                // This means not all values were changed
                anyGreaterThan0 = true;
                break;
            }
        }

        if (anyGreaterThan0) {
            System.out.println("A value was greater than 0.");
            Thread.sleep(10000);
        }
        else
            System.out.println("No value was greater than 0.");

    }

    @Test
    public void usage() {
        TestVars.testVarsMap.clear();
        TestVars.setVar("my var", "Banco X");
        String value = (String) TestVars.getVar("my var");
        System.out.println(value);

        TestVars.setVar("Agência", "7666");

        /*
        Now, let us print all the keys and values
         */
        System.out.println(TestVars.testVarsMap.toString());
    }
}
