package se.iths.db;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class JPAMain {
    public static void main(String[] args) {


        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPAMarcus");

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        User u = new User("19960622-5291", "Marcus", "Lärk");
        em.persist(u);
        System.out.println("Personal-Number = " + u.getId());

        List<User> list = em.createQuery("from User", User.class)
                .getResultList();
        System.out.println(list);
        em.getTransaction().commit();

        em.close();
    }
}
