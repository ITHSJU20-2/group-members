package se.iths.db;

public class UserQuery {
    public static void main(String[] args) {
        UserDAO udao = new UserDAOWithJPAImpl();

        System.out.println(udao.getByFirstName("Marcus"));
        System.out.println(udao.getByFirstName("Tobias"));
        System.out.println(udao.getByLastName("Lloyd Jones"));
        System.out.println(udao.getByLastName("Jartun"));
    }

}
