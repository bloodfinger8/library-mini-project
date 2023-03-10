package com.group.libraryapp.controller.user

import com.group.libraryapp.dto.response.BaseResponse
import com.group.libraryapp.dto.response.SuccessRes
import com.group.libraryapp.dto.user.request.UserCreateRequest
import com.group.libraryapp.dto.user.request.UserUpdateRequest
import com.group.libraryapp.dto.user.response.UserLoanHistoryResponse
import com.group.libraryapp.dto.user.response.UserResponse
import com.group.libraryapp.usecase.user.UserService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Api(tags = ["회원 관련 API"])
@RestController
class UserController constructor(
    val userService: UserService
){

    @ApiOperation(value = "사용자 회원가입")
    @PostMapping("/user/sign-up")
    fun signUpUser(@Valid @RequestBody request: UserCreateRequest): ResponseEntity<BaseResponse> {
        userService.signUp(request)
        return ResponseEntity.ok(SuccessRes<String>());
    }

    @ApiOperation(value = "사용자 로그인")
    @PostMapping("/user/sign-in")
    fun signInUser() {
    }

    @ApiOperation(value = "사용자 검색")
    @GetMapping("/user")
    fun getUsers(): List<UserResponse> {
        return userService.searchUsers()
    }

    @ApiOperation(value = "사용자 수정")
    @PutMapping("/user")
    fun updateUserName(@RequestBody request: UserUpdateRequest) {
        userService.updateUserName(request)
    }

    @ApiOperation(value = "사용자 삭제")
    @DeleteMapping("/user")
    fun deleteUser(@RequestParam name: String) {
        userService.deleteUser(name)

    }

    @ApiOperation(value = "사용자 도서 렌탈 히스토리 조회")
    @GetMapping("/user/loan")
    fun getLoanHistories() : List<UserLoanHistoryResponse> {
        return userService.searchUserLoanHistories()
    }
}