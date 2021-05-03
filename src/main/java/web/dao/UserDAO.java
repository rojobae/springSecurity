package web.dao;

import web.models.User;
import java.util.List;

public interface UserDAO {

    void addUser(User user);

    void updateUser(int id, User user);

    void removeUser(int id);

    User getUserById(int id);

    List<User> getAllUsers();
}
