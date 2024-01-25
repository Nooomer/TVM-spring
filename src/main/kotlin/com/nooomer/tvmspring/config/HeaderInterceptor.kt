package com.nooomer.tvmspring.config

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor

@Component
class HeaderInterceptor : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val customHeaderValue = request.getHeader("AppType")
        return if (customHeaderValue != null && ((customHeaderValue == "Mobile") || (customHeaderValue) == "Test")) {
            true
        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied")
            false
        }
    }

}