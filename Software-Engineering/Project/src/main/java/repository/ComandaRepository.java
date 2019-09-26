package repository;

import model.Comanda;
import model.ElementComanda;
import model.PersonalMedical;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ComandaRepository {

    private SessionFactory sessionFactory;

    public ComandaRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(Comanda comanda) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                for (ElementComanda elem : comanda.getList()) {
                    elem.setComanda(comanda);
                    int idElem = (int) session.save(elem);
                    elem.setId(idElem);
                }
                int id = (int) session.save(comanda);
                comanda.setNrComanda(id);

                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
    }

    public Set<Comanda> getAll() {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                List<Comanda> list =
                        (List<Comanda>) session.createQuery("from Comanda", Comanda.class).list();
                //System.out.println(list.size() + " comenzi gasite:");
                for (Comanda c : list) {
                    List<ElementComanda> elems = (List<ElementComanda>) session.createQuery("from ElementComanda where Comanda=:nrComanda", ElementComanda.class)
                            .setInteger("nrComanda", c.getNrComanda()).list();
                    c.setList(new HashSet<>(elems));
                }
                Set<Comanda> set = new HashSet<>(list);
                tx.commit();
                return set;
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        return null;
    }

    public Set<Comanda> getAllComenziForSectie(PersonalMedical personalMedical) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                String username = personalMedical.getNume();
                List<Comanda> list =
                        (List<Comanda>) session.createQuery("from Comanda where personalMedical = :username", Comanda.class)
                                .setString("username", personalMedical.getNume())
                                .list();
                for (Comanda c : list) {
                    List<ElementComanda> elems = (List<ElementComanda>) session.createQuery("from ElementComanda where Comanda=:nrComanda", ElementComanda.class)
                            .setInteger("nrComanda", c.getNrComanda()).list();
                    c.setList(new HashSet<>(elems));
                }
                Set<Comanda> set = new HashSet<>(list);
                tx.commit();
                return set;
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        return null;
    }

    public Set<Comanda> getAllComenziNeonorate() {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                List<Comanda> list =
                        (List<Comanda>) session.createQuery("from Comanda", Comanda.class).list();
                System.out.println(list.size() + " comenzi gasite:");
                for (Comanda c : list) {
                    List<ElementComanda> elems = (List<ElementComanda>) session.createQuery("from ElementComanda where Comanda=:nrComanda", ElementComanda.class)
                            .setInteger("nrComanda", c.getNrComanda()).list();
                    c.setList(new HashSet<>(elems));
                }
                Set<Comanda> set = new HashSet<>(list);
                Set<Comanda> setComenziNeonorate = new HashSet<>();
                set.forEach(comanda -> {
                    if (comanda.getStatus().equals("Neonorata")) {
                        setComenziNeonorate.add(comanda);
                    }
                });
                tx.commit();
                return setComenziNeonorate;
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        return null;
    }

    public void delete(Comanda comanda) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

                for (ElementComanda elem : comanda.getList()) {
                    session.delete(elem);
                }
                Comanda result = session.createQuery("from Comanda where nrComanda =:numarComanda", Comanda.class)
                        .setInteger("numarComanda", comanda.getNrComanda())
                        .setMaxResults(1)
                        .uniqueResult();
                session.delete(result);
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
    }

    public void update(Comanda newComanda) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.update(newComanda);
                for (ElementComanda elem : newComanda.getList()) {
                    SQLQuery query = session.createSQLQuery("UPDATE ElementComanda set Cantitate=" + elem.getCantitate() + " where ID_ElementComanda="+elem.getId());
                    int res = query.executeUpdate();
                }
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
    }

    public void updateStatus(Comanda comanda) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Comanda oldComanda =
                        (Comanda) session.get(Comanda.class, comanda.getNrComanda());
                oldComanda.setStatus("Onorata");
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
    }
}
