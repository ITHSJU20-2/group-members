package se.iths.groupmembers;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static final int PORT = 5050;

    public static void main(String[] args) {
        System.out.println("Starting server...");

        ExecutorService executorService = Executors.newCachedThreadPool();

        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server started!\nListening on http://localhost:" + PORT);

            while (true) {
                Socket socket = serverSocket.accept();

                executorService.execute(() -> handleConnection(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleConnection(Socket socket) {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while (true) {
                String line = input.readLine();
                System.out.println(line);
                if (line == null || line.isEmpty()) {
                    break;
                }
            }
            PrintWriter output = new PrintWriter(socket.getOutputStream());

//            String page = "<html><head><title>Demo page</title></head><body><h1>Hello World</h1></body></html>";
//
//            writeHTML(output, page);
            File test = new File(new File("core/src/main/resources/static/index.html").getAbsoluteFile().toString());
            renderFile(socket, test, "text/html; Charset=UTF-8", test.getName());
            output.flush();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeHTML(PrintWriter writer, String html) {
        writer.println("HTTP/1.1 200 OK");
        writer.println("Content-Length: " + html.getBytes().length);
        writer.println("Content-Type: text/html");
        writer.println("");
        writer.println(html);
    }

    private static void writeHTML(PrintStream printStream, String html) {
        printStream.println("HTTP/1.1 200 OK");
        printStream.println("Content-Length: " + html.getBytes().length);
        printStream.println("Content-Type: text/html");
        printStream.println();
        printStream.println(html);
    }

    private static void renderFile(Socket socket, File file, String fileType, String fileName) {
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

            String dataString = new String(data1, StandardCharsets.UTF_8);

            writeHTML(printStream, dataString);

            printStream.flush();
            printStream.close();

            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
