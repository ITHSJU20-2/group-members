package se.iths.groupmembers.router;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class LoadHandler {

    public static void load(Socket socket, String fileName, Status status, boolean head) {
        if (fileName.isEmpty()) {
            fileName = "index.html";
        }
        if (!fileName.contains(".")) {
            fileName += ".html";
        }
        try {
            PrintStream printStream = new PrintStream(socket.getOutputStream());
            int length;
            File file = new File(new File("router/src/main/resources/static/" + fileName).getAbsoluteFile().toString());
            FileInputStream fileInputStream = new FileInputStream(file);

            byte[] bytes = new byte[1024];

            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            while ((length = fileInputStream.read(bytes)) != -1) {
                bos.write(bytes, 0, length);
            }
            bos.flush();
            bos.close();
            byte[] data1 = bos.toByteArray();

            String contentType = Files.probeContentType(file.toPath());
            if (fileName.endsWith(".js")) {
                contentType = "text/javascript";
            }

            printStream.printf("HTTP/1.1 %d %s%n", status.getStatus(), status.getStatusString());
            printStream.println("Content-Type: " + contentType);
            printStream.println("Content-Length: " + data1.length);
            if (!head) {
                printStream.println();

                if (!contentType.startsWith("image") && !contentType.startsWith("application")) {
                    String contents = new String(data1, StandardCharsets.UTF_8);
                    printStream.println(contents);
                } else {
                    printStream.write(data1);
                }
            }
            printStream.flush();
            printStream.close();

            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
