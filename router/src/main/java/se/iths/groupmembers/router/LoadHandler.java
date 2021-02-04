package se.iths.groupmembers.router;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class LoadHandler {

    public static void load(Socket socket, File file, String contentType) {
        try {
        PrintStream printStream = new PrintStream(socket.getOutputStream());
        int length = 0;
        FileInputStream fileInputStream = new FileInputStream(file);

        byte[] bytes = new byte[1024];

        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        while ((length = fileInputStream.read(bytes)) != -1) {
            bos.write(bytes, 0, length);
        }
        bos.flush();
        bos.close();
        byte[] data1 = bos.toByteArray();

        String html = new String(data1, StandardCharsets.UTF_8);


        printStream.println("HTTP/1.1 200 OK");
        printStream.println("Content-Length: " + html.getBytes().length);
        printStream.println("Content-Type: " + contentType);
        printStream.println();
        printStream.println(html);

        printStream.flush();
        printStream.close();

        fileInputStream.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }
}
