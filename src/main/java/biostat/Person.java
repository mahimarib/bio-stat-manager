package biostat;


public class Person {
    private String name;
    private String gender;
    private int age;
    private double height;
    private double weight;

    public Person(
            String name, String gender, int age,
            double height, double weight) {
        setName(name);
        setGender(gender);
        this.age = age;
        this.height = height;
        this.weight = weight;
    }

    public Person() {}

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Used to see if a string is a number.
     *
     * @param num number in string
     * @return boolean
     */
    public static boolean isValidNumber(String num) {
        for (int i = 0; i < num.length(); i++)
            if ('0' > num.charAt(i) ^ num.charAt(i) > '9')
                return false;
        return true;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.substring(0, 1).toUpperCase() +
                    name.substring(1).toLowerCase();
    }

    /**
     * Used to see if the name entered has no special characters.
     *
     * @param name Name as a string
     * @return boolean
     */
    public static boolean isValidName(String name) {
        for (int i = 0; i < name.length(); i++) {
            name = name.toLowerCase();
            if ('a' > name.charAt(i) ^ name.charAt(i) > 'z')
                return false;
        }
        return true;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender.substring(0, 1).toUpperCase();
    }

    /**
     * Used to see if the gender entered is correct.
     *
     * @param gender Gender as a string
     * @return boolean
     */
    public static boolean isValidGender(String gender) {
        gender = gender.substring(0, 1).toUpperCase();
        return gender.equals("F") || gender.equals("M");
    }

    /**
     * method used together in order to see if the user entered valid inputs
     *
     * @param parsedInput String array
     * @return boolean
     */
    public static boolean isValidInput(String[] parsedInput) {
        return isValidName(parsedInput[0]) && isValidGender(parsedInput[1]) &&
               isValidNumber(parsedInput[2]) && isValidNumber(parsedInput[3]) &&
               isValidNumber(parsedInput[4]);
    }

    private String heightConverted() {
        return Math.floor(height / 12) + " feet " + (height % 12) + " inches";
    }

    /**
     * Used to print the information of the person. used in
     * {@link PersonDataManager#getPerson(String)}.
     */
    public void printInfo() {
        System.out.println(name + " is a " + age + " year old " +
                           (gender.equals("M") ? gender + "ale" :
                            gender + "emale") + " who is " + heightConverted() +
                           " tall and weighs " + weight + " pounds");
    }

    /**
     * @return Person's stats in tabular form.
     */
    @Override
    public String toString() {
        return String.format(
                "%-15s%-6d%-10s%-10.1f%-10.1f",
                name, age, gender, height,
                weight);
    }

    /**
     * Checks if this person is equal to given person.
     *
     * @param o Other person.
     * @return true or false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return age == person.age &&
               Double.compare(person.height, height) == 0 &&
               Double.compare(person.weight, weight) == 0 &&
               name.equals(person.name) &&
               gender.equals(person.gender);
    }

    /**
     * Compares the values of this person to the given person.
     *
     * @param o Other person.
     * @return positive of is person is greater than the given person,
     * negative if not.
     */
    public int compareTo(Person o) {
        int i = name.compareTo(o.name);
        if (i != 0) return i;

        i = age - o.age;
        if (i != 0) return i;

        i = (int) (height - o.height);
        if (i != 0) return i;

        i = (int) (weight - o.weight);
        if (i != 0) return i;

        i = gender.compareTo(o.gender);
        return i;
    }
}
