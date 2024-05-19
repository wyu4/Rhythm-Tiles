public class MapBuilder {
    public static void main(String[] args) {
        System.out.println("Launching map builder...");

    }

    public static void delay(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            System.out.println("Error running the delay block: " + e.getCause());
        }
    }
}
