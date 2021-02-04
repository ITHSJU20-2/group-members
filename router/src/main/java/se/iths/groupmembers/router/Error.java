package se.iths.groupmembers.router;

import se.iths.groupmembers.spi.Page;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Error implements Page {

    private String path;

    public Error() {
        path = "error";
    }
    @Override
    public void load(Socket socket) {
        File file = new File(new File("router/src/main/resources/static/error.html").getAbsoluteFile().toString());
        LoadHandler.load(socket, file, "text/html");
    }

    @Override
    public String getPath() {
        return path;
    }
}
