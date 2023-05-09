package com.group.libraryapp.usecase.group

import com.group.libraryapp.domain.company.Company
import com.group.libraryapp.domain.company.CompanyRepository
import com.group.libraryapp.dto.book.command.RegisterCompanyCommand
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RegisterCompanyUseCase (
    val companyRepository: CompanyRepository,
){
    @Transactional
    fun register(command: RegisterCompanyCommand) {
        companyRepository.save(Company.create(command.name, command.domain))
    }
}