package se.iths.groupmembers.router;

import se.iths.groupmembers.spi.Page;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Index implements Page {
    private final String path;

    public Index() {
        path = "";
    }

    @Override
    public void load(Socket socket) {
        File file = new File(new File("router/src/main/resources/static/index.html").getAbsoluteFile().toString());
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
            printStream.println("Content-Type: text/html");
            printStream.println();
            printStream.println(html);

            printStream.flush();
            printStream.close();

            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getPath() {
        return path;
    }
}
