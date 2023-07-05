package com.nooomer.tvmspring.exceptions

import java.io.Serializable

class ErrorDto(
    var timestamp: String,
    var status: Int,
    var error: String,
    var message: String,
    var path: String
) : Serializable