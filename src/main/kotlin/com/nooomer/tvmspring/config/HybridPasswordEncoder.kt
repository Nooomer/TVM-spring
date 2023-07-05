package com.nooomer.tvmspring.config

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import java.nio.charset.StandardCharsets
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class HybridPasswordEncoder : PasswordEncoder {
    private val bcryptEncoder: BCryptPasswordEncoder = BCryptPasswordEncoder()
    override fun encode(rawPassword: CharSequence): String {
        return bcryptEncoder.encode(rawPassword)
    }

    override fun matches(rawPassword: CharSequence, encodedPassword: String): Boolean {
        return bcryptEncoder.matches(rawPassword, encodedPassword)
    }

    companion object {
        private val hashPattern = Pattern.compile("^(\\w+)\\$(\\d+)\\$([-\\w]+)\\$([-\\w+=]+)/?$")
    }
}