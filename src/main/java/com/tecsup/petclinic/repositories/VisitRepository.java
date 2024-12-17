package com.tecsup.petclinic.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tecsup.petclinic.entities.Visit;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Integer> {

    // Buscar visitas por el ID mascota
    List<Visit> findByPetId(Integer petId);

    // Buscar visitas por fecha
    List<Visit> findByVisitDate(Date visitDate);

    // Buscar visitas que contengan cierto texto
    List<Visit> findByDescriptionContaining(String keyword);

    @Override
    List<Visit> findAll();

}
