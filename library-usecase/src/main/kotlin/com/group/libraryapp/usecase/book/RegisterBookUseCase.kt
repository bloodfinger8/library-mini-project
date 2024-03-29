package com.group.libraryapp.usecase.book

import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.book.event.BookRegisteredNotifier
import com.group.libraryapp.domain.company.CompanyRepository
import com.group.libraryapp.domain.elasticsearch.book.BookSearchEngine
import com.group.libraryapp.domain.user.loanHistory.UserLoanHistoryRepository
import com.group.libraryapp.domain.user.loanHistory.type.UserLoanStatus
import com.group.libraryapp.exception.companyNotFoundFail
import com.group.libraryapp.gateway.jpa.book.BookQuerydslRepositoryImpl
import com.group.libraryapp.usecase.book.dto.command.RegisterBookCommand
import com.group.libraryapp.usecase.book.dto.response.BookStatRes
import com.group.libraryapp.usecase.book.dto.response.RegisterBookDto
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RegisterBookUseCase(
    private val bookRepository: BookRepository,
    private val bookQuerydslRepository: BookQuerydslRepositoryImpl,
    private val userLoanHistoryRepository: UserLoanHistoryRepository,
    private val companyRepository: CompanyRepository,
    private val eventPublisher: ApplicationEventPublisher,
    private val searchEngine: BookSearchEngine
) {
    @Transactional
    fun register(req: RegisterBookCommand): RegisterBookDto {
        val company = companyRepository.findById(req.companyId) ?: companyNotFoundFail(req.companyId)
        val book = bookRepository.save(req.createBook(company))
        searchEngine.save(book)
        eventPublisher.publishEvent(BookRegisteredNotifier(book))
        return RegisterBookDto.of(book)
    }

    @Transactional(readOnly = true)
    fun countLoanedBook(): Int {
        return userLoanHistoryRepository.countByStatus(UserLoanStatus.LOANED).toInt()
    }

    @Transactional(readOnly = true)
    fun getStat(): List<BookStatRes> {
        val stat = bookQuerydslRepository.getStat()
        return stat.map { BookStatRes(it.type, it.count) }
    }
}
