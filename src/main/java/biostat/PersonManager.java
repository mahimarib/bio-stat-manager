package biostat;

import biostat.choices.*;
import biostat.menu.Menu;
import biostat.menu.SimpleChoice;

public class PersonManager {
    private static final Menu menu = new Menu() {
        @Override
        public boolean hasQuit() {
            return input.equals("Q");
        }
    };

    public static PersonDataManager manager;

    public static void main(String... args) {
        menu.addChoice(new ImportFromFile());
        menu.addChoice(new AddPerson());
        menu.addChoice(new GetInfo());
        menu.addChoice(new SimpleChoice("P", "Print table", () -> {
            try {
                if (manager == null) {
                    throw new IllegalArgumentException();
                }
                manager.printTable();
            } catch (IllegalArgumentException e) {
                System.out.println("there is no one added!");
            }
        }));

        menu.addChoice(new RemovePerson());
        menu.addChoice(new SaveToFile());
        menu.addChoice(new SimpleChoice("Q", "Quit", null));

        do {
            menu.run();
            System.out.println();
        } while (!menu.hasQuit());

        System.out.println("Exiting...");
    }
}
