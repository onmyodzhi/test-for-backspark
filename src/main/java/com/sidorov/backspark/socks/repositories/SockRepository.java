package com.sidorov.backspark.socks.repositories;

import com.sidorov.backspark.socks.models.Sock;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SockRepository extends JpaRepository<Sock, Long>, JpaSpecificationExecutor<Sock> {

    @Query("SELECT s FROM Sock s " +
            "WHERE s.color = :#{#sock.color} " +
            "AND s.cottonPercentage = :#{#sock.cottonPercentage}")
    Optional<Sock> existsSock(Sock sock);
}
