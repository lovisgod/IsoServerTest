package com.lovisgod.tranctiontest

import java.util.*

object Constants {
    const val HOST_NAME = "localhost"
    const val HOST_PORT = 8083
    const val TEST_CTMK = "11111111111111111111111111111111"
    const val SRCI = 53

    var STAN = 0

    fun getNextStan(): String {
        var stan = STAN

        // compute and save new stan
        val newStan = if (stan > 999999) 0 else ++stan
        STAN = newStan
        return String.format(Locale.getDefault(), "%06d", newStan)
    }
}