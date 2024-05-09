package com.lovisgod.tranctiontest.services

import com.bea.xml.stream.NamespaceBase
import com.lovisgod.tranctiontest.utils.ApplicationUtils.ISW_IP
import com.lovisgod.tranctiontest.utils.ApplicationUtils.ISW_PORT
import com.lovisgod.tranctiontest.utils.ApplicationUtils.getNextStan
import com.lovisgod.tranctiontest.utils.ApplicationUtils.printISOMessage
import com.lovisgod.tranctiontest.utils.DateUtils.monthFormatter
import com.lovisgod.tranctiontest.utils.DateUtils.timeAndDateFormatter
import com.lovisgod.tranctiontest.utils.DateUtils.timeFormatter
import com.lovisgod.tranctiontest.utils.MyPostPackager
import com.lovisgod.tranctiontest.utils.NetworkService
import com.lovisgod.tranctiontest.utils.UnpackISOMessage
import org.jpos.iso.*
import org.jpos.q2.iso.QMUX
import org.jpos.util.NameRegistrar
import org.slf4j.LoggerFactory

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import java.util.concurrent.atomic.AtomicLong

@RestController
class TransController(var qmux: MUX) {

    private val log = LoggerFactory.getLogger(TransController::class.java)
    var stan = AtomicLong()

    @GetMapping("/do-isw-signon")
    fun doSignOnToIsw():Any {
        println("got here for signon")

//        val networkService = NetworkService("localhost", 9009, false )
        val request = ISOMsg()
        return try {
            val now = Date()
            val stan = getNextStan()
            // Load package from resources directory.

//            isoMsg.packager = packager
//            request.packager = MyPostPackager()
            request.mti = "0800"
//            isoMsg[3] = "9A0000"
            request[7] = timeAndDateFormatter.format(now)
            request[11] = stan
            request[12] = timeFormatter.format(now)
            request[13] = monthFormatter.format(now)
            request[70] = "001"

            printISOMessage(request)

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
//            if (response.getValue(39) == "00") {
//                println("got here for echo message")
////                doEchoToIsw()
//                myQbean(qmux)
//            }
            return UnpackISOMessage().parseISOMessage(response)
        } catch (e: ISOException) {
            println(e.printStackTrace())
            return ResponseObject(
                statusCode = 500,
                message = "Sign on message not successful",
                data = ""
            )
        }

    }

    @GetMapping("/echo")
    @Throws(NameRegistrar.NotFoundException::class, ISOException::class)
    fun performTransaction(): String {
        try {
            val request = ISOMsg()
            val stanValue = ISOUtil.zeropad(stan.incrementAndGet().toLong(), 6)
            // set header
            request.mti = "0800"
            request[3] = "9A0000"
            request[7] = ISODate.getDateTime(Date())
            request[11] = stanValue
            request[12] = ISODate.getTime(Date())
            request[13] = ISODate.getDate(Date())
            request[41] = "2ISW0001"

            log.info("mux.isConnected {}", qmux.isConnected)
            val response = qmux.request(request, 300000)
            log.info("mux.isConnected {}", qmux.isConnected)
            response.dump(System.out, "")

            if (response.getValue(39) == "00") {
                println("got here for echo message")
                doEchoToIsw()
            }
//            log.info("RespMsg {}", respMsg)
            return UnpackISOMessage().parseISOMessage(response)
        } catch (e: Exception) {
            return e.toString() ?: ""
        }
    }


    fun doEchoToIsw():Any {
        println("got here for signon")

//        val networkService = NetworkService("localhost", 9009, false )
        val request = ISOMsg()
        return try {
            val now = Date()
            val stan = getNextStan()
            // Load package from resources directory.

//            isoMsg.packager = packager
//            request.packager = MyPostPackager()
            request.mti = "0800"
//            isoMsg[3] = "9A0000"
            request[7] = timeAndDateFormatter.format(now)
            request[11] = stan
            request[12] = timeFormatter.format(now)
            request[13] = monthFormatter.format(now)
            request[70] = "301"

            printISOMessage(request)

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
            return UnpackISOMessage().parseISOMessage(response)
        } catch (e: ISOException) {
            println(e.printStackTrace())
            return ResponseObject(
                statusCode = 500,
                message = "Sign on message not successful",
                data = ""
            )
        }

    }
}