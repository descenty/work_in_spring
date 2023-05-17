package com.descenty.work_in_spring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.descenty.work_in_spring.dto.VacancyDto;
import com.descenty.work_in_spring.entity.City;
import com.descenty.work_in_spring.entity.Vacancy;
import com.descenty.work_in_spring.repository.CityRepository;
import com.descenty.work_in_spring.repository.VacancyRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VacancyService {
    private final VacancyRepository vacancyRepository;
    private final CityRepository cityRepository;

    public List<Vacancy> getAll() {
        return vacancyRepository.findAll();
    }

    public Vacancy getById(Long id) {
        return vacancyRepository.findById(id).orElseThrow();
    }

    public void save(VacancyDto vacancyDto) {

        vacancyRepository.save(mapToEntity(vacancyDto));
    }

    public void deleteById(Long id) {
        vacancyRepository.deleteById(id);
    }

    public Vacancy mapToEntity(VacancyDto vacancyDto) {
        Vacancy vacancy = new Vacancy();
        vacancy.setId(vacancyDto.getId());
        vacancy.setTitle(vacancyDto.getTitle());
        vacancy.setDescription(vacancyDto.getDescription());
        vacancy.setMinSalary(vacancyDto.getMinSalary());
        vacancy.setMaxSalary(vacancyDto.getMaxSalary());
        Optional<City> city = cityRepository.findById(vacancyDto.getCityId());
        if (city.isPresent()) {
            vacancy.setCity(city.get());
        }
        return vacancy;
    }

}
