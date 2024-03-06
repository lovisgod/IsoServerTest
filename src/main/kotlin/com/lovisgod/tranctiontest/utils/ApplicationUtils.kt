package com.lovisgod.tranctiontest.utils

import org.jpos.iso.ISOException
import org.jpos.iso.ISOMsg
import java.util.*
import java.util.prefs.Preferences

object ApplicationUtils {

    val Prefs = Preferences.userRoot().node("com.lovisgod.transactiontest")
    fun getNextStan(): String {
        var stan = Prefs.getInt("STAN", 0)

        // compute and save new stan
        val newStan = if (stan > 999999) 0 else ++stan
        Prefs.putInt("STAN", newStan)

        return String.format(Locale.getDefault(), "%06d", newStan)
    }

     fun printISOMessage(isoMsg: ISOMsg) {
        try {
            System.out.printf("MTI = %s%n", isoMsg.mti)
            for (i in 1..isoMsg.maxField) {
                if (isoMsg.hasField(i)) {
                    System.out.printf("Field (%s) = %s%n", i, isoMsg.getString(i))
                }
            }
        } catch (e: ISOException) {
            e.printStackTrace()
        }
    }

    val ISW_IP = "172.26.42.206"
    val ISW_PORT = 15155
    val NIBSS_IP = "196.6.103.10"
    val NIBSS_PORT = 55533
    const val ISW_TERMINAL_IP_NUS = "196.6.103.18"
    const val ISW_TERMINAL_PORT_NUS = 4008
}