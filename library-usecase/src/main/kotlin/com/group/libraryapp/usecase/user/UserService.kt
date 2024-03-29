package com.group.libraryapp.usecase.user

import com.group.libraryapp.gateway.jpa.user.UserQuerydslRepositoryImpl
import com.group.libraryapp.usecase.user.dto.response.UserLoanHistoryDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    val userQuerydslRepository: UserQuerydslRepositoryImpl
) {
    @Transactional(readOnly = true)
    fun searchUserLoanHistories(): List<UserLoanHistoryDto> {
        return userQuerydslRepository.getHistories().map { UserLoanHistoryDto.from(it) }
    }
}
