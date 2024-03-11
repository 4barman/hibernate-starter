package ru.fourbarman;


import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.fourbarman.converter.BirthdayConverter;
import ru.fourbarman.entity.Birthday;
import ru.fourbarman.entity.Role;
import ru.fourbarman.entity.User;

import java.time.LocalDate;

public class HibernateRunner {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        //auto convert Birthday to Date
        //configuration.addAttributeConverter(new BirthdayConverter(), true);

        //OR without autoApply:true here - set @Converter(autoApply = true) on Converter Class and then:
        configuration.addAttributeConverter(new BirthdayConverter());

        //add JsonBinaryType type converter
        configuration.registerTypeOverride(new JsonBinaryType());
        configuration.configure();

        try (SessionFactory sessionFactory = configuration.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User user = User.builder()
                    .username("ivan11@gmail.com")
                    .firstname("Ivan")
                    .lastname("Ivanov")
                    .info("""
                            {
                            "name": "Ivan",
                            "id": 25
                            }
                            """)
                    .role(Role.ADMIN)
                    .birthDate(new Birthday(LocalDate.of(2000, 1, 1)))
                    .build();
            //session.save(user);
//            session.update(user); will throw exception if not found!
//            session.saveOrUpdate(user);
//            session.delete(user);
            User u = session.get(User.class, "ivan10@gmail.com");

            session.getTransaction().commit();
        }
    }
}