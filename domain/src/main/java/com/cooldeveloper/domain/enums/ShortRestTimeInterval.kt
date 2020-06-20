package com.cooldeveloper.domain.enums

import java.util.concurrent.TimeUnit

enum class ShortRestTimeInterval(val time: Long) {
    MINUTES_5(TimeUnit.MINUTES.toMillis(5)),
    MINUTES_10(TimeUnit.MINUTES.toMillis(10)),
    MINUTES_15(TimeUnit.MINUTES.toMillis(15)),
}