package com.group.libraryapp.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.group.libraryapp.dto.response.FailureRes
import com.group.libraryapp.exception.UNAUTHORIZED
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class ExceptionHandlerFilter: OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try{
            filterChain.doFilter(request, response);
        } catch (e: JwtException) {
            when(e) {
                is ExpiredJwtException -> response("JWT Token has expired", response)
                else -> response("JWT token valid error" ,response)
            }
        }
    }

    private fun response(message: String, response: HttpServletResponse) {
        response.status = HttpStatus.INTERNAL_SERVER_ERROR.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        try {
            response.writer.write(ObjectMapper().writeValueAsString(FailureRes(UNAUTHORIZED,message)));
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}