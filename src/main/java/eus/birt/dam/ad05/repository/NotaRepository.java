package eus.birt.dam.ad05.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import eus.birt.dam.ad05.entity.Nota;

// Extiende JpaRepository para tener ya métodos listos como findAll(), findById() y save()
public interface NotaRepository extends JpaRepository<Nota, Long> {
}