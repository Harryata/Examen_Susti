package com.tecsup.petclinic.entities;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import java.util.Date;

@Entity(name = "visits")
@Data
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Relación con Pet
    @ManyToOne
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "visit_date")
    private Date visitDate;

    @Column(name = "description", length = 255)
    private String description;

    public Visit() {
    }

    public Visit(Integer id, Pet pet, Date visitDate, String description) {
        this.id = id;
        this.pet = pet;
        this.visitDate = visitDate;
        this.description = description;
    }

    public Visit(Pet pet, Date visitDate, String description) {
        this.pet = pet;
        this.visitDate = visitDate;
        this.description = description;
    }

}
