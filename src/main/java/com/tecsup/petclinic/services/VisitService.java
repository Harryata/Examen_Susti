package com.tecsup.petclinic.services;

import java.util.Date;
import java.util.List;

import com.tecsup.petclinic.entities.Visit;
import com.tecsup.petclinic.exception.VisitNotFoundException;

public interface VisitService {

    /** Crea una visita */
    Visit create(Visit visit);

    /** Actualiza una visita */
    Visit update(Visit visit);

    /** Elimina una visita por ID */
    void delete(Integer id) throws VisitNotFoundException;

    /** Encuentra una visita por ID */
    Visit findById(Integer id) throws VisitNotFoundException;

    /** Encuentra visitas por el ID de mascota */
    List<Visit> findByPetId(Integer petId);

    /** Encuentra visitas por fecha */
    List<Visit> findByVisitDate(Date visitDate);

    /** Busca visitas por palabra clave en la descripción */
    List<Visit> findByDescriptionContaining(String keyword);

    /** Retorna todas las visitas */
    List<Visit> findAll();
}
