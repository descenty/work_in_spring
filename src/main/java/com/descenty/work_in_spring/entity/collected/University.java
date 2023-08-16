//package com.descenty.work_in_spring.entity.collected;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//@Entity
//@Table(name = "university")
//@Getter
//@Setter
//public class University {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private String name;
//    @ManyToOne(fetch = FetchType.LAZY)
//    private City city;
//    private String country;
//    private String faculty;
//    private String speciality;
//    private String degree;
//    private String yearOfGraduation;
//}
