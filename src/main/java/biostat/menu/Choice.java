package biostat.menu;

/**
 * Class used to represent a choice in the user menu, allows you to pass in
 * the key, description and the action it needs to run which is user defined.
 * Choices can have sub-choices.
 */
public abstract class Choice implements Action {
    private final String key;
    private boolean separate;
    private final String description;
    private SubMenu subChoices;

    public Choice(String key, String description) {
        this.key = key.toUpperCase();
        this.description = description;
    }

    String getKey() {
        return key;
    }

    private boolean hasSubChoices() {
        return subChoices != null;
    }

    public void addSubChoices(Choice c) {
        if (!hasSubChoices()) subChoices = new SubMenu();
        subChoices.addChoice(c);
    }

    public abstract void run();

    void _run() {
        if (hasSubChoices()) {
            separate = true;
            subChoices.run();
            separate = false;
        } else run();
    }

    @Override
    public String toString() {
        String s = "(" + key + ") - " + description;
        return hasSubChoices() ? s + '\n' + subChoices.toString() : s;
    }

    private class SubMenu extends Menu {
        @Override
        public boolean hasQuit() {
            boolean shouldQuit = true;
            for (int i = 0; i < numOfChoices; i++) {
                if (choices[i].getKey().equals(input)) shouldQuit = false;
            }
            return shouldQuit;
        }

        private String getIndented() {
            String s = "";
            for (int i = 0; i < numOfChoices; i++) {
                s = s + "\t" + choices[i];
            }
            return s;
        }

        private String getMenu() {
            String s = "";
            for (int i = 0; i < numOfChoices; i++) {
                s = s + choices[i];
            }
            return s;
        }

        @Override
        public String toString() {
            return separate ? getMenu() : getIndented();
        }
    }
}
