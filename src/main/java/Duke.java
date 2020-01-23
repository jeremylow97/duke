import java.util.ArrayList;
import java.util.Scanner;

public class Duke {
    public static void main(String[] args) {
        System.out.println("Hello I am from North Korea\n" +
                "What can I do for you?");
        System.out.println("____________________________________\n");
        Scanner sc = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<Task>();

        String input = sc.nextLine();
        while (!input.isEmpty()) {
            if (input.equals("bye")) {
                break;
            }

            if (input.equals("list")) {
                System.out.println("Here are the tasks in your list");
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println(i+1 + "." + tasks.get(i));
                }
                input = sc.nextLine();
                continue;
            }

            String command = input.substring(0, input.indexOf(' '));
            if (command.equals("todo")) {
                Todo todo = new Todo(input.substring(input.indexOf(' '), input.length()));
                tasks.add(todo);
                System.out.println("Got it, I've added the following task:\n" + "  " + todo + "\n"
                        + "Now you have " + tasks.size() + " tasks in the list.");
            } else if (command.equals("deadline")) {
                String by = input.substring(input.indexOf('/') + 4);
                String task = input.substring(input.indexOf(' ') + 1, input.indexOf('/') -1);
                Deadline deadline = new Deadline(task, by);
                tasks.add(deadline);
                System.out.println("Got it, I've added the following task:\n" + "  " + deadline + "\n"
                    + "Now you have " + tasks.size() + " tasks in the list.");
            } else if (command.equals("event")) {
                String at = input.substring(input.indexOf('/') + 4);
                String task = input.substring(input.indexOf(' ') + 1, input.indexOf('/') -1);
                Event event = new Event(task, at);
                tasks.add(event);
                System.out.println("Got it, I've added the following task:\n" + "  " + event + "\n"
                        + "Now you have " + tasks.size() + " tasks in the list.");
            }

            input = sc.nextLine();
        }

        System.out.println("GOODBYE!! MUAHAHHAHAHAHHAAHAHHAHAHA");

    }
}

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

class Deadline extends Task {
    private String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}

class Todo extends Task {
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}

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