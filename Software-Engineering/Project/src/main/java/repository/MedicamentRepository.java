package repository;

import model.Medicament;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class MedicamentRepository {

    private SessionFactory sessionFactory;

    public MedicamentRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public void save(Medicament medicament){
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.save(medicament);
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
    }

    public List<Medicament> getAll(){
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                List<Medicament> list =
                        session.createQuery("from Medicament", Medicament.class).list();
                //System.out.println(list.size() + " medicament(s) found:");
                tx.commit();
                return list;
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        return null;
    }

    public void update(Medicament medicament, Integer quantity){
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                Medicament medicament1 =
                        (Medicament) session.get(Medicament.class, medicament.getNume());
                medicament1.setCantitate(quantity);
                tx.commit();

            } catch(RuntimeException ex){
                if (tx!=null)
                    tx.rollback();
            }
        }
    }

    public Medicament findOne(String id){
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Medicament medicament =
                        session.createQuery("from Medicament where nume = :id", Medicament.class)
                                .setString("id", id)
                                .uniqueResult();

                return medicament;

            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        return null;
    }
}
