package com.group.libraryapp.domain.user.loanHistory

import com.group.libraryapp.domain.user.loanHistory.type.UserLoanStatus

interface UserLoanHistoryRepository {
    fun saveAll(userLoanHistories: List<UserLoanHistory>)
    fun findByBookNameAndStatus(bookName: String, status: UserLoanStatus): UserLoanHistory?
    fun countByStatus(status: UserLoanStatus): Long
    fun existsByBookIdAndStatus(bookId: Long, status: UserLoanStatus): Boolean
}
