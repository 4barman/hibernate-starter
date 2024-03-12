package ru.fourbarman.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import ru.fourbarman.converter.BirthdayConverter;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@TypeDef(name = "json", typeClass = JsonBinaryType.class)
@Table(name = "users", schema = "public")
public class User {

    @EmbeddedId
    private PersonalInfo personalInfo;

    @Column(unique = true)
    private String username;

    @Type(type = "json")
    private String info;

    @Enumerated(EnumType.STRING)
    private Role role;
}
