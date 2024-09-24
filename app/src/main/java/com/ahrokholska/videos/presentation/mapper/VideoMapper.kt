package com.ahrokholska.videos.presentation.mapper

import com.ahrokholska.videos.domain.model.Video
import com.ahrokholska.videos.presentation.model.VideoUI

fun Video.toUi() = VideoUI(
    id = id,
    url = url,
    imageURL = imageURL,
    durationFormatted = with(durationSec) {
        val sec = durationSec % 60
        val min = durationSec  / 60
        val hour = min / 60
        if (hour == 0) {
            numberOrZeroAndNumber(min) + ":" + numberOrZeroAndNumber(sec)
        } else {
            numberOrZeroAndNumber(hour) + ":" + numberOrZeroAndNumber(min) + ":" +
                    numberOrZeroAndNumber(sec)
        }
    }
)

private fun numberOrZeroAndNumber(number: Int) = if (number < 10) "0$number" else "$number"