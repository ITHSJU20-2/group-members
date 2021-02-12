package se.iths.groupmembers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import se.iths.db.JPA;

public class Main {

    private static final int PORT = 5050;

    public static void main(String[] args) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();

        JPA dao = new JPA();

        Server server = new Server();
        server.setGson(gson);
        server.setJpa(dao);
        server.setPort(PORT);

        server.start();
    }
}
