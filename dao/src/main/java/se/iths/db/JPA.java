package se.iths.db;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class JPA implements UserDAO {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("GroupMembers");

@Override
public User getByFirstName(String firstName) {
    EntityManager em = emf.createEntityManager();
    em.getTransaction().begin();
    User u = em.createQuery("from User u where u.firstName = :firstName", User.class)
            .setParameter("firstName", firstName).getSingleResult();
        if (u != null) {
            System.out.println(u);
        }
    em.getTransaction().commit();
        em.close();
        return u;
}


    @Override
    public User getById(int id){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        User u = em.find(User.class, id);
        if (u != null) {
            System.out.println(u);
        }
        em.getTransaction().commit();
        em.close();
        return u;
    }

    @Override
    public List<User> getAll() {
        List<User> list;
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        list = em.createQuery("from User u", User.class).getResultList();
        em.getTransaction().commit();
        em.close();
        return list;
    }

    @Override
    public User add(String firstName, String lastName) {
        EntityManager em = emf.createEntityManager();
        User u = new User(firstName, lastName);
        em.getTransaction().begin();
        em.persist(u);
        em.getTransaction().commit();
        em.close();
        return u;
    }

    @Override
    public User removeByFirstName(String firstName) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        User u = em.createQuery("from User u where u.firstName = :firstName", User.class)
                .setParameter("firstName", firstName).getSingleResult();
        System.out.println(u);
        if (u != null)
            em.remove(u);
        em.getTransaction().commit();
        em.close();
        return u;
    }

    @Override
    public User removeByLastName(String lastName) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        User u = em.createQuery("from User u where u.lastName = :lastName", User.class)
                .setParameter("lastName", lastName).getSingleResult();
        System.out.println(u);
        if (u != null)
            em.remove(u);
        em.getTransaction().commit();
        em.close();
        return u;
    }

    @Override
    public User removeById(int id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        User u = em.find(User.class, id);
            if (u != null) {
                em.remove(u);
                System.out.println("Record with ID " + u.getId() + " has been deleted from table");
            }
            em.getTransaction().commit();
            em.close();
            return u;
    }

    @Override
    public User updateByFirstLast(String firstName, String lastName, String newFirstName, String newLastName) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        User u = em.createQuery("from User u where u.firstName = :firstName and u.lastName = :lastName", User.class)
                .setParameter("firstName", firstName)
                .setParameter("lastName", lastName).getSingleResult();
        if (u != null) {
            u.setFirstName(newFirstName);
            u.setLastName(newLastName);
        }
        em.getTransaction().commit();
        em.close();
        return u;
    }

}

