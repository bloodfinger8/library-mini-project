package com.group.libraryapp.domain.user.loanHistory

import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.loanHistory.type.UserLoanStatus
import javax.persistence.*

@Entity
class UserLoanHistory (
    @ManyToOne
    val user: User,
    val bookName: String,
    @Enumerated(EnumType.STRING)
    var status: UserLoanStatus,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
) {
    companion object {
        fun create(user: User,
                   bookName: String = "book",
                   status: UserLoanStatus = UserLoanStatus.LOANED,
                   id: Long? = null) : UserLoanHistory{
            return UserLoanHistory(user, bookName, status, id)
        }
    }


    fun doReturn () {
        this.status = UserLoanStatus.RETURNED
    }
}