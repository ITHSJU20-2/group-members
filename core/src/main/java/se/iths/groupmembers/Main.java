package se.iths.groupmembers;

import se.iths.db.UserDAOWithJPAImpl;

public class Main {

    private static final int PORT = 5050;

    public static void main(String[] args) {
//        new Server().start(PORT);
        UserDAOWithJPAImpl dao = new UserDAOWithJPAImpl();
        System.out.println(dao.getByFirstName("Tobias"));
    }
}
