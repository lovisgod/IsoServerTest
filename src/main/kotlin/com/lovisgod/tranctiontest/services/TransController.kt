package com.lovisgod.tranctiontest.services

import Console
import HexUtil
import Utils
import com.bea.xml.stream.NamespaceBase
import com.lovisgod.tranctiontest.Constants
import com.lovisgod.tranctiontest.model.RequestIccData
import com.lovisgod.tranctiontest.model.TerminalInfo
import com.lovisgod.tranctiontest.model.TransactionRequest
import com.lovisgod.tranctiontest.utils.*
import com.lovisgod.tranctiontest.utils.ApplicationUtils.ISW_IP
import com.lovisgod.tranctiontest.utils.ApplicationUtils.ISW_PORT
import com.lovisgod.tranctiontest.utils.ApplicationUtils.getNextStan
import com.lovisgod.tranctiontest.utils.ApplicationUtils.printISOMessage
import com.lovisgod.tranctiontest.utils.DateUtils.monthFormatter
import com.lovisgod.tranctiontest.utils.DateUtils.timeAndDateFormatter
import com.lovisgod.tranctiontest.utils.DateUtils.timeFormatter
import jdk.jshell.execution.Util
import org.jpos.iso.*
import org.jpos.q2.iso.QMUX
import org.jpos.util.NameRegistrar
import org.slf4j.LoggerFactory

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.*
import java.util.concurrent.atomic.AtomicLong

@RestController
class TransController(var qmux: MUX) {

    private val log = LoggerFactory.getLogger(TransController::class.java)


//    @GetMapping("/test_functions")
//    fun testFunction(): Any {
//        val keyCheckValue = KCVCalculator.kcv(HexUtil.parseHex("EF5D973292W9D508A737572C3BD51F7F"))
//
//        return keyCheckValue.toString()
//    }

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

//    @GetMapping("/echo")
//    @Throws(NameRegistrar.NotFoundException::class, ISOException::class)
//    fun performTransaction(): String {
//        try {
//            val request = ISOMsg()
//            val stanValue = ISOUtil.zeropad(stan.incrementAndGet().toLong(), 6)
//            // set header
//            request.mti = "0800"
//            request[3] = "9A0000"
//            request[7] = ISODate.getDateTime(Date())
//            request[11] = stanValue
//            request[12] = ISODate.getTime(Date())
//            request[13] = ISODate.getDate(Date())
//            request[41] = "2ISW0001"
//
//            log.info("mux.isConnected {}", qmux.isConnected)
//            val response = qmux.request(request, 300000)
//            log.info("mux.isConnected {}", qmux.isConnected)
//            response.dump(System.out, "")
//
//            if (response.getValue(39) == "00") {
//                println("got here for echo message")
//                doEchoToIsw()
//            }
////            log.info("RespMsg {}", respMsg)
//            return UnpackISOMessage().parseISOMessage(response)
//        } catch (e: Exception) {
//            return e.toString() ?: ""
//        }
//    }


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

    @GetMapping("/do-isw-key-exchange")
    fun doKeyExchangeToIsw():Any {
        println("got here for key exchange")

        val request = ISOMsg()
        return try {
            val now = Date()
            val stan = getNextStan()
            request.mti = "0800"
            request[7] = timeAndDateFormatter.format(now)
            request[11] = stan
            request[12] = timeFormatter.format(now)
            request[13] = monthFormatter.format(now)
            request[70] = "101"

            printISOMessage(request)

            log.info("mux.isConnected {}", qmux.isConnected)

            val response = qmux.request(request, 300000)
            log.info("mux.isConnected {}", qmux.isConnected)
            response.dump(System.out, "")

            if (response.getValue(39)!= "00") {
                return response.getValue(39).toString()
            } else {
                val encryptedKey = response.getString(Constants.SRCI)
                Console.log("encrypted key", encryptedKey)
                val decryptedKey = TripleDES.soften(Constants.TEST_CTMK, encryptedKey)
                Console.log("decrypted key", decryptedKey)
                val kcv = KCVCalculator.kcv(HexUtil.parseHex(decryptedKey))
                Console.log("kcv calculated", kcv.toString())
                return decryptedKey
            }
        } catch (e: ISOException) {
            println(e.printStackTrace())
            return ResponseObject(
                statusCode = 500,
                message = "key exchange message not successful",
                data = ""
            )
        }

    }


    @PostMapping("/perform-isw-purchase")
    fun do200ToIsw(
        @RequestBody(required = true) transactionRequest: TransactionRequest
    ):Any {
        println("got here for 200 message to isw")

        var terminalInfo = TerminalInfo().copy(
            merchantCategoryCode = transactionRequest.merchantCategoryCode.toString(),
            terminalCode =transactionRequest.terminalCode.toString(),
            merchantName = transactionRequest.merchantName.toString(),
            merchantId = transactionRequest.merchantId.toString()
        )
        var transactionInfo = RequestIccData().apply {
            this.haspin = transactionRequest.haspin?.equals(true)
            this.TRACK_2_DATA = transactionRequest.track2Data.toString()
            this.APP_PAN_SEQUENCE_NUMBER = transactionRequest.panSequenceNumber.toString()
            this.TRANSACTION_AMOUNT = transactionRequest.amount.toString()
            this.EMV_CARD_PIN_DATA.CardPinBlock = transactionRequest.pinBlock.toString()
        }

        val request = ISOMsg()
        return try {
            val now = Date()
            val stan = getNextStan()

            val processCode = "50" + "00" + "00"
            var hasPin = transactionInfo.haspin
            val randomReference = "${Date().time}".substring(0, 12)

            val track2data =transactionInfo.TRACK_2_DATA
            println("track2 data => ${track2data}")
            // extract pan and expiry
            val strTrack2 = track2data.split("F")[0]
            println(strTrack2.split("D"))
            var panX = strTrack2.split("D")[0]
            val expiry = strTrack2.split("D")[1].substring(0, 4)
            val src = strTrack2.split("D")[1].substring(4, 7)

            // build the iso message
            request.mti = "0200"
            request[2] = panX
            request[3] = processCode
            request[4] = transactionInfo.TRANSACTION_AMOUNT
            request[7] = timeAndDateFormatter.format(now)
            request[11] = stan
            request[12] = timeFormatter.format(now)
            request[13] = monthFormatter.format(now)
            request[14] = expiry
            request[15] = monthFormatter.format(now)
            request[18] = terminalInfo.merchantCategoryCode
            request[22] = "051"
            request[23] = transactionInfo.APP_PAN_SEQUENCE_NUMBER
            request[25] = "00"
            request[26] = "06"
            request[28] = "C00000000"
            request[33] = "111111"
            request[35] =  transactionInfo.TRACK_2_DATA.split("F")[0]
            request[32] = IsoUtils.getBINFromPAN(panX)
            request[37] = randomReference
            request[40] = src
            request[41] = terminalInfo.terminalCode
            request[42] = terminalInfo.merchantId
            request[43] = terminalInfo.merchantName
            request[49] = "566"
//            request[59] = "{98:3FAB0001,100:666101,103:9010304486, 127.33: 6008, 127.41: 10.2.103.19-36065}")
            request[98] = "3FAB0001                 "
            request[100] = "666033"
            request[103] = "87001505"
            request.set("127.002", "0200:415495:1207193655:787755594")
            request.set("127.013", "      000000 566 ")
            request.set("127.020", "20230209")
            request.set("127.020", "20230209")
            request.set("127.022", Utils.getRIDAsXML("627480"))
            request.set("127.025", Utils.getIccStructureData(transactionInfo.TRANSACTION_AMOUNT))
            request.set("127.033", "6008")
            request.set("127.041", "172.26.42.206,15155")

            if (hasPin == true) {
                request.set(52, transactionInfo.EMV_CARD_PIN_DATA.CardPinBlock)
                request.set(123, "510101511344101")
                // remove unset fields
//            message.message.removeFields( 59)
            } else {
                request.set(123, "511101511344101")
                // remove unset fields
                request.unset( 52)
            }
            printISOMessage(request)

            log.info("mux.isConnected {}", qmux.isConnected)

            val response = qmux.request(request, 30000)
            log.info("mux.isConnected {}", qmux.isConnected)
            response.dump(System.out, "")

            if (response.getValue(39)!= "00") {

                return ResponseObject(
                    statusCode = 401,
                    message = "Transaction not successful",
                    data = response.getValue(39).toString()
                )
            } else {
                ResponseObject(
                    statusCode = 401,
                    message = "Transaction  successful",
                    data = response.getValue(39).toString()
                )
            }
        } catch (e: ISOException) {
            println(e.printStackTrace())
            return ResponseObject(
                statusCode = 500,
                message = "Transaction not successful",
                data = "0x0x0"
            )
        }

    }
}