package com.lovisgod.tranctiontest.listeners

import org.jpos.iso.ISOMsg
import org.jpos.iso.ISORequestListener
import org.jpos.iso.ISOSource
import org.springframework.stereotype.Component

@Component
class RequestListener: ISORequestListener {
    override fun process(p0: ISOSource?, p1: ISOMsg?): Boolean {
        processTransactions(p0, p1)
        return true
    }


    fun processTransactions(isoSource: ISOSource?, isoMessage: ISOMsg?) {
        when(isoMessage?.mti) {
         "0800" -> {
            processNetworkManagementTransactions(isoSource, isoMessage)
         }
         else -> {
             isoMessage?.setResponseMTI()
             isoMessage?.set(39, "92")
             isoSource?.send(isoMessage)
         }
        }
    }

    fun processNetworkManagementTransactions(isoSource: ISOSource?, isoMessage: ISOMsg?) {
        when (isoMessage?.getValue(3).toString()) {
            "9D0000" -> {
                isoMessage?.setResponseMTI()
                isoMessage?.set(39, "00")
                isoSource?.send(
                        isoMessage
                )
            }
            else -> {
                isoMessage?.setResponseMTI()
                isoMessage?.set(39, "92")
                isoSource?.send(isoMessage)
            }
        }
    }

}