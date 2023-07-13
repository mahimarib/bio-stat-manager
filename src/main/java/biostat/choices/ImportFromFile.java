package biostat.choices;

import biostat.PersonDataManager;
import biostat.PersonManager;
import biostat.menu.Choice;
import biostat.menu.Menu;

public class ImportFromFile extends Choice {
    public ImportFromFile() {
        super("I", "Import From File");
    }

    @Override
    public void run() {
        System.out.print("Enter file location: ");
        String location = Menu.keyboard.nextLine();
        try {
            PersonManager.manager = PersonDataManager.buildFromFile(location);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
