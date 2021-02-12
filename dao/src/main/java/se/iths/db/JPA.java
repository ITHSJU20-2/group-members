package se.iths.db;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class JPA implements UserDAO {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("GroupMembers");

    @Override
    public boolean getByFirstName(String firstName) {
        boolean success = false;
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        User u = em.createQuery("from User u where u.firstName = :firstName", User.class)
                .setParameter("firstName", firstName).getSingleResult();
        if (u != null) {
            System.out.println(u);
            success = true;
        }
        em.getTransaction().commit();
        return success;
    }


    @Override
    public boolean getById(int id) {
        boolean success = false;
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        User u = em.find(User.class, id);
        if (u != null) {
            System.out.println(u);
            success = true;
        }
        em.getTransaction().commit();
        return success;
    }

    @Override
    public User getByFirstLast(String firstName, String lastName) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        User u = em.createQuery("from User u where u.firstName = :firstName and u.lastName = :lastName", User.class)
                .setParameter("firstName", firstName)
                .setParameter("lastName", lastName).getSingleResult();
        em.getTransaction().commit();
        if (u != null) {
            return u;
        }
        return null;
    }

    @Override
    public List<User> getAll() {
        List<User> list;
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        list = em.createQuery("from User u", User.class).getResultList();
        em.getTransaction().commit();
        return list;
    }

    @Override
    public boolean add(String firstName, String lastName) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(new User(firstName, lastName));
        em.getTransaction().commit();
        return true;
    }

    @Override
    public boolean removeByFirstName(String firstName) {
        boolean success = false;
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        User u = em.createQuery("from User u where u.firstName = :firstName", User.class)
                .setParameter("firstName", firstName).getSingleResult();
        System.out.println(u);
        if (u != null)
            em.remove(u);
        success = true;
        em.getTransaction().commit();
        return success;
    }

    @Override
    public boolean removeByLastName(String lastName) {
        boolean success = false;
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        User u = em.createQuery("from User u where u.lastName = :lastName", User.class)
                .setParameter("lastName", lastName).getSingleResult();
        System.out.println(u);
        if (u != null)
            em.remove(u);
        success = true;
        em.getTransaction().commit();
        return success;
    }

    @Override
    public boolean removeById(int id) {
        boolean success = false;
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        User u = em.find(User.class, id);
        if (u != null) {
            em.remove(u);
            success = true;
            System.out.println("Record with ID " + u.getId() + " has been deleted from table");
        }
        em.getTransaction().commit();
        return success;
    }

    @Override
    public boolean updateByFirstLast(String firstName, String lastName, String newFirstName, String newLastName) {
        boolean success = false;
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        User u = em.createQuery("from User u where u.firstName = :firstName and u.lastName = :lastName", User.class)
                .setParameter("firstName", firstName)
                .setParameter("lastName", lastName).getSingleResult();
        if (u != null) {
            u.setFirstName(newFirstName);
            u.setLastName(newLastName);
            success = true;
        }
        em.getTransaction().commit();
        return success;
    }

}

