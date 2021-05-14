package web.dao;

import web.models.User;

import java.util.List;

public interface UserDAO {

    void addUser(User user);

    void updateUser(User user);

    void removeUser(int id);

    User getUserById(int id);

    User getUserByUsername(String username);

    List<User> getAllUsers();
}
