package se.iths.groupmembers.router.get.pdf;

import com.google.gson.Gson;
import se.iths.db.JPA;
import se.iths.groupmembers.router.LoadHandler;
import se.iths.groupmembers.router.Status;
import se.iths.groupmembers.spi.Page;
import se.iths.groupmembers.spi.Path;

import java.io.PrintStream;
import java.net.Socket;

@Path(path = "pdf/Laboration1.pdf")
public class Laboration1PDF implements Page {

    @Override
    public void doGet(Socket socket, boolean head, PrintStream printStream, Gson gson, JPA dao) {
        LoadHandler.load(this.getClass().getAnnotation(Path.class).path(), Status.OK, head, printStream);
    }

    @Override
    public void doPost(Socket socket, String body, boolean head, PrintStream printStream, Gson gson, JPA dao) {
        doGet(socket, head, printStream, gson, dao);
    }
}
