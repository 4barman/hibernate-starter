package ru.fourbarman.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.fourbarman.converter.BirthdayConverter;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users", schema = "public")
public class User {
    @Id
    private String username;
    private String firstname;
    private String lastname;
    @Column(name = "birth_date")
    //@Convert(converter = BirthdayConverter.class)
    private Birthday birthDate;
    @Enumerated(EnumType.STRING)
    private Role role;
}
