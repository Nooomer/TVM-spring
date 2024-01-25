package com.nooomer.tvmspring.dto

import java.io.Serializable

data class NewMessageDto(
    var messageText: String,
) : Serializable