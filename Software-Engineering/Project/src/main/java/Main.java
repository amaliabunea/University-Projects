//import model.Comanda;
//import model.Medicament;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.Transaction;
//import org.hibernate.boot.MetadataSources;
//import org.hibernate.boot.registry.StandardServiceRegistry;
//import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
//import repository.ComandaRepository;
//import repository.MedicamentRepository;
//import utils.Factory;
//import java.time.LocalDate;
//import java.util.*;
//
//public class Main {
//
//    static SessionFactory sessionFactory;
//    static void initialize() {
//        // A SessionFactory is set up once for an application!
//        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
//                .configure() // configures settings from hibernate.cfg.xml
//                .build();
//        try {
//            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
//        }
//        catch (Exception e) {
//            StandardServiceRegistryBuilder.destroy( registry );
//        }
//    }
//
//    static void close(){
//        if ( sessionFactory != null ) {
//            sessionFactory.close();
//        }
//
//    }
//
//    public static void main(String[] args) {
//
//
//        try {
//            initialize();
//
//            Medicament medicament = new Medicament("parac", 0.75f, 120, 20);
//            Medicament medicament1 = new Medicament("parac1", 0.75f, 120, 20);
//            Medicament medicament2 = new Medicament("parac2", 0.75f, 120, 20);
//            Medicament medicament3 = new Medicament("parac3", 0.75f, 120, 20);
//
//            MedicamentRepository medicamentRepository = new MedicamentRepository(sessionFactory);
//            medicamentRepository.save(medicament);
//            medicamentRepository.save(medicament1);
//            medicamentRepository.save(medicament2);
//            medicamentRepository.save(medicament3);
//            List<Medicament> list =  medicamentRepository.getAll();
//            list.forEach(x -> System.out.println(x));
//
//            ComandaRepository comandaRepository = new ComandaRepository(sessionFactory);
//            Set<Medicament> medicamentList = new HashSet<>();
//            Set<Medicament> medicamentList1 = new HashSet<>();
//
//            medicamentList.add(medicament);
//            medicamentList.add(medicament1);
//            medicamentList.add(medicament2);
//            medicamentList.add(medicament3);
//
//            medicamentList1.add(medicament);
//            medicamentList1.add(medicament1);
//            medicamentList1.add(medicament2);
//            Comanda comanda = new Comanda(1, medicamentList, LocalDate.of(2012, 12, 4));
//            Comanda comanda2 = new Comanda(2, medicamentList1, LocalDate.of(2013, 4 ,5));
//            comandaRepository.save(comanda);
//            comandaRepository.save(comanda2);
//            List<Comanda> list1 = comandaRepository.getAll();
//            list1.forEach(x -> System.out.println(x));
//            System.out.println(list1.size());
//
//        }catch (Exception e){
//            System.err.println("Exception " + e);
//            e.printStackTrace();
//        }finally {
//            close();
//        }
//    }
//}
//
