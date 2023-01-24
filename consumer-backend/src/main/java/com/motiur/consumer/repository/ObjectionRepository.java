package com.motiur.consumer.repository;

import org.springframework.data.repository.CrudRepository;

import com.motiur.consumer.model.Objection;
import com.motiur.consumer.model.User;

public interface ObjectionRepository extends CrudRepository<Objection, Long> {
    Iterable<Objection> findByUser(User user);
}
