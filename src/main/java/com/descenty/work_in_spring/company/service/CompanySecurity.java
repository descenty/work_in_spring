package com.descenty.work_in_spring.company.service;

import java.util.UUID;

import org.springdoc.core.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.descenty.work_in_spring.company.repository.CompanyRepository;
import com.descenty.work_in_spring.user.dto.UserDTO;
import com.descenty.work_in_spring.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompanySecurity {
    private final CompanyRepository companyRepository;

    public boolean isCompanyEmployer(Long companyId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return companyRepository.existsByIdAndEmployersIdsContaining(companyId,
                UUID.fromString(authentication.getName()));
    }

    public boolean isCreator(Long companyId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return companyRepository.existsByIdAndCreatorId(companyId, UUID.fromString(authentication.getName()));
    }
}