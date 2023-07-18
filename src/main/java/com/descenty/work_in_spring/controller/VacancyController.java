package com.descenty.work_in_spring.controller;

import com.descenty.work_in_spring.entity.Vacancy;
import com.descenty.work_in_spring.service.VacancyService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/vacancy")
@RestController
@AllArgsConstructor
public class VacancyController {
    private final VacancyService vacancyService;

    @GetMapping("/{cityId}")
    public List<Vacancy> findAll(@PathVariable Long cityId) {
        return vacancyService.findAll(cityId);
    }
}
