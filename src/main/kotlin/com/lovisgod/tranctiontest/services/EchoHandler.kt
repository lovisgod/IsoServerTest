package com.lovisgod.tranctiontest.services

import com.lovisgod.tranctiontest.utils.ApplicationUtils
import com.lovisgod.tranctiontest.utils.DateUtils
import com.lovisgod.tranctiontest.utils.UnpackISOMessage
import org.jpos.iso.ISOException
import org.jpos.iso.ISOMsg
import org.jpos.iso.ISOUtil
import org.jpos.iso.MUX
import org.jpos.q2.QBeanSupport
import org.jpos.q2.iso.QMUX
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.*

@Component
class myQbean(val qmux: MUX) : QBeanSupport() {
    private var interval: Long = 30000 // Interval between echo messages in milliseconds
    fun setInterval(interval: Long) {
        this.interval = interval
    }

    @Scheduled(fixedRate = 30000 )
     fun runTask() {
           while (true) {
               try {
                   Thread.sleep(interval)
                   sendEchoMessage(qmux = qmux)
               } catch (e: InterruptedException) {
                   Thread.currentThread().interrupt()
                   log.warn("Interrupted while sleeping", e)
               }
           }
    }

    private fun sendEchoMessage(qmux: MUX) {
        try {
            val echoMsg = createEchoMessage(qmux)
            // Send the echo message using your JPOS channel
            // Example: send(echoMsg);
            // Replace send(echoMsg) with the appropriate method to send the message
            log.info("Echo message sent: ")
        } catch (e: ISOException) {
            log.error("Error creating echo message", e)
        }
    }

    @Throws(ISOException::class)
    private fun createEchoMessage(qmux: MUX) {
        println("got here for signon")

//        val networkService = NetworkService("localhost", 9009, false )
        val request = ISOMsg()
         try {
            val now = Date()
            val stan = ApplicationUtils.getNextStan()
            // Load package from resources directory.

//            isoMsg.packager = packager
//            request.packager = MyPostPackager()
            request.mti = "0800"
//            isoMsg[3] = "9A0000"
            request[7] = DateUtils.timeAndDateFormatter.format(now)
            request[11] = stan
            request[12] = DateUtils.timeFormatter.format(now)
            request[13] = DateUtils.monthFormatter.format(now)
            request[70] = "301"

            ApplicationUtils.printISOMessage(request)

//            networkService.connect()
//            networkService.send(request)
//            val responseX = networkService.receive()
//            responseX.dump(System.out, "")
            log.info("mux.isConnected {}", qmux.isConnected)
//
            val response = qmux.request(request, 300000)
            log.info("mux.isConnected {}", qmux.isConnected)
            response.dump(System.out, "")
//            log.info("RespMsg {}", respMsg)
        } catch (e: ISOException) {
            println(e.printStackTrace())
        }
    }
}

