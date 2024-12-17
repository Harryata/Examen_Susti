package com.tecsup.petclinic.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.tecsup.petclinic.entities.Visit;
import com.tecsup.petclinic.exception.VisitNotFoundException;
import com.tecsup.petclinic.repositories.VisitRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class VisitServiceImpl implements VisitService {

    private final VisitRepository visitRepository;

    public VisitServiceImpl(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    @Override
    public Visit create(Visit visit) {
        return visitRepository.save(visit);
    }

    @Override
    public Visit update(Visit visit) {
        return visitRepository.save(visit);
    }

    @Override
    public void delete(Integer id) throws VisitNotFoundException {
        Visit visit = findById(id);
        visitRepository.delete(visit);
    }

    @Override
    public Visit findById(Integer id) throws VisitNotFoundException {
        Optional<Visit> visit = visitRepository.findById(id);
        if (!visit.isPresent()) {
            throw new VisitNotFoundException("Visit not found with id: " + id);
        }
        return visit.get();
    }

    @Override
    public List<Visit> findByPetId(Integer petId) {
        List<Visit> visits = visitRepository.findByPetId(petId);
        visits.forEach(v -> log.info("Found visit: {}", v));
        return visits;
    }

    @Override
    public List<Visit> findByVisitDate(Date visitDate) {
        List<Visit> visits = visitRepository.findByVisitDate(visitDate);
        visits.forEach(v -> log.info("Found visit: {}", v));
        return visits;
    }

    @Override
    public List<Visit> findByDescriptionContaining(String keyword) {
        List<Visit> visits = visitRepository.findByDescriptionContaining(keyword);
        visits.forEach(v -> log.info("Found visit: {}", v));
        return visits;
    }

    @Override
    public List<Visit> findAll() {
        List<Visit> visits = visitRepository.findAll();
        visits.forEach(v -> log.info("Found visit: {}", v));
        return visits;
    }
}
