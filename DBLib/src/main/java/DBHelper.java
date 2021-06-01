import Entity.Student;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class DBHelper {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("db");
    EntityManager em = emf.createEntityManager();

    public void createStudent(Student student){
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(student);
        tx.commit();
        em.close();
        emf.close();

    }
}
