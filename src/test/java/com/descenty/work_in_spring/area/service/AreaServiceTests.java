package com.descenty.work_in_spring.area.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
@AutoConfigureTestDatabase
public class AreaServiceTests {
    @Autowired
    private AreaService areaService;

    @Test
    void getAllMain() throws IOException {
        areaService.getAllMain(1L);
    }
}
