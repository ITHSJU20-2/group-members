package se.iths.db;

public class UserQuery {
    public static void main(String[] args) {
        UserDAO udao = new UserDAOWithJPAImpl();

        System.out.println(udao.getByFirstName("Ulla"));
    }
}
