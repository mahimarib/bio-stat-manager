package biostat.choices;

import biostat.PersonDataManager;
import biostat.PersonManager;
import biostat.menu.Choice;
import biostat.menu.Menu;

import java.io.IOException;

public class SaveToFile extends Choice {
    public SaveToFile() {
        super("S", "Save to file");
    }

    @Override
    public void run() {
        try {
            if (PersonManager.manager == null) {
                throw new IllegalArgumentException();
            }
            System.out.print("Please select a name for the file: ");
            String file = Menu.keyboard.nextLine();
            PersonDataManager.saveToFile(PersonManager.manager, file);
        } catch (IllegalArgumentException e) {
            System.out.println("can't save if no one is added!");
        } catch (IOException e) {
            System.out.println("I/O exception");
        }
    }
}
