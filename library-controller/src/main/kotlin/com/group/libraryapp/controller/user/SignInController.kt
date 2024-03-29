package com.group.libraryapp.controller.user

import com.group.libraryapp.dto.response.SuccessRes
import com.group.libraryapp.dto.user.request.UserSignInRequest
import com.group.libraryapp.usecase.user.SignInUseCase
import com.group.libraryapp.usecase.user.dto.command.SignInCommand
import com.group.libraryapp.usecase.user.dto.response.UserSignInDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@Tag(name = "회원 관련 API")
@RestController
class SignInController(
    private val signInUseCase: SignInUseCase
) {
    @Operation(summary = "사용자 로그인")
    @ApiResponses(
        ApiResponse(responseCode = "40200", description = "id/pw not matched"),
        ApiResponse(responseCode = "50000", description = "server error")
    )
    @PostMapping("/user/sign-in")
    fun signIn(@Valid @RequestBody request: UserSignInRequest): ResponseEntity<SuccessRes<UserSignInDto>> {
        val res = signInUseCase.signIn(SignInCommand(request.email, request.password))
        return ResponseEntity.ok(SuccessRes(res))
    }
}
