package se.iths.groupmembers.spi;

import java.net.Socket;

public interface Page {
    void load(Socket socket, boolean head);

    void load(Socket socket, String body, boolean head);

    String getPath();
}
