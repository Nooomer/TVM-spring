package com.nooomer.tvmspring.services.helpers

import com.nooomer.tvmspring.exceptions.NotAuthorizeException
import com.nooomer.tvmspring.services.helpers.interfaces.SessionHelper
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.util.*

@Component
class SecurityContextSessionHelper(private val request: HttpServletRequest) : SessionHelper {
    override fun setSessionData(data: Any) {
        request.session.setAttribute("SPRING_SECURITY_CONTEXT", data)
    }

    override fun getSessionData(): SecurityContext {
        val context = request.session.getAttribute("SPRING_SECURITY_CONTEXT")
        return if (Objects.isNull(context)) {
            throw NotAuthorizeException("You not logged to account")
        } else {
            context as SecurityContext
        }
    }

    override fun deleteSession() {
        SecurityContextHolder.getContext().authentication = null
        request.session.invalidate()
    }
}