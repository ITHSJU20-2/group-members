package se.iths.groupmembers.router;

import se.iths.groupmembers.router.get.html.GetUsers;

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
    public static void printStream (Socket socket) {
        try{
            PrintStream printStream = new PrintStream(socket.getOutputStream());


            printStream.println("HTTP/1.1 200 OK");
            printStream.println("Content-Type: application/json");
            printStream.println("Content-Length: " + bytes.length);
            if (!head) {
                printStream.println();
                printStream.println(contents);
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
