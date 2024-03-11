package ru.fourbarman;


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
        configuration.configure();

        try (SessionFactory sessionFactory = configuration.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User user = User.builder()
                    .username("ivan5@gmail.com")
                    .firstname("Ivan")
                    .lastname("Ivanov")
                    .role(Role.ADMIN)
                    .birthDate(new Birthday(LocalDate.of(2000, 1, 1)))
                    .build();
            session.save(user);

            session.getTransaction().commit();
        }
    }
}