package com.group.libraryapp.controller.book

import com.group.libraryapp.dto.book.request.BookRequest
import com.group.libraryapp.dto.response.BaseResponse
import com.group.libraryapp.dto.response.SuccessRes
import com.group.libraryapp.security.AuthenticationDTO
import com.group.libraryapp.usecase.book.RegisterBookUseCase
import com.group.libraryapp.usecase.book.dto.response.BookStatRes
import com.group.libraryapp.util.UserRole
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@Tag(name = "도서 관련 API")
@RestController
class RegisterBookController(
    val registerBookUseCase: RegisterBookUseCase
) {
    @Operation(summary = "도서 등록", security = [SecurityRequirement(name = "Bearer Token")])
    @ApiResponses(
        ApiResponse(responseCode = "40000", description = "")
    )
    @Secured(UserRole.ROLE_USER)
    @PostMapping("/book")
    fun saveBook(
        @Valid @RequestBody
        req: BookRequest,
        @Parameter(hidden = true) @AuthenticationPrincipal
        authenticationDTO: AuthenticationDTO
    ): ResponseEntity<BaseResponse> {
        val res = registerBookUseCase.register(req.toCmd(authenticationDTO))
        return ResponseEntity.ok(SuccessRes(res))
    }

    @GetMapping("/book/loan")
    fun loanBookCount(): Int {
        return registerBookUseCase.countLoanedBook()
    }

    @GetMapping("/book/stat")
    fun bookStat(): List<BookStatRes> {
        return registerBookUseCase.getStat()
    }
}
