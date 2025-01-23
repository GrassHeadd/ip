import java.sql.SQLOutput;
import java.util.Scanner;

public class Ozymandias {
    public static String greetHello() {
        return "    ____________________________________________________________ \n" +
                "    Hello! I'm Ozymandias\n    What can I do for you?"
                + "\n    ____________________________________________________________ ";
    }

    public static String greetGoodbye() {
        return "    Bye. Hope to see you again soon!"
                + "\n    ____________________________________________________________";
    }
    public static void main(String[] args) {
        System.out.println(greetHello());
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("bye")) {
                System.out.println(greetGoodbye());
                return;
            }
            System.out.println("    ____________________________________________________________ \n" +
                    "    " + input
                    + "\n    ____________________________________________________________ ");
        }
    }
}
