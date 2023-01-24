package com.motiur.consumer.service;

import java.util.Optional;

import com.motiur.consumer.model.Objection;
import com.motiur.consumer.model.User;

public interface ObjectionService {
    void save(Objection objection);

    Optional<Objection> findById(Long id);

    Iterable<Objection> findAll();

    Iterable<Objection> findByUser(User user);

    void deleteById(Long id);
}
