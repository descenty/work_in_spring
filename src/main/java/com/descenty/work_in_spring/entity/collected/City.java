//package com.descenty.work_in_spring.entity.collected;
//
//import java.util.List;
//
//import com.descenty.work_in_spring.entity.Vacancy;
//import com.descenty.work_in_spring.entity.user.resume.Resume;
//import jakarta.persistence.CascadeType;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.OneToMany;
//import jakarta.persistence.Table;
//import lombok.Getter;
//import lombok.Setter;
//
//@Entity
//@Table(name = "city")
//@Getter
//@Setter
//public class City {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private String name;
//    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Vacancy> vacancies;
//    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Resume> resumes;
//}
