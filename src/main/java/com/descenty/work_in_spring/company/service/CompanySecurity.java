package com.descenty.work_in_spring.company.service;

import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.descenty.work_in_spring.company.repository.CompanyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompanySecurity {
    private final CompanyRepository companyRepository;

    // TODO check admin access

    public boolean isCompanyEmployer(Long companyId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName() != "anonymousUser" && (authentication.getAuthorities().stream()
                .anyMatch((authority) -> authority.getAuthority().equals("admin"))
                || companyRepository.existsByIdAndEmployersIdsContaining(companyId,
                        UUID.fromString(authentication.getName())));
    }

    public boolean isCreator(Long companyId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName() != "anonymousUser" && (authentication.getAuthorities().stream()
                .anyMatch((authority) -> authority.getAuthority().equals("admin"))
                || companyRepository.existsByIdAndCreatorId(companyId, UUID.fromString(authentication.getName())));
    }
}