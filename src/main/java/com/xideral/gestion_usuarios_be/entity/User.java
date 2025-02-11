package com.xideral.gestion_usuarios_be.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import java.sql.Timestamp;
import java.sql.Date;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "date_created")
    private Date dateCreated;

    @Column(name = "last_updated")
    @UpdateTimestamp
    private Timestamp lastUpdated;

}
