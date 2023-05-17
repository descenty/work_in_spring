package com.descenty.work_in_spring.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.descenty.work_in_spring.entity.City;
import com.descenty.work_in_spring.service.CityService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/city")
@AllArgsConstructor
public class CityController {
    private final CityService cityService;

    @GetMapping("/")
    public List<City> getAll(@RequestParam(required = false) String name) {
        return cityService.getAll(name);
    }

    @GetMapping("/{id}")
    public City getById(@PathVariable Long id) {
        return cityService.getById(id);
    }

    @PostMapping("/save")
    public City save(@RequestBody City city) {
        return cityService.save(city);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        cityService.deleteById(id);
    }
}
