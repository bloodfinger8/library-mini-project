package com.group.libraryapp.usecase

import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.loanHistory.UserLoanHistory

class UserLoanHistoryCollection(
    private val map: MutableMap<Long, User>
) {
    companion object {
        fun of(userLoanHistories: List<UserLoanHistory>): UserLoanHistoryCollection =
            UserLoanHistoryCollection(userLoanHistories.associateBy ({ it.book.id!! }, { it.user }).toMutableMap())
    }
}