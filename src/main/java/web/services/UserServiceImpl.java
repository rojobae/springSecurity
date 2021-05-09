package web.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.UserDAO;
import web.models.Role;
import web.models.User;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDAO.getUserByUsername(username);
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : user.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getRole()));
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }

    @Override
    public User getUserById(int id) {
        Optional<User> userFromDb = Optional.ofNullable(userDAO.getUserById(id));
        return userFromDb.orElse(new User());
    }

    @Override
    public User getUserByUsername(String username) {
        return userDAO.getUserByUsername(username);
    }

    @Override
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    public Role getRoleById(int id) {
        return userDAO.getRoleById(id);
    }

    @Override
    public List<Role> getAllRoles() {
        return userDAO.getAllRoles();
    }

    @Override
    public void addUser(User user) {

        if (userDAO.getUserByUsername(user.getUsername()) == null) {
            userDAO.addUser(user);
        }
    }

    @Override
    public void removeUser(int id) {
        userDAO.removeUser(id);
    }

    @Override
    public void updateUser(User user) {
        userDAO.updateUser(user);
    }
}
