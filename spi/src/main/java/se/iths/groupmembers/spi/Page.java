package se.iths.groupmembers.spi;

import java.net.Socket;

public interface Page {
    void load(Socket socket);
    String getPath();
}
