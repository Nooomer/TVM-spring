package com.nooomer.tvmspring.dto

import java.io.Serializable

data class MessagesDto(
    val id: Int? = null,
    val messageText: String? = null,
    val sendTime: String? = null,
    val fromId: Int? = null,
    val toId: Int? = null,
    val chats: ChatsDto? = null,
    val users: UsersDto? = null,
    val usersByToId: UsersDto? = null
) : Serializable