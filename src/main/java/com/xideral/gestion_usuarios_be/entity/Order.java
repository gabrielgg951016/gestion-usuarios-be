package com.xideral.gestion_usuarios_be.entity;

import com.xideral.gestion_usuarios_be.enums.OrderStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.FetchType;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.UpdateTimestamp;
import java.sql.Date;
import java.sql.Timestamp;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "total")
    private Long total;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @ColumnTransformer(write = "?::status")
    private OrderStatus status;

    @Column(name = "date_created")
    private Date dateCreated;

    @Column(name = "last_updated")
    @UpdateTimestamp
    private Timestamp lastUpdated;
}

