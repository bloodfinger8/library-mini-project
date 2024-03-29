package com.group.libraryapp.usecase.user

import com.group.libraryapp.domain.user.Email
import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.exception.loginFail
import com.group.libraryapp.security.JWTAccessToken
import com.group.libraryapp.security.JWTTokenProvider
import com.group.libraryapp.usecase.user.dto.command.SignInCommand
import com.group.libraryapp.usecase.user.dto.response.UserSignInDto
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SignInUseCase(
    private val authenticationManager: AuthenticationManager,
    private val tokenProvider: JWTTokenProvider,
    private val userRepository: UserRepository
) {
    @Transactional
    fun signIn(command: SignInCommand): UserSignInDto {
        val user = userRepository.findByEmail(Email(command.email)) ?: loginFail()
        emailPasswordAuthenticate(command)
        return UserSignInDto.of(user, accessToken(user))
    }

    private fun emailPasswordAuthenticate(command: SignInCommand) {
        SecurityContextHolder.getContext().authentication =
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(command.email, command.password))
    }

    private fun accessToken(user: User) =
        tokenProvider.signAcToken(
            JWTAccessToken.of(
                user.id!!,
                user.email.name(),
                user.name,
                companyId = user.company!!.id!!
            )
        )
}
