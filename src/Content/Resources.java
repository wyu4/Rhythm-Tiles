package Content;

public class Resources {
    /*
     * Storing this as a static variable so that directory location can be easily changed,
     * without having to change all the locations inside the static classes below.
     */
    public static final String RESOURCES_DIR = "Resources";

    // Static class containing relative locations of all images
    public static class Images {
        public static final String BACK = RESOURCES_DIR + "\\Back.png";
        public static final String EXIT = RESOURCES_DIR + "\\Exit.png";
        public static final String PAUSE = RESOURCES_DIR + "\\Pause.png";
        public static final String PLAY = RESOURCES_DIR + "\\Play.png";
        public static final String RECORD = RESOURCES_DIR + "\\Record.png";
        public static final String SAVE = RESOURCES_DIR + "\\Save.png";
        public static final String RESTART = RESOURCES_DIR + "\\Restart.png";
    }

    // Static class containing relative locations of all maps
    public static class Maps {
        public static final String TEST = RESOURCES_DIR + "\\Test.json";
    }
}
