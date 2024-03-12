package ru.fourbarman;


import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.fourbarman.entity.PersonalInfo;
import ru.fourbarman.entity.User;
import ru.fourbarman.util.HibernateUtil;

@Slf4j
public class HibernateRunner {

    public static void main(String[] args) {

        User user = User.builder()
                .username("petr@gamil.com")
                .personalInfo(
                        PersonalInfo.builder()
                                .lastname("Petr")
                                .firstname("Petrov")
                                .build()
                )
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