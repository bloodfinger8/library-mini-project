package com.group.libraryapp.service.user

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.user.Email
import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanHistory.UserLoanHistory
import com.group.libraryapp.domain.user.loanHistory.UserLoanHistoryRepository
import com.group.libraryapp.domain.user.loanHistory.type.UserLoanStatus
import com.group.libraryapp.dto.user.request.UserCreateRequest
import com.group.libraryapp.dto.user.request.UserUpdateRequest
import com.group.libraryapp.usecase.user.UserService
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserServiceTest @Autowired constructor(
    private val userService: UserService,
    private val userRepository: UserRepository,
    private val userLoanHistoryRepository: UserLoanHistoryRepository,
    private val bookRepository: BookRepository,
) {
    companion object {
        const val EMAIL = "didwodn82@naver.com"
        const val PASSWORD = "123456"
        const val NAME = "재우"
    }

    @Test
    fun `유저 생성`() {
        val userCreateRequest = UserCreateRequest(EMAIL, PASSWORD,NAME)

        val user = userService.signUp(userCreateRequest)

        Assertions.assertThat(user.name).isEqualTo(NAME)
        Assertions.assertThat(user.email.email).isEqualTo(EMAIL)
    }

    @Test
    @DisplayName("유저 검색")
    fun getUser() {
        val users = listOf(
            User(Email(EMAIL), PASSWORD, "재우"),
            User(Email("didwodn8822@gmail.com"), PASSWORD , "재우2")
        )
        userRepository.saveAll(users)

        val searchUsers = userService.searchUsers()

        Assertions.assertThat(searchUsers).hasSize(2)
        Assertions.assertThat(searchUsers).extracting("name").containsExactlyInAnyOrder("재우","재우2")
    }

    @Test
    @DisplayName("유저 업데이트")
    fun updateUser() {
        val saveUser = userRepository.save(User(Email(EMAIL), PASSWORD,NAME))
        val userUpdateRequest = UserUpdateRequest(saveUser.id!!, "재우2")

        userService.updateUserName(userUpdateRequest)

        val user = userRepository.findAll()
        Assertions.assertThat(user[0].name).isEqualTo("재우2")
    }


    @Test
    @DisplayName("유저 삭제")
    fun deleteUser() {
        userRepository.save(User(Email(EMAIL), PASSWORD, NAME))

        userService.deleteUser(NAME)

        Assertions.assertThat(userRepository.findByName(NAME)).isNull()
    }


    @Test
    fun `유저 대출 히스토리 조회`() {
        val user = userRepository.save(User(Email(EMAIL), PASSWORD, NAME))
        val book1 = Book.create("book-1")
        val book2 = Book.create("book-2")
        val book3 = Book.create("book-3")

        bookRepository.saveAll(listOf(book1, book2, book3))
        userLoanHistoryRepository.saveAll(listOf(
            UserLoanHistory.create(user, book1, UserLoanStatus.LOANED),
            UserLoanHistory.create(user, book2, UserLoanStatus.LOANED),
            UserLoanHistory.create(user, book3, UserLoanStatus.RETURNED)
        ))

        val results = userService.searchUserLoanHistories()

        Assertions.assertThat(results).hasSize(1)
        Assertions.assertThat(results[0].email.email).isEqualTo(EMAIL)
        Assertions.assertThat(results[0].books).hasSize(3)
        Assertions.assertThat(results[0].books).extracting("name")
            .containsExactlyInAnyOrder("book-1","book-2","book-3")
    }

    @AfterEach
    fun clean() {
        userRepository.deleteAll()
    }
}