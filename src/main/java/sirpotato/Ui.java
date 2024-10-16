package sirpotato;

import java.util.Scanner;

/**
 * A class that handles the interactions with the users
 * Reads their input
 * Handles responses to the user
 */
public class Ui {

    private static final String HORIZONTAL_LINE = "___________________________ \n";
    private static final String INDENT = "   ";

    private String logo;
    private Scanner scanner;


    /**
     * Initialises the UI for the user
     * Sets the logo and initialises the scanner
     */
    public Ui() {
        this.logo = "Sir Potato";
        this.scanner = new Scanner(System.in);
    }

    /**
     * Reads the user's next command to the chatbot.
     */
    public String readCommand() {
        String userInput = scanner.nextLine();
        return userInput;
    }

    /**
     * Displays & returns the welcome message when the user first starts the chat
     *
     * @return Welcome message
     */
    public String displayWelcomeMessage() {
        String response = "Hello from" + logo
                + "What can I do ya for?\n" + HORIZONTAL_LINE;
        System.out.println(response);
        return response;
    }

    /**
     * Displays & returns the bye message when the user types 'bye'.
     *
     * @return Bye message
     */
    public String displayByeMessage() {
        String response = "Bye mate, see you around.";
        System.out.println(response);
        return response;
    }

    /**
     * Displays & returns the message that a task has been added to the list
     *
     * @param task the task that is being added to the toDoList.
     * @param tasks the task list the task is being added to.
     * @return Added task message
     */
    public String displayAddedTask(Task task, TaskList tasks) {
        String response = "Got it. I've added this task for ya.\n"
                + "Now you've got " + tasks.getSizeOfList() + " tasks in the list mate";
        System.out.println(response);
        return response;
    }

    /**
     * Prints & returns out the deletion message
     *
     * @param task Task being deleted
     * @param tasks The to-do list being deleted from
     * @return Deletion of task message
     */
    public String displayDeletionMessage(Task task, TaskList tasks) {
        String response = "Gotcha mate, I've deleted the following task: \n"
                + task + "\n" + "Now you've got "
                + (tasks.getSizeOfList()) + " tasks left.";
        System.out.println(response);
        return response;
    }

    /**
     * Prints & returns the tasks in the current tasklist
     *
     * @param tasks The TaskList containing the current tasks
     * @return the formatted list of tasks
     */
    public String listTasks(TaskList tasks) {
        String response = "Here are the tasks in your list:\n" + "List: \n";

        for (int i = 0; i < tasks.getList().size(); i++) {
            response = response + (i + 1) + ". " + tasks.getTask(i) + "\n";
        }
        System.out.println(response);
        return response;
    }

    /**
     * Prints & returns the sorted task list
     *
     * @param tasks The TaskList containing the current tasks
     * @return the formatted list of tasks
     */
    public String displaySortedTasks(TaskList tasks) {
        String response = "Here is your sorted task list: \n";

        for (int i = 0; i < tasks.getList().size(); i++) {
            response = response + (i + 1) + ". " + tasks.getTask(i) + "\n";
        }
        System.out.println(response);
        return response;
    }

    /**
     * Prints and returns the marked item
     *
     * @param itemNumber the index number(starts at 1) of the item to be deleted
     * @param tasks the TaskList's arrayList containing the current to-do list.
     * @return the marked item message
     */
    public String displayMarkedItem(int itemNumber, TaskList tasks) {
        String response = "Good on ya! I've marked it done:\n"
                + tasks.getTask(itemNumber);
        System.out.println(response);
        return response;
    }

    /**
     * Prints and returns unmarked item
     *
     * @param itemNumber the index number(starts at 1) of the item to be deleted
     * @param tasks the TaskList's arrayList containing the current to-do list.
     * @return the unmarked item message
     */
    public String displayUnmarkedItem(int itemNumber, TaskList tasks) {
        String response = "What's happened here mate? I've unmarked it for ya.\n"
                + tasks.getTask(itemNumber);
        System.out.println(response);
        return response;
    }

    /**
     * Formats the chatbot's response by sandwiching it between a dashed line
     * And automatically indents the response then returns it and prints it
     *
     * @param input the text that the chatbot wishes to format
     * @return the response sandwiched between horizontal lines
     */
    public String respond(String input) {
        String response = HORIZONTAL_LINE
                + INDENT + input
                + HORIZONTAL_LINE;
        System.out.println(response);
        return response;
    }

    /**
     * Prints & returns all the tasks in the tasklist that match the search string
     *
     * @param tasks The Tasklist in which we wish to search
     * @param searchString The string we wish to search for
     * @return the formatted list of tasks that match the search
     */
    public String findTasks(TaskList tasks, String searchString) {
        String response = "Here are the matching tasks found:\n";

        for (int i = 0; i < tasks.getList().size(); i++) {
            if (tasks.getList().get(i).containsString(searchString)) {
                response = response + (i + 1) + ". " + tasks.getTask(i) + "\n";
            }
        }
        System.out.println(response);
        return response;
    }

}
