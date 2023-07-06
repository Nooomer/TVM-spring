package com.nooomer.tvmspring.services.helpers.interfaces

import org.jetbrains.annotations.NotNull

interface SessionHelper {
    fun setSessionData(@NotNull data: Any)
    fun getSessionData(): Any
    fun deleteSession()
}