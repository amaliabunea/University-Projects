package repository;

import model.Farmacist;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class FarmacistRepository {

    private SessionFactory sessionFactory;

    public FarmacistRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Boolean findOne(String username, String password){
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Farmacist login =
                        session.createQuery("from Farmacist where nume = :user and password = :pass", Farmacist.class)
                                .setString("user", username)
                                .setString("pass", password)
                                .uniqueResult();

                if(login != null){
                    return true;
                }

            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        return false;
    }

}
