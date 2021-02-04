package se.iths.groupmembers;

public class Main {

    private static final int PORT = 5050;

    public static void main(String[] args) {
        new Server().start(PORT);
    }
}
