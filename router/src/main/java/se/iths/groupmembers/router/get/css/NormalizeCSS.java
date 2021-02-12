package se.iths.groupmembers.router.get.css;

import com.google.gson.Gson;
import se.iths.db.JPA;
import se.iths.groupmembers.router.LoadHandler;
import se.iths.groupmembers.router.Status;
import se.iths.groupmembers.spi.Page;

import java.io.PrintStream;
import java.net.Socket;

public class NormalizeCSS implements Page {
    private final String path;

    public NormalizeCSS() {
        path = "css/normalize.css";
    }

    @Override
    public void doGet(Socket socket, boolean head, PrintStream printStream, Gson gson, JPA dao) {
        LoadHandler.load(path, Status.OK, head, printStream);
    }

    @Override
    public void doPost(Socket socket, String body, boolean head, PrintStream printStream, Gson gson, JPA dao) {
        doGet(socket, head, printStream, gson, dao);
    }

    @Override
    public String getPath() {
        return path;
    }
}
