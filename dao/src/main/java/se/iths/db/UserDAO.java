package se.iths.db;

import java.util.List;

public interface UserDAO {

    List<User> getByFirstName(String FirstName);

    List<User> getAll();
}
