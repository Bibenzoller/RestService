package com.concretepage.auth.dao;

import com.concretepage.auth.entity.User;
import com.concretepage.auth.entity.UserRole;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Transactional
@Repository
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public User getUserByUsername(String username) {

        return entityManager.find(User.class, username);
    }
    @SuppressWarnings("unchecked")
    @Override
    public List<User> getAllUsers() {
        String hql = "FROM User as atcl ORDER BY atcl.username";
        return (List<User>) entityManager.createQuery(hql).getResultList();
    }
    @Override
    public void addUser(User user, UserRole role) {
        entityManager.persist(user);
        entityManager.persist(role);
    }

    @Override
    public void deleteUser(String username) {
        entityManager.remove(getUserByUsername(username));
    }

    @Override
    public boolean userExists(String username, String password) {
        String hql = "FROM User as atcl WHERE atcl.username = ? and atcl.password = ?";
        int count = entityManager.createQuery(hql).setParameter(1, username).setParameter(2, password).getResultList().size();
        return count > 0 ? true : false;
    }
}