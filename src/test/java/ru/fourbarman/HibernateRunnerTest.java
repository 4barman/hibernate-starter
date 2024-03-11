package ru.fourbarman;

import org.junit.jupiter.api.Test;
import ru.fourbarman.entity.User;

import javax.persistence.Column;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

class HibernateRunnerTest {

    /**
     * How Hibernate builds query in example?
     */
    @Test
    void checkReflectionApi() {
        User user = User.builder()
                .username("ivan@gmail.com")
                .firstname("Ivan")
                .lastname("Ivanov")
                .birthDate(LocalDate.of(2000, 1, 1))
                .age(20)
                .build();
        String sql = """
                insert
                into
                %s
                (%s)
                values
                (%s)
                """;

        //retrieve table name from entity
        String tableName = Optional.ofNullable(user.getClass().getAnnotation(Table.class))
                .map(tableAnnotation -> tableAnnotation.schema() + "." + tableAnnotation.name())
                .orElse(user.getClass().getName());

        //retrieve entity field names
        Field[] declaredFields = user.getClass().getDeclaredFields();

        //retrieve column names from entity field names
        String columnNames = Arrays.stream(declaredFields)
                .map(field -> Optional.ofNullable(field.getAnnotation(Column.class))
                        .map(Column::name)
                        .orElse(field.getName()))
                .collect(Collectors.joining(", "));

        //retrieve column values
        String columnValues = Arrays.stream(declaredFields)
                .map(field -> "?")
                .collect(Collectors.joining(", "));

        System.out.println(sql.formatted(tableName, columnNames, columnValues));
    }

}