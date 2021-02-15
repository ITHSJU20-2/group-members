package se.iths.groupmembers.router;

import com.google.gson.Gson;
import se.iths.db.User;

import java.io.*;
import java.nio.file.Files;

public class LoadHandler {

    public static void load(String fileName, Status status, boolean head, PrintStream printStream) {
        try {
            if (fileName.isEmpty()) {
                fileName = "index.html";
            }
            if (!fileName.contains(".")) {
                fileName += ".html";
            }

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
            byte[] output = bos.toByteArray();

            String contentType = Files.probeContentType(file.toPath());

            if (fileName.endsWith(".js")) {
                contentType = "text/javascript";
            }

            print(printStream, output, status, contentType, head);

            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void print(PrintStream printStream, byte[] output, Status status, String contentType, boolean head) {
        try {
            printStream.printf("HTTP/1.1 %d %s%n", status.getStatus(), status.getStatusString());
            printStream.println("Content-Type: " + contentType);
            printStream.println("Content-Length: " + output.length);
            if (!head) {
                printStream.println();
                printStream.write(output);
            }
            printStream.flush();
            printStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printJson(User u, Gson gson, PrintStream printStream, Boolean head) {
        byte[] output;
        Status status;

        if (u == null) {
            status = Status.NOT_FOUND;
            output = "{\n\"ok\":false\n}".getBytes();
        } else {
            status = Status.OK;
            output = gson.toJson(u, User.class).getBytes();
        }

        LoadHandler.print(printStream, output, status, "application/json", head);
    }

}
