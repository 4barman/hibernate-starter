package ru.fourbarman;


import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;
import ru.fourbarman.converter.BirthdayConverter;
import ru.fourbarman.entity.User;
import ru.fourbarman.util.HibernateUtil;

public class HibernateRunner {
    public static void main(String[] args) {

        User user = User.builder()
                .username("ivan@gamil.com")
                .lastname("Ivanov")
                .firstname("Ivan")
                .build();

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
             try (Session session1 = sessionFactory.openSession()) {
                session1.beginTransaction();

                session1.saveOrUpdate(user);

                session1.getTransaction().commit();
            }
            try (Session session2 = sessionFactory.openSession()) {
                session2.beginTransaction();

                user.setFirstname("Sveta");
                //session2.delete(user);

                //refresh/merge
                //session2.refresh(user); //refresh user from db

                Object mergedUser = session2.merge(user); //merge user to db

                session2.getTransaction().commit();
            }
        }
    }
}