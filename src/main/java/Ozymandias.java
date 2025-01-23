public class Ozymandias {
    public static String greetHello() {
        return "____________________________________________________________ \n" +
                "Hello! I'm Ozymandias! \nWhat can I do for you?"
                + "\n____________________________________________________________ ";
    }

    public static String greetGoodbye() {
        return "Bye. Hope to see you again soon!"
                + "\n____________________________________________________________";
    }
    public static void main(String[] args) {
        System.out.println(greetHello());
        System.out.println(greetGoodbye());
    }
}
