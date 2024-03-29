package com.group.libraryapp.usecase.book

import com.group.libraryapp.SliceDto
import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.exception.userNotFoundFail
import com.group.libraryapp.usecase.book.assembler.BookDtoAssembler
import com.group.libraryapp.usecase.book.dto.response.BookDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ListBookUseCase(
    private val bookRepository: BookRepository,
    private val userRepository: UserRepository,
    private val bookDtoAssembler: BookDtoAssembler
) {
    @Transactional(readOnly = true)
    fun inventory(id: Long, companyId: Long, page: Int, pageSize: Int): SliceDto<BookDto> {
        val books = bookRepository.findAllByCompanyId(companyId, page, pageSize)
        val user = userRepository.findById(id) ?: userNotFoundFail(id)
        return SliceDto(books.hasNext, bookDtoAssembler.toDtoList(books.elements, user))
    }
}
