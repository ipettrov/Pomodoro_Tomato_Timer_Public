package com.cooldeveloper.domain.enums

import java.util.concurrent.TimeUnit

enum class LongRestTimeInterval(val time: Long) {
    MINUTES_10(TimeUnit.MINUTES.toMillis(10)),
    MINUTES_15(TimeUnit.MINUTES.toMillis(15)),
    MINUTES_20(TimeUnit.MINUTES.toMillis(20)),
}