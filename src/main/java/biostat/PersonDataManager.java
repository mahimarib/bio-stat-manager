package biostat;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class PersonDataManager {
    private Person[] people;
    private int numOfPeople;

    public PersonDataManager() {
        numOfPeople = 0;
        people = new Person[10];
    }

    public PersonDataManager(int initCapacity) {
        if (initCapacity < 0) throw new IllegalArgumentException(
                "initial capacity cannot be less than 0");
        numOfPeople = 0;
        people = new Person[initCapacity];
    }

    /**
     * Method used to parse a CSV file and load the information on people.
     *
     * @param location The location of the file.
     * @return PersonDataManager
     * @throws IllegalArgumentException exception is thrown if the inputs in
     *                                  the file are incorrect.
     */
    public static PersonDataManager buildFromFile(String location)
    throws IllegalArgumentException {
        PersonDataManager personDataManager = new PersonDataManager();
        Person person = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(location));
            String line;
            br.readLine(); // skipping first line
            while ((line = br.readLine()) != null) {
                String[] parsed = line.split(",");
                for (int i = 0; i < parsed.length; i++)
                    parsed[i] = parsed[i].trim().replaceAll("\"", "");
                person = new Person();
                if (!Person.isValidInput(parsed))
                    throw new IllegalArgumentException(
                            "Problem parsing: " + Arrays.toString(parsed) +
                            " please fix file and try again");
                person.setName(parsed[0]);
                person.setGender(parsed[1]);
                person.setAge(Integer.parseInt(parsed[2]));
                person.setHeight(Double.parseDouble(parsed[3]));
                person.setWeight(Double.parseDouble(parsed[4]));
                personDataManager.addPerson(person);
            }
            System.out.println("Loading...");
            System.out.println("Person data loaded successfully!");
        } catch (FileNotFoundException e) {
            System.out.println(
                    "Can't find file in given location: " + location);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (PersonAlreadyExistsException e) {
            System.out.println(
                    "There are duplicate people named: " + person.getName() +
                    " in the file, please fix and try again.");
        }
        return personDataManager;
    }

    /**
     * Method used for saving the current PersonDataManager to a CSV file.
     *
     * @param manager
     * @param fileName
     * @throws IOException
     */
    public static void saveToFile(PersonDataManager manager, String fileName)
    throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        writer.write("\"Name\", \"Gender\", \"Age\", \"Height\", \"Weight\",");
        writer.newLine();
        for (int i = 0; i < manager.numOfPeople; i++) {
            writer.write(String.format(
                    "\"%s\", \"%s\", %d, %.0f, %.0f,",
                    manager.people[i].getName(),
                    manager.people[i].getGender(),
                    manager.people[i].getAge(),
                    manager.people[i].getHeight(),
                    manager.people[i].getWeight()));
            writer.newLine();
        }
        writer.close();
        System.out.println("A file named " + fileName + " has been created!");
    }

    private static void copy(
            Person[] src, int srcStart, Person[] copy, int copyStart,
            int numOfPeople) {
        for (int i = 0; i < numOfPeople; i++)
            copy[copyStart + i] = src[srcStart + i];
    }

    public void ensureCapacity(int minCapacity) {
        Person[] biggerArray;
        if (people.length < minCapacity) {
            biggerArray = new Person[minCapacity];
            copy(people, 0, biggerArray, 0, numOfPeople);
            people = biggerArray;
        }
    }

    /**
     * Internal method that is used to find which position a new person
     * should be added to keep order using binary search recursively.
     * <p>
     * best case:  O(1)
     * worst case: O(log n)
     *
     * @param lo     The lowest position to check in array.
     * @param hi     The highest position to check in array.
     * @param person The person you want to add.
     * @return The position the new person should be in the array.
     * @throws PersonAlreadyExistsException Checks if the person you are
     *                                      trying to add exists already.
     */
    private int placementPos(int lo, int hi, Person person)
    throws PersonAlreadyExistsException {
        // checks if the person should be added before the first person.
        if (person.compareTo(people[lo]) < 0) return lo;
        // checks if the person should be added after the last person.
        if (person.compareTo(people[hi]) > 0) return hi + 1;
        int mid = (hi + lo) / 2;
        // checks if they are the same.
        if (person.equals(people[mid]) || person.equals(people[lo]) ||
            person.equals(people[hi])) throw new PersonAlreadyExistsException();
        // if the person you are trying to add is before the middle
        // person search again between the first person and person before the
        // middle persons.
        if (person.compareTo(people[mid]) < 0) return placementPos(
                lo, mid - 1, person);
        // if the person you are trying to add is after the middle
        // person search again between the person after the middle person and
        // the last person.
        if (person.compareTo(people[mid]) > 0) return placementPos(
                mid + 1, hi, person);
        return -1;
    }

    /**
     * Internal method that is used to rightShift all elements recursively to
     * add a new person in the given position.
     * <p>
     * best case:  O(1)
     * worst case: O(n)
     *
     * @param pos    The position the new person should be in.
     * @param person The new person to add.
     */
    private void rightShift(int pos, Person person) {
        if (people[pos] != null) {
            Person temp = people[pos];
            people[pos] = person;
            rightShift(pos + 1, temp);
        } else {
            people[pos] = person;
        }
    }

    /**
     * Internal method that is used to leftShift all elements recursively to
     * remove the person in the given position.
     *
     * @param pos index of the person.
     */
    private void leftShift(int pos) {
        if (pos + 1 == people.length || people[pos + 1] == null)
            people[pos] = null;
        else {
            people[pos] = people[pos + 1];
            leftShift(pos + 1);
        }
    }

    /**
     * Binary search to find a person based on name.
     *
     * @param name Name of the person.
     * @return the index where the person is.
     */
    private int find(String name) {
        int lo = 0;
        int hi = numOfPeople - 1;
        while (lo <= hi) {
            int mid = (hi + lo) / 2;
            if (name.equals(people[mid].getName())) return mid;
            if (name.compareTo(people[mid].getName()) < 0)
                hi = mid - 1;
            if (name.compareTo(people[mid].getName()) > 0)
                lo = mid + 1;
        }
        return -1;
    }

    /**
     * Binary search to find a person.
     *
     * @param person {@link Person}
     * @return the index where the person is.
     */
    private int find(Person person) {
        int lo = 0;
        int hi = numOfPeople - 1;
        while (lo <= hi) {
            int mid = (hi + lo) / 2;
            if (person.equals(people[mid])) return mid;
            if (person.compareTo(people[mid]) < 0)
                hi = mid - 1;
            if (person.compareTo(people[mid]) > 0)
                lo = mid + 1;
        }
        return -1;
    }

    /**
     * Find if there are other people with the same name given the name of
     * the person and the index of the person as it increments through the
     * people after the person specified and before the person until there is
     * no one the same name. If there are people with the same name, it makes
     * a new PersonDataManager variable and adds the people with the same
     * name inside. Then it prints out the people with the same name and it
     * asks the user which person they wanted to select from a number 1 to
     * the number of the same people there are.
     *
     * @param name Name of the person.
     * @param pos  index of the person.
     * @return the person the user wanted, if there isn't more than one
     * person with the same name it will return null.
     */
    private Person listPersonWithSameName(String name, int pos) {
        PersonDataManager peopleWithSameName = null;
        {
            int i = 1;
            while (pos - i >= 0 && pos + i < numOfPeople &&
                   (people[pos + i].getName().equals(name) ||
                    people[pos - i].getName().equals(name))) {
                Person leftPerson = people[pos - i];
                Person rightPerson = people[pos + i];
                try {
                    if (peopleWithSameName == null) {
                        peopleWithSameName = new PersonDataManager();
                        peopleWithSameName.addPerson(people[pos]);
                    }
                    if (rightPerson.getName().equals(name))
                        peopleWithSameName.addPerson(people[pos + i]);
                    if (leftPerson.getName().equals(name))
                        peopleWithSameName.addPerson(people[pos - i]);
                } catch (PersonAlreadyExistsException e) {
                    System.out.println("tried to enter same person");
                }
                i++;
            }
        }
        if (peopleWithSameName != null) {
            peopleWithSameName.printTable();
            Scanner sc = new Scanner(System.in);
            System.out.println(
                    "there are people with the same name, please specify the " +
                    "number of the person you want (1 .. " +
                    peopleWithSameName.numOfPeople + "): ");
            int i = sc.nextInt();
            if (i < 1 || i > peopleWithSameName.numOfPeople) {
                throw new IndexOutOfBoundsException();
            }
            return peopleWithSameName.people[i - 1];
        }
        return null;
    }

    /**
     * Uses binary search to find the person looking for, and checks the
     * people around it to see if they have the same name and asks to specify
     * which person they meant using
     * {@link PersonDataManager#listPersonWithSameName(String, int)}. Then
     * prints out the information of the person.
     *
     * @param name Name of the person.
     * @throws PersonDoesNotExistsException exception is thrown if the person
     *                                      asked for doesn't exists.
     */
    public void getPerson(String name) throws PersonDoesNotExistsException {
        int pos = find(name);
        if (pos == -1) throw new PersonDoesNotExistsException();
        Person person = listPersonWithSameName(name, pos);
        if (person != null) {
            person.printInfo();
        } else {
            people[pos].printInfo();
        }
    }

    /**
     * Uses binary search to find the person looking for, and checks the
     * people around it to see if they have the same name and asks to specify
     * which person they meant using
     * {@link PersonDataManager#listPersonWithSameName(String, int)}. Then
     * removes the person.
     *
     * @param name Name of the person.
     * @throws PersonDoesNotExistsException exception is thrown if the person
     *                                      asked for doesn't exists.
     */
    public void removePerson(String name) throws PersonDoesNotExistsException {
        int pos = find(name);
        if (pos == -1) throw new PersonDoesNotExistsException();
        Person person = listPersonWithSameName(name, pos);
        if (person != null) {
            leftShift(find(person));
        } else {
            leftShift(pos);
        }
        numOfPeople--;
    }

    /**
     * Adds a new person in the array in order of name, if a person in the
     * list has the same name it will then check age -> height -> weight ->
     * gender, this is done by: {@link Person#compareTo(Person)}. This method
     * utilizes {@link PersonDataManager#ensureCapacity(int)} to make sure
     * there is enough space in the array,
     * {@link PersonDataManager#placementPos(int, int, Person)} to find the
     * position where the newPerson should be added to keep order,
     * {@link PersonDataManager#rightShift(int, Person)}  is used to
     * rightShift all the
     * elements over to add in the newPerson.
     * <p>
     * best case: O(1)
     * worst case: O(n)
     *
     * @param newPerson Person to add.
     * @throws PersonAlreadyExistsException will be thrown if tried to add a
     *                                      person with the same bio stats.
     */
    public void addPerson(Person newPerson)
    throws PersonAlreadyExistsException {
        if (numOfPeople == people.length) ensureCapacity(numOfPeople * 2 + 1);
        if (numOfPeople == 0) {
            people[0] = newPerson;
        } else {
            rightShift(placementPos(0, numOfPeople - 1, newPerson), newPerson);
        }
        numOfPeople++;
    }

    /**
     * Prints the table
     */
    public void printTable() {
        System.out.printf("%-15s%-6s%-10s%-10s%-10s", "Name", "Age",
                          "Gender", "Height", "Weight");
        System.out.println('\b');
        for (int i = 0; i < 48; i++) System.out.print("=");
        System.out.println('\b');
        for (int i = 0; i < numOfPeople; i++) System.out.println(people[i]);
    }
}
