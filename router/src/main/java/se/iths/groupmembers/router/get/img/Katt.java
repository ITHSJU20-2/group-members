package se.iths.groupmembers.router.get.img;

import se.iths.groupmembers.router.LoadHandler;
import se.iths.groupmembers.router.Status;
import se.iths.groupmembers.spi.Page;

import java.net.Socket;

    public class Katt implements Page {
        private final String path;

        public Katt() {
            path = "img/Katt.jpg";
        }

        @Override
        public void load(Socket socket) {
            LoadHandler.load(socket, path, Status.OK);
        }

        @Override
        public String getPath() {
            return path;
        }
}
