package com.lovisgod.tranctiontest.utils

import org.jpos.iso.ISOException
import org.jpos.iso.ISOMsg
import org.jpos.iso.packager.GenericPackager

class UnpackISOMessage {
    @Throws(Exception::class)
    private fun parseISOMessage(): ISOMsg {
        val message = "02003220000000808000000010000000001500120604120000000112340001840"
        System.out.printf("Message = %s%n", message)
        return try {
            // Load package from resources directory.
            val `is` = javaClass.getResourceAsStream("/fields.xml")
            val packager = GenericPackager(`is`)
            val isoMsg = ISOMsg()
            isoMsg.packager = packager
            isoMsg.unpack(message.toByteArray())
            isoMsg
        } catch (e: ISOException) {
            throw Exception(e)
        }
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

    fun parseISOMessage(isoMsg: ISOMsg): String {
        var data = StringBuilder()
        try {
//            System.out.printf("MTI = %s%n", isoMsg.mti)
            data.append("MTI = ${isoMsg.mti}")
            for (i in 1..isoMsg.maxField) {
                if (isoMsg.hasField(i)) {
                    data.append("Field (${i}) = ${isoMsg.getString(i)}").append("\n")
                }
            }
        } catch (e: ISOException) {
            e.printStackTrace()
        }
        return data.toString()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val iso = UnpackISOMessage()
            try {
                val isoMsg = iso.parseISOMessage()
                iso.printISOMessage(isoMsg)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}