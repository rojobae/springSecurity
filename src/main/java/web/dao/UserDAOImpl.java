package web.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import web.models.Role;
import web.models.User;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Transactional
@EnableTransactionManagement(proxyTargetClass = true)
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void addUser(User user) {
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        entityManager.persist(user);
    }

    @Override
    public void updateUser(User user) {
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        entityManager.merge(user);
    }

    @Override
    public void removeUser(int id) {
        if (getUserById(id) != null) {
            entityManager.remove(getUserById(id));
            entityManager.flush();
            entityManager.clear();
        }
    }

    @Override
    public User getUserById(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User getUserByUsername(String username) {
        return entityManager.find(User.class, username);
    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    public Role getRoleById(int id) {
        return entityManager.createQuery("from Role where id = '" + id + "'", Role.class).getSingleResult();
    }

    @Override
    public List<Role> getAllRoles() {
        return entityManager.createQuery("SELECT r FROM Role r", Role.class).getResultList();
    }

}
