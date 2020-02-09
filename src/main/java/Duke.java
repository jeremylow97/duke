import javafx.collections.FXCollections;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

import javafx.scene.image.ImageView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;



public class Duke {

    private TaskList tasks;
    private Ui ui;
    private Storage storage;


    /**
     * Constructor for Duke to start the initialise the necessary variables
     */
    public Duke() {
        storage = new Storage();


        try {
            tasks = storage.load();
            ui = new Ui(tasks);

        } catch (FileNotFoundException e) {
            tasks = new TaskList();
            ui = new Ui(tasks);
        }

    }


    /**
     * Starts the whole program and get user input
     * @throws IOException if I/O error happens
     */
    public void run() throws IOException {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();

        while (!input.equals("bye")) {

            Parser parser = new Parser();
            String output = parser.parse(input, ui, tasks);
            storage.updateData(tasks);

            input = sc.nextLine();
        }

        ui.printGoodbye();

    }

    public String getResponse(String input) throws IOException {

        Parser parser = new Parser();
        String output = parser.parse(input, ui, tasks);
        storage.updateData(tasks);

        // this is like the system.in shit
        return output;
    }





    /**
     * Main method for the class
     * @param args no commmand line arguments are used explicitly
     * @throws IOException is thrown when there is I/O error
     */
    public static void main(String[] args) throws IOException {
        new Duke().run();
    }

}

/**
 * Task class to store task information
 */
class Task {
    private String description;
    private boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718"); //return tick or X symbols
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}


/**
 * Deadline class which is a Task but more specific
 */
class Deadline extends Task {
    private LocalDate by;

    public Deadline(String description, String by) {
        super(description);
        this.by = LocalDate.parse(by);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ")";
    }
}

/**
 * Todo class which is a Task but more specific
 */
class Todo extends Task {
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}

/**
 * Event class which is a Task but more specific
 */
class Event extends Task {
    private String at;

    public Event(String description, String at) {
        super(description);
        this.at = at;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (at: " + at + ")";
    }
}


