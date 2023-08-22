package com.descenty.work_in_spring.api;

import com.descenty.work_in_spring.area.dto.AreaCreate;
import com.descenty.work_in_spring.area.repository.AreaRepository;
import com.descenty.work_in_spring.area.service.AreaService;
import com.descenty.work_in_spring.company.dto.CompanyCreate;
import com.descenty.work_in_spring.company.dto.CompanyDTO;
import com.descenty.work_in_spring.area.dto.AreaDTO;
import com.descenty.work_in_spring.company.service.CompanyService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
class CompanyTests {
    @Autowired
    private AreaService areaService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private AreaRepository areaRepository;
    private Long areaId;

    private Long companyId;


    @BeforeEach
    void init() {
        areaRepository.deleteAll();
        areaId = areaService.create(AreaCreate.builder().id(1L).name("test").build()).map(AreaDTO::getId).orElseThrow();
        companyId = companyService.create(areaId, CompanyCreate.builder().id(1L).name("test").build()).map(CompanyDTO::getId).orElseThrow();
    }

    @Test
    @Transactional
    void update() {
        CompanyCreate companyCreate = CompanyCreate.builder().name("test2").build();
        CompanyDTO companyDTO = companyService.update(companyId, companyCreate).orElseThrow();
        assertThat(companyDTO.getId()).isEqualTo(companyId);
        assertThat(companyDTO.getAreaId()).isEqualTo(areaId);
        assertThat(companyDTO.getName()).isEqualTo(companyCreate.getName());
    }

    @Test
    @Transactional
    void delete() {
        System.out.println(companyService.delete(companyId));
    }
}
