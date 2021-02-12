package se.iths.groupmembers.spi;

import com.google.gson.Gson;
import se.iths.db.JPA;

import java.io.PrintStream;
import java.net.Socket;

public interface Page {
    void doGet(Socket socket, boolean head, PrintStream printStream, Gson gson, JPA dao);

    void doPost(Socket socket, String body, boolean head, PrintStream printStream, Gson gson, JPA dao);

    String getPath();
}
