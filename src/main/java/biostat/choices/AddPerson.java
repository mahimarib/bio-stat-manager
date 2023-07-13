package biostat.choices;

import biostat.Person;
import biostat.PersonAlreadyExistsException;
import biostat.PersonDataManager;
import biostat.PersonManager;
import biostat.menu.Choice;
import biostat.menu.Menu;

import java.util.InputMismatchException;

public class AddPerson extends Choice {
    public AddPerson() {
        super("A", "Add person");
    }

    @Override
    public void run() {
        if (PersonManager.manager == null) {
            PersonManager.manager = new PersonDataManager();
        }
        try {
            System.out.print(
                    "Please enter the name of the person: ");
            String name = Menu.keyboard.nextLine();
            if (!Person.isValidName(name)) {
                throw new IllegalArgumentException();
            }
            System.out.print("Please enter the age: ");
            int age = Integer.parseInt(Menu.keyboard.nextLine());
            System.out.print("Please enter the gender (M or F): ");
            String gender = Menu.keyboard.nextLine();
            if (!Person.isValidGender(gender))
                throw new IllegalArgumentException();
            System.out.print(
                    "Please enter the height (in inches): ");
            double height = Double.parseDouble(
                    Menu.keyboard.nextLine());
            System.out.print("Please enter the weight (in lbs): ");
            double weight = Double.parseDouble(
                    Menu.keyboard.nextLine());
            Person person = new Person(
                    name, gender, age, height, weight);
            PersonManager.manager.addPerson(person);
            System.out.println(person.getName() + " has been " +
                               "added to the list!");
        } catch (PersonAlreadyExistsException e) {
            System.out.println("this person already exists");
        } catch (InputMismatchException | IllegalArgumentException e) {
            System.out.println("sorry invalid input try again!");
        }
    }
}
