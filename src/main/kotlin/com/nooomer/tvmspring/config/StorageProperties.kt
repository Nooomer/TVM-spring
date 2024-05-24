package com.nooomer.tvmspring.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component


@ConfigurationProperties("storage")
@Component
class StorageProperties {
    var location: String = "/srv/root/sounds"
}