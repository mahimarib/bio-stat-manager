package biostat.menu;

import java.util.Scanner;

public abstract class Menu implements Action {
    public static Scanner keyboard = new Scanner(System.in);
    Choice[] choices;
    protected static String input = "";
    int numOfChoices;

    protected Menu() {
        choices = new Choice[10];
    }

    public void addChoice(Choice c) {
        choices[numOfChoices++] = c;
    }

    public abstract boolean hasQuit();

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < numOfChoices; i++) {
            s.append(choices[i].toString()).append('\n');
        }
        return s.toString();
    }

    @Override
    public void run() {
        System.out.println(this);
        System.out.print("Enter Selection: ");
        input = keyboard.nextLine();
        for (int i = 0; i < numOfChoices; i++) {
            if (choices[i].getKey().equals(input)) {
                choices[i]._run();
            }
        }
    }
}
