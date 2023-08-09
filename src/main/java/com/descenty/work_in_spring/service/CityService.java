// package com.descenty.work_in_spring.service;

// import java.util.List;

// import org.springframework.stereotype.Service;

// import com.descenty.work_in_spring.entity.collected.City;
// import com.descenty.work_in_spring.repository.CityRepository;

// import lombok.AllArgsConstructor;

// @Service
// @AllArgsConstructor
// public class CityService {
//     private final CityRepository cityRepository;

//     public List<City> getAll(String name) {
//         return cityRepository.findAll();
//     }

//     public City getById(Long id) {
//         return cityRepository.findById(id).orElse(null);
//     }

//     public City save(City city) {
//         return cityRepository.save(city);
//     }

//     public void deleteById(Long id) {
//         cityRepository.deleteById(id);
//     }
// }
