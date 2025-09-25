package com.klef.dev.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.dev.model.TV;
import com.klef.dev.repository.TVRepository;

import java.util.List;

@Service
public class TVServiceImpl implements TVService {

    @Autowired
    private TVRepository tvRepository;

    @Override
    public TV addTV(TV tv) {
        return tvRepository.save(tv);
    }

    @Override
    public List<TV> getAllTVs() {
        return tvRepository.findAll();
    }

    @Override
    public TV getTVById(int id) {
        return tvRepository.findById(id).orElse(null);
    }

    @Override
    public TV updateTV(TV tv) {
        return tvRepository.save(tv);
    }

    @Override
    public void deleteTVById(int id) {
        tvRepository.deleteById(id);
    }

    @Override
    public List<TV> getTVsByBrand(String brand) {
        return tvRepository.findByBrand(brand);
    }

    @Override
    public List<TV> getTVsBySmart(String smart) {
        return tvRepository.findBySmart(smart);
    }
}
