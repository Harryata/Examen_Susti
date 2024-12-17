package com.tecsup.petclinic.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.tecsup.petclinic.entities.Pet;
import com.tecsup.petclinic.exception.VisitNotFoundException;
import org.junit.jupiter.api.Test;

import com.tecsup.petclinic.entities.Visit;
import com.tecsup.petclinic.exception.PetNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@SpringBootTest
@Slf4j
public class VisitServiceTest {

    @Autowired
    private VisitService visitService;

    @Autowired
    private PetService petService;

    @Test
    public void testFindVisitById() {

        Integer ID = 3; // Ajustar
        String DESCRIPTION = "neutered"; // Ajustar
        Visit visit = null;

        try {
            visit = this.visitService.findById(ID);
        } catch (VisitNotFoundException e) {
            fail(e.getMessage());
        }

        log.info("Visit encontrado: " + visit);
        assertEquals(DESCRIPTION, visit.getDescription());
    }

    @Test
    public void testCreateVisit() {

        Integer PET_ID = 1; // Ajustar
        String DESCRIPTION = "Control de rutina";
        String DATE_STRING = "2024-01-10";

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date visitDate = null;
        try {
            visitDate = df.parse(DATE_STRING);
        } catch (ParseException e) {
            fail("Error parseando fecha: " + e.getMessage());
        }

        Pet pet = null;
        try {
            pet = petService.findById(PET_ID);
        } catch (PetNotFoundException e) {
            fail("No se encontró la mascota con ID: " + PET_ID);
        }

        Visit visit = new Visit(pet, visitDate, DESCRIPTION);

        Visit visitCreated = this.visitService.create(visit);

        log.info("VISIT CREATED: " + visitCreated);

        assertNotNull(visitCreated.getId(), "La visita creada debería tener un ID no nulo");
        assertEquals(pet.getId(), visitCreated.getPet().getId(), "El ID de la mascota debería coincidir");
        assertEquals(DESCRIPTION, visitCreated.getDescription(), "La descripción debería coincidir");
        assertEquals(visitDate, visitCreated.getVisitDate(), "La fecha de la visita debería coincidir");
    }
}
