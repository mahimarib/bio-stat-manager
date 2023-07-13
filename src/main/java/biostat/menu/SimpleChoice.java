package biostat.menu;

public class SimpleChoice extends Choice {
    private final Action func;

    public SimpleChoice(String key, String description, Action func) {
        super(key, description);
        this.func = func;
    }

    @Override
    public void run() {
        if (func != null) func.run();
    }
}
