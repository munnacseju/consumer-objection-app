package com.motiur.consumer.service;

import java.util.Optional;

import com.motiur.consumer.model.Objection;
import com.motiur.consumer.model.User;
import com.motiur.consumer.repository.ObjectionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ObjectionServiceImpl implements ObjectionService {
    @Autowired
    private ObjectionRepository objectionRepository;

    @Override
    public void save(Objection objection) {
        objectionRepository.save(objection);
    }

    @Override
    public Optional<Objection> findById(Long id) {
        return objectionRepository.findById(id);
    }

    @Override
    public Iterable<Objection> findAll() {
        return objectionRepository.findAll();
    }

    @Override
    public Iterable<Objection> findByUser(User user) {
        return objectionRepository.findByUser(user);
    }

    @Override
    public void deleteById(Long id) {
        objectionRepository.deleteById(id);
    }
}
