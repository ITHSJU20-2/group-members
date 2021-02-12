package se.iths.db;

import java.util.List;

public interface UserDAO {


    boolean getByFirstName(String firstName);

    List<User> getAll();

    boolean getById(int id);

    User getByFirstLast(String firstName, String lastName);

    boolean add(String firstName, String lastName);

    boolean removeByFirstName(String firstName);

    boolean removeByLastName(String lastName);

    boolean removeById(int id);

    boolean updateByFirstLast(String firstName, String lastName, String newFirstName, String newLastName);
}
