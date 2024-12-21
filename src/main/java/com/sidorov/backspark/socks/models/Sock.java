package com.sidorov.backspark.socks.models;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.proxy.HibernateProxy;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "socks")
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class Sock {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sock_id_seq")
    @SequenceGenerator(name = "sock_id_seq", sequenceName = "sock_id_seq", allocationSize = 1)
    Long id;

    @Column(name = "count")
    @NotNull(message = "field count cant be null")
    @Min(value = 0, message = "Socks count can`t be less then 0")
    Long count;

    @Column(name = "cotton_percentage")
    @NotNull(message = "percentage of cotton can`t be null")
    @Min(value = 0, message = "percentage of cotton can`t be less then 0")
    @Max(value = 100, message = "the percentage can't be more than 100 in the lineup")
    Short cottonPercentage;

    @Column(name = "color")
    @NotNull(message = "socks can`t be invisible")
    String color;

    @Override
    public String toString() {
        return "Sock{" +
                "id=" + id +
                ", count=" + count +
                ", cottonPercentage=" + cottonPercentage +
                ", color='" + color + '\'' +
                '}';
    }


    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Sock sock = (Sock) o;
        return id != null && Objects.equals(id, sock.id);
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
