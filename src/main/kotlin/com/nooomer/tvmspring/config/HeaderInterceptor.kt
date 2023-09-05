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
            true // Продолжить выполнение запроса
        } else {
            //response.status = HttpServletResponse.SC_FORBIDDEN // Отклонить запрос с ошибкой 403 Forbidden
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied")
            false
        }
    }

}