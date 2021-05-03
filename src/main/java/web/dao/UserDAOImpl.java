package web.dao;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import web.models.User;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
@Service
@EnableTransactionManagement(proxyTargetClass = true)
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void addUser(User user) {
        entityManager.persist(user);
    }

    @Transactional
    public void updateUser(int id, User user) {
        entityManager.merge(user);
    }

    @Transactional
    public void removeUser(int id) {
        if (getUserById(id) != null) {
            entityManager.remove(getUserById(id));
            entityManager.flush();
            entityManager.clear();
        }
    }

    @Transactional
    public User getUserById(int id) {
        return entityManager.find(User.class, id);
    }

    @Transactional
    public List<User> getAllUsers() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }
}
