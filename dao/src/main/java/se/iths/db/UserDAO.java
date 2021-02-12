package se.iths.db;

import java.util.List;

public interface UserDAO {

    List<User> getByFirstName(String FirstName);

    List<User> getAll();

    boolean add(String firstName, String lastName);

    boolean removeByFirstName(String firstName);

    boolean removeByLastName(String lastName);

    boolean removeById(int id);

    boolean updateByFirstLast(String firstName, String lastName, String newFirstName, String newLastName);
}
