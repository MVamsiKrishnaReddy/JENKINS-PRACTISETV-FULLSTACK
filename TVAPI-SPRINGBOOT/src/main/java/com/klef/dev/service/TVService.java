package com.klef.dev.service;

import com.klef.dev.model.TV;
import java.util.List;

public interface TVService {
    TV addTV(TV tv);
    List<TV> getAllTVs();
    TV getTVById(int id);
    TV updateTV(TV tv);
    void deleteTVById(int id);
    List<TV> getTVsByBrand(String brand);
    List<TV> getTVsBySmart(String smart);
}
