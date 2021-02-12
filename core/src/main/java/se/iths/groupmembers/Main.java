package se.iths.groupmembers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Main {

    // TODO: Dependency injection
    // GSON DAO

    private static final int PORT = 5050;
    private static GsonBuilder gsonBuilder;
    private static Gson gson;

    public static void main(String[] args) {
        gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        gson = gsonBuilder.create();
        new Server().start(PORT);
    }
}
