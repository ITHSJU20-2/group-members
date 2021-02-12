package se.iths.groupmembers.spi;

import java.net.Socket;

public interface Page {
    void doGet(Socket socket, boolean head);

    void doPost(Socket socket, String body, boolean head);

    String getPath();
}
