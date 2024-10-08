package sirpotato;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.LocalDate;

/**
 * Contains methods to parse user input and validate user input
 */
class Parser {

    /**
     * Validates the date that user inputs
     * If not in dd-MM-yyyy format, will throw an exception
     * 
     * @param date the date(in string format) that the user types in
     */
    public static void validateDateFormat(String date) throws DukeException {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        try {
            LocalDate.parse(date, dateFormatter);
        } catch (DateTimeParseException e) {
            throw new DukeException("Mate, the date must be in the format dd-MM-yyyy.");
        }
    }

    /**
     * Validates user input
     * 
     * @param userInput The user's input to the chatbot
     */
    public static void checkForErrors(String userInput) throws DukeException {
        if (userInput.startsWith("todo")) {
            if (!userInput.startsWith("todo ")) {
                throw new DukeException("Mate, you may have misspelt or forgotten to type the rest of your command");
            }
            if (userInput.trim().length() <= 5 || userInput.substring(5).isEmpty()) {
                throw new DukeException("Mate, you have got to give us a description of the task");
            }
        } else if (userInput.startsWith("deadline")) {
            if (!userInput.startsWith("deadline ")) {
                throw new DukeException("Mate, you may have misspelt or forgotten to type the rest of your command");
            }
            if (userInput.trim().length() <= 9) {
                throw new DukeException("Mate, you need to give me a task description and deadline date");
            }
            String[] sectionedString = userInput.split("/by ");
            if (sectionedString.length < 2 || 
                sectionedString[0].substring(9).isEmpty() || 
                sectionedString[1].isEmpty()) {
                throw new DukeException("Mate, you need to give me a task description and deadline date");
            }
            validateDateFormat(sectionedString[1].trim());
        } else if (userInput.startsWith("event")) {
            if (!userInput.startsWith("event ")) {
                throw new DukeException("Mate, you may have misspelt or forgotten to type the rest of your command");
            }
            if (userInput.trim().length() <= 6) {
                throw new DukeException("Mate, an event should have the description, the start, and the end.");
            }
            String[] sectionedString = userInput.split(" /from | /to ");
            if (sectionedString.length < 3 || sectionedString[0].substring(6).isEmpty() ||
                sectionedString[1].isEmpty() || sectionedString[2].isEmpty()) {
                throw new DukeException("Mate, an event should have the description, the start, and the end.");
            }
            validateDateFormat(sectionedString[1].trim());
            validateDateFormat(sectionedString[2].trim());
        } else if (userInput.startsWith("delete")) {
            if (!userInput.startsWith("delete ")) {
                throw new DukeException("Mate, you may have misspelt or forgotten to type the rest of your command");
            }
            if (userInput.trim().length() <= 7) {
                throw new DukeException("You need to say which item to delete");
            } 
            String[] sectionedString = userInput.trim().split("\\s+");
            String taskToDeleteString = sectionedString[1].trim();
            if (!taskToDeleteString.matches("\\d+")) {
                throw new DukeException("Mate, please give a task number(not text) to delete");
            }
        } else if (userInput.startsWith("sort")) {
            if (!userInput.startsWith("sort ")) {
                throw new DukeException("Mate, you may have misspelt or forgotten to type the rest of your command");
            }
            if (userInput.trim().length() <= 5) {
                throw new DukeException("Mate, please specify your category: either description or deadline");
            }
            String[] sectionedString = userInput.split(" ");
            String categoryToSortBy = sectionedString[1];
            if (!(categoryToSortBy.equals("description") || categoryToSortBy.equals("deadline"))) {
                throw new DukeException("You have to sort by description or deadline");
            }
        } else if (userInput.trim().startsWith("mark")) {
            if (!userInput.startsWith("mark ")) {
                throw new DukeException("Mate, you may have misspelt or forgotten to type the rest of your command");
            }
            if (userInput.trim().length() <= 5) {
                throw new DukeException("Mate, please specify your task to mark");
            }
            String[] sectionedString = userInput.trim().split("\\s+");
            String taskToMarkString = sectionedString[1].trim();
            if (!taskToMarkString.matches("\\d+")) {
                throw new DukeException("Mate, please give a task number(not text) to mark");
            }
        } else if (userInput.trim().startsWith("unmark")) {
            if (!userInput.startsWith("unmark ")) {
                throw new DukeException("Mate, you may have misspelt or forgotten to type the rest of your command");
            }
            if (userInput.trim().length() <= 7) {
                throw new DukeException("Mate, please specify your task to mark");
            }
            String[] sectionedString = userInput.trim().split("\\s+");
            String taskToUnmarkString = sectionedString[1].trim();
            if (!taskToUnmarkString.matches("\\d+")) {
                throw new DukeException("Mate, please give a task number(not text) to unmark");
            }
        } else if (userInput.trim().startsWith("find")) {
            if (!userInput.startsWith("find ")) {
                throw new DukeException("Mate, you may have misspelt or forgotten to type the rest of your command");
            }
            if (userInput.trim().length() <= 5) {
                throw new DukeException("Mate, please specify a keyword/string to search for");
            }
        }else if (!userInput.trim().equals("bye") && !userInput.trim().equals("list")) {
            throw new DukeException("I'm sorry, that is not a valid input");
        }
    }

    /**
     * Parses the date given into dd-MM-yyyy format
     * 
     * @param dateToParse The string date that we wish to parse into dd-MM-yyyy format
     * @return the LocalDate object representing the dd-MM-yyyy date 
     */
    public static LocalDate parseDate(String dateToParse) throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return LocalDate.parse(dateToParse, formatter);
    }

    /**
     * Parses the user input and returns the command to be executed
     * 
     * @param userInput User's input to the chatbot
     * @return Command object to be executed i.e AddCommand or MarkCommand
     * @throws DukeException if the input is not valid 
     */
    public static Command parseCommand(String userInput) throws DukeException {
        checkForErrors(userInput);

        if (userInput.trim().equals("bye")) {
            return new ExitCommand();
        } else if (userInput.trim().equals("list")) {
            return new ListCommand();
        } else if (userInput.startsWith("mark")) {
            int itemNumber = Integer.parseInt(userInput.split(" ")[1]) - 1;
            return new MarkCommand(itemNumber);
        } else if (userInput.startsWith("unmark")) {
            int itemNumber = Integer.parseInt(userInput.split(" ")[1]) - 1;
            return new UnmarkCommand(itemNumber);
        } else if (userInput.startsWith("todo")) {
            String description = userInput.substring(5);
            return new AddCommand(new Todo(description));
        } else if (userInput.startsWith("find")) {
            String searchString = userInput.substring(5);
            return new FindCommand(searchString);
        }else if (userInput.startsWith("deadline")) {
            String[] sectionedString = userInput.split("/by ");
            String description = sectionedString[0].substring(9);
            LocalDate by = parseDate(sectionedString[1].trim());
            return new AddCommand(new Deadline(description, by));
        } else if (userInput.startsWith("event")) {
            String[] sectionedString = userInput.split(" /from | /to ");
            String description = sectionedString[0].substring(6);
            LocalDate from = parseDate(sectionedString[1]);
            LocalDate to = parseDate(sectionedString[2]);
            return new AddCommand(new Event(description, from, to));
        } else if (userInput.startsWith("delete")) {
            String[] sectionedString = userInput.split(" ");
            int itemToDelete = Integer.parseInt(sectionedString[1]) - 1;
            return new DeleteCommand(itemToDelete);
        } else if (userInput.startsWith("sort")) {
            String[] sectionedString = userInput.split(" ");
            String categoryToSortBy = sectionedString[1];
            return new SortCommand(categoryToSortBy);
        } else {
            throw new DukeException("That is not valid input mate. Please have another go.");
        }
    }
}