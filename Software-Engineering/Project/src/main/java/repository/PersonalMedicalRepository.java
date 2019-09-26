package repository;

import model.PersonalMedical;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class PersonalMedicalRepository {

    private SessionFactory sessionFactory;

    public PersonalMedicalRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public PersonalMedical findOne(String username, String password){
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                PersonalMedical login =
                        session.createQuery("from PersonalMedical where nume = :user and password = :pass", PersonalMedical.class)
                                .setString("user", username)
                                .setString("pass", password)
                                .uniqueResult();

                if(login != null){
                    return login;
                }

            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        return null;
    }

    public Boolean findByUsenameAndPassword(String username, String password){
        PersonalMedical personalMedical = findOne(username, password);
        if(personalMedical != null){
            return true;
        }
        else {
            return false;
        }
    }

}