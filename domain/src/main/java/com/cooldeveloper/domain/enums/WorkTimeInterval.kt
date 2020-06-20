package com.cooldeveloper.domain.enums

import java.util.concurrent.TimeUnit

enum class WorkTimeInterval(val time: Long) {
    MINUTES_15(TimeUnit.MINUTES.toMillis(15)),
    MINUTES_20(TimeUnit.MINUTES.toMillis(20)),
    MINUTES_25(TimeUnit.MINUTES.toMillis(25)),
    MINUTES_30(TimeUnit.MINUTES.toMillis(30)),
    MINUTES_35(TimeUnit.MINUTES.toMillis(35)),
    MINUTES_40(TimeUnit.MINUTES.toMillis(40)),
    MINUTES_45(TimeUnit.MINUTES.toMillis(45))
}