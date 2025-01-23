import java.util.Scanner;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Ozymandias {
    private static LinkedHashMap<Integer, String> tasks = new LinkedHashMap<>();
    private static int idCounter = 1;

    public static String greetHello() {
        return """
                    Hello! I'm Ozymandias
                    What can I do for you?\s
                """;
    }

    public static String greetGoodbye() {
        return "    Bye. Hope to see you again soon!\n";
    }

    public static void addText(String text) {
        tasks.put(idCounter++, text);
        System.out.println("    added: " + text+ "\n");
    }

    public static LinkedHashMap<Integer, String> getTasks() {
        return new LinkedHashMap<>(tasks); // Return a copy to prevent modification
    }

    public static void printTasks() {
        for (Integer id : tasks.keySet()) {
            System.out.println("    " + id + ". " + tasks.get(id));
        }
        System.out.println();
    }

    public static void main(String[] args) {
        System.out.println(greetHello());
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("bye")) {
                System.out.println(greetGoodbye());
                return;
            } else if (input.equalsIgnoreCase("list")) {
                printTasks();
            } else {
                addText(input);
            }
        }
    }
}
