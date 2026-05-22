package co.edu.unbosque.chistesneco.repository;

import co.edu.unbosque.chistesneco.entity.HistorialChiste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistorialChisteRepository extends JpaRepository<HistorialChiste, Long> {
}