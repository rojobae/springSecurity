package web.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import web.models.User;

import java.util.List;

public interface UserService extends UserDetailsService {

    void addUser(User user);

    void updateUser(User user);

    void removeUser(int id);

    User getUserById(int id);

    List<User> getAllUsers();

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
