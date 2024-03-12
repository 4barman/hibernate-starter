package ru.fourbarman;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.fourbarman.entity.User;
import ru.fourbarman.util.HibernateUtil;

public class HibernateRunner {
    private static final Logger log = LoggerFactory.getLogger(HibernateRunner.class);

    public static void main(String[] args) {

        User user = User.builder()
                .username("ivan@gamil.com")
                .lastname("Ivanov")
                .firstname("Ivan")
                .build();
        log.info("User entity is in Transient state: {}", user);
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            Session session1 = sessionFactory.openSession();
            try (session1) {
                Transaction transaction = session1.beginTransaction();
                log.info("Transaction is created: {}", transaction);

                session1.saveOrUpdate(user);
                log.trace("User is in Persistent state: {}, session: {}", user, session1);

                session1.getTransaction().commit();
            }
            log.warn("User is in Detached state: {}, session is closed: {}", user, session1);
        } catch (Exception e) {
            log.error("Exception occurred", e);
            throw e;
        }
    }
}