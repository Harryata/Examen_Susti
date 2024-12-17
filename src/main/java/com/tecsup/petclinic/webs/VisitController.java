package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.domain.VisitTO;
import com.tecsup.petclinic.entities.Visit;
import com.tecsup.petclinic.exception.VisitNotFoundException;
import com.tecsup.petclinic.mapper.VisitMapper;
import com.tecsup.petclinic.services.VisitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Controlador para la entidad Visit
 */
@RestController
@Slf4j
public class VisitController {

    private final VisitService visitService;
    private final VisitMapper visitMapper;
    // Formato de fecha
    private final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    public VisitController(VisitService visitService, VisitMapper visitMapper) {
        this.visitService = visitService;
        this.visitMapper = visitMapper;
    }

    /**
     * Obtener todas las visitas
     * Lista de VisitTO
     */
    @GetMapping(value = "/visits")
    public ResponseEntity<List<VisitTO>> findAllVisits() {

        List<Visit> visits = visitService.findAll();
        log.info("visits: {}", visits);
        visits.forEach(item -> log.info("Visit >> {}", item));

        List<VisitTO> visitsTO = visitMapper.toVisitTOList(visits);
        log.info("visitsTO: {}", visitsTO);
        visitsTO.forEach(item -> log.info("VisitTO >> {}", item));

        return ResponseEntity.ok(visitsTO);
    }

    /**
     * Crear una nueva visita
     */
    @PostMapping(value = "/visits")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<VisitTO> create(@RequestBody VisitTO visitTO) {

        Visit newVisit = visitMapper.toVisit(visitTO);

        if (visitTO.getVisitDate() != null) {
            try {
                newVisit.setVisitDate(dateFormatter.parse(visitTO.getVisitDate()));
            } catch (ParseException e) {
                log.error("Error al parsear la fecha: {}", visitTO.getVisitDate(), e);
                return ResponseEntity.badRequest().build();
            }
        }

        Visit createdVisit = visitService.create(newVisit);
        VisitTO newVisitTO = visitMapper.toVisitTO(createdVisit);

        return ResponseEntity.status(HttpStatus.CREATED).body(newVisitTO);
    }

    /**
     * Buscar una visita por su ID
     */
    @GetMapping(value = "/visits/{id}")
    public ResponseEntity<VisitTO> findById(@PathVariable Integer id) {
        try {
            Visit visit = visitService.findById(id);
            VisitTO visitTO = visitMapper.toVisitTO(visit);
            return ResponseEntity.ok(visitTO);
        } catch (VisitNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Actualizar una visita existente
     */
    @PutMapping(value = "/visits/{id}")
    public ResponseEntity<VisitTO> update(@RequestBody VisitTO visitTO, @PathVariable Integer id) {
        try {
            Visit updateVisit = visitService.findById(id);


            if (visitTO.getVisitDate() != null) {
                try {
                    updateVisit.setVisitDate(dateFormatter.parse(visitTO.getVisitDate()));
                } catch (ParseException e) {
                    log.error("Error al parsear la fecha: {}", visitTO.getVisitDate(), e);
                    return ResponseEntity.badRequest().build();
                }
            }

            // Actualizar descripción
            updateVisit.setDescription(visitTO.getDescription());

            // Si se envia petId, podrías recuperar la entidad Pet

            updateVisit = visitService.update(updateVisit);
            VisitTO updatedVisitTO = visitMapper.toVisitTO(updateVisit);
            return ResponseEntity.ok(updatedVisitTO);
        } catch (VisitNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Eliminar una visita por su ID
     */
    @DeleteMapping(value = "/visits/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        try {
            visitService.delete(id);
            return ResponseEntity.ok("Delete ID: " + id);
        } catch (VisitNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
