package biostat.choices;

import biostat.PersonDoesNotExistsException;
import biostat.PersonManager;
import biostat.menu.Choice;
import biostat.menu.Menu;

public class RemovePerson extends Choice {
    public RemovePerson() {
        super("R", "Remove Person");
    }

    @Override
    public void run() {
        try {
            if (PersonManager.manager == null) {
                throw new IllegalArgumentException();
            }
            System.out.print(
                    "Please enter the name of the person: ");
            String name = Menu.keyboard.nextLine();
            PersonManager.manager.removePerson(name);
        } catch (IllegalArgumentException e) {
            System.out.println("there is no one added!");
        } catch (PersonDoesNotExistsException e) {
            System.out.println("the person you enter does not " +
                               "exist");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("you entered an invalid number");
        }
    }
}
