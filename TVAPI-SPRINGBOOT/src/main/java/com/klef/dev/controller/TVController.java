package com.klef.dev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import com.klef.dev.model.TV;
import com.klef.dev.service.TVService;

import java.util.List;

@RestController
@RequestMapping("/tvapi")
@CrossOrigin(origins = "*")
public class TVController {

    @Autowired
    private TVService tvService;

    @GetMapping("/")
    public String home() {
        return "TV Management API is running!";
    }

    @PostMapping("/add")
    public ResponseEntity<TV> addTV(@RequestBody TV tv) {
        TV savedTV = tvService.addTV(tv);
        return new ResponseEntity<>(savedTV, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<TV>> getAllTVs() {
        List<TV> tvs = tvService.getAllTVs();
        return new ResponseEntity<>(tvs, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getTVById(@PathVariable int id) {
        TV tv = tvService.getTVById(id);
        if(tv != null) {
            return new ResponseEntity<>(tv, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("TV with ID " + id + " not found.", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateTV(@RequestBody TV tv) {
        TV existing = tvService.getTVById(tv.getId());
        if(existing != null) {
            TV updatedTV = tvService.updateTV(tv);
            return new ResponseEntity<>(updatedTV, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Cannot update. TV with ID " + tv.getId() + " not found.", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTV(@PathVariable int id) {
        TV existing = tvService.getTVById(id);
        if(existing != null) {
            tvService.deleteTVById(id);
            return new ResponseEntity<>("TV with ID " + id + " deleted successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Cannot delete. TV with ID " + id + " not found.", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/search/brand")
    public ResponseEntity<List<TV>> getTVsByBrand(@RequestParam String brand) {
        List<TV> tvs = tvService.getTVsByBrand(brand);
        return new ResponseEntity<>(tvs, HttpStatus.OK);
    }

    @GetMapping("/search/smart")
    public ResponseEntity<List<TV>> getTVsBySmart(@RequestParam String smart) {
        List<TV> tvs = tvService.getTVsBySmart(smart);
        return new ResponseEntity<>(tvs, HttpStatus.OK);
    }
}
