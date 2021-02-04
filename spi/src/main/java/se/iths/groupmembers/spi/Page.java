package se.iths.groupmembers.spi;

import java.net.Socket;

public interface Page {
    String path = "";
    void load(Socket socket);
    String getPath();
}
