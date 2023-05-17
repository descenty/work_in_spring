package com.descenty.work_in_spring.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.descenty.work_in_spring.dto.VacancyDto;
import com.descenty.work_in_spring.entity.Vacancy;
import com.descenty.work_in_spring.service.VacancyService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/vacancy")
@AllArgsConstructor
public class VacancyController {
    private final VacancyService vacancyService;

    @RequestMapping("")
    public List<Vacancy> getAll() {
        return vacancyService.getAll();
    }

    @RequestMapping("/{id}")
    public Vacancy getById(@RequestParam Long id) {
        return vacancyService.getById(id);
    }

    @PostMapping("")
    public void save(@RequestBody VacancyDto vacancyDto) {
        vacancyService.save(vacancyDto);
    }

}
