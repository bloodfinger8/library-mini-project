package com.group.libraryapp.controller.book

import com.group.libraryapp.dto.book.request.BookLoanRequest
import com.group.libraryapp.dto.book.request.BookRequest
import com.group.libraryapp.dto.book.request.BookReturnRequest
import com.group.libraryapp.dto.book.response.BookStatResponse
import com.group.libraryapp.usecase.book.BookService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Api(tags = ["도서 관련 API"])
@RestController
class BookController constructor(
    val bookService: BookService
){

    @ApiOperation(value = "도서 등록")
    @PostMapping("/book")
    fun saveBook(@Valid @RequestBody request: BookRequest) {
        bookService.saveBook(request)
    }

    @ApiOperation(value = "도서 대출")
    @PostMapping("/book/loan")
    fun loanBook(@RequestBody request: BookLoanRequest) {
        bookService.loanBook(request)
    }

    @PutMapping("/book/return")
    fun returnBook(@RequestBody request: BookReturnRequest) {
        bookService.returnBook(request)
    }

    @GetMapping("/book/loan")
    fun loanBookCount() : Int {
        return bookService.countLoanedBook()
    }

    @GetMapping("/book/stat")
    fun bookStat(): List<BookStatResponse> {
        return bookService.getStat()
    }
}