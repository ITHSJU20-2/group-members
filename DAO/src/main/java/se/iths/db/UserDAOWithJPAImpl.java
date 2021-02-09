package se.iths.db;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class UserDAOWithJPAImpl implements UserDAO {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("GroupMembers");

    @Override
    public List<User> getByFirstName(String firstName) {
        List<User> list;
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        list = em.createQuery("from User u where u.firstName = :firstName", User.class)
                .setParameter("firstName", firstName).getResultList();
        em.getTransaction().commit();
        return list;
    }

    @Override
    public List<User> getByLastName(String lastName) {
        List<User> list;
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        list = em.createQuery("from User u where u.lastName = :lastName", User.class)
                .setParameter("lastName", lastName).getResultList();
        em.getTransaction().commit();
        return list;
    }
}
