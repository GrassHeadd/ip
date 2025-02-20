package Ozymandias.ui;

import java.time.format.DateTimeParseException;

/**
 * A parser that interprets user commands and delegates tasks appropriately
 */
public class Parser {

    /**
     * Processes the user's input and calls the
     * corresponding methods based on the command.
     *
     * @param input The raw command entered by the user.
     * @param oz    The main chatbot object.
     * @return A response message to be displayed to the user.
     */
    public static String handleCommand(String input, Ozymandias oz) {
        try {
            String trimmedInput = input.trim();
            if (HandleInput.isFindCommand(trimmedInput)) {
                return HandleInput.handleFindCommand(trimmedInput, oz);
            } else if (HandleInput.isByeCommand(trimmedInput)) {
                oz.setExit(true);
                return Ui.greetGoodbye();
            } else if (HandleInput.isListCommand(trimmedInput)) {
                return oz.printTasks();
            } else if (HandleInput.isMarkOrUnmarkCommand(trimmedInput)) {
                return HandleInput.handleMarkOrUnmark(trimmedInput, oz);
            } else if (HandleInput.isDeleteCommand(trimmedInput)) {
                return HandleInput.handleDeleteCommand(trimmedInput, oz);
            } else if (HandleInput.isRemindCommand(trimmedInput)) {
                System.out.println("handle called");
                return HandleInput.handleRemindCommand(trimmedInput, oz);
            } else {
                return createDifferentTask(trimmedInput, oz);
            }
        } catch (DateTimeParseException e) {
            return "Invalid date you entered\n";
        } catch (IndexOutOfBoundsException e) {
            return "Missing task details or invalid format you banana pants\n";
        } catch (NumberFormatException e) {
            return "Invalid task id you entered\n";
        } catch (NullPointerException e) {
            return "You didn't put anything!\n";
        } catch (Exception e) {
            return "You did some stupid stuff, find out yourself!\n";
        }
    }

    /**
     * Creates different task objects (ToDos, Deadlines, Events)
     * based on the user's input string.
     */
    public static String createDifferentTask(String input, Ozymandias oz) {
        try {
            if (input.startsWith("todo")) {
                return HandleTaskCreate.handleTodoCreation(input, oz);
            } else if (input.startsWith("deadline")) {
                return HandleTaskCreate.handleDeadlineCreation(input, oz);
            } else if (input.startsWith("event")) {
                return HandleTaskCreate.handleEventCreation(input, oz);
            }
        } catch (DateTimeParseException e) {
            return "Invalid date you entered\n";
        } catch (IndexOutOfBoundsException e) {
            return "Missing task details or invalid format you banana pants\n";
        } catch (NullPointerException e) {
            return "You didn't put anything!\n";
        } catch (Exception e) {
            return "idk man think yourself what you did wrong!\n";
        }

        // If the command doesn’t match any known pattern
        return "what are you even asking me to do???";
    }
}
