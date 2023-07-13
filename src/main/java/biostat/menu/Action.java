package biostat.menu;

/**
 * Interface allowing to anonymously send actions per choice, basically the
 * same as {@link Runnable}
 */
public interface Action {
    void run();
}
