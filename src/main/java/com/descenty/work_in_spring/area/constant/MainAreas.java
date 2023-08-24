package com.descenty.work_in_spring.area.constant;

import jakarta.annotation.Resource;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;

@Data
public class MainAreas {
    @Value("./static/area/main_areas.json")
    public static Resource resource;
    public Map<String, String[]> areas;
}
