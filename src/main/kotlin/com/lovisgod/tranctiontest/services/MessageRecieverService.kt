package com.lovisgod.tranctiontest.services

import com.lovisgod.tranctiontest.Constants
import com.lovisgod.tranctiontest.listeners.RequestListener
import jakarta.annotation.PostConstruct
import org.jpos.iso.ISOServer
import org.jpos.iso.ServerChannel
import org.jpos.iso.channel.ASCIIChannel
import org.jpos.iso.packager.GenericPackager
import org.jpos.q2.Q2
import org.jpos.util.LogSource
import org.jpos.util.Logger
import org.jpos.util.SimpleLogListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class MessageReceiverService {

    @Autowired lateinit var requestListener: RequestListener

    @PostConstruct
    fun initializeIsoServer() {
//        val logger = Logger()
//        val hostname = Constants.HOST_NAME
//        val port = Constants.HOST_PORT
//        var data = javaClass.getResourceAsStream("/fields.xml")
//        val packager = GenericPackager(data)
//        logger.addListener(SimpleLogListener(System.out))
//        val channel: ServerChannel = ASCIIChannel(hostname, port, packager)
//        (channel as LogSource).setLogger(logger, "channel")
//        val server = ISOServer(port, channel, null)
//        server.setLogger (logger, "server")
//        server.addISORequestListener(requestListener)
//        Thread (server).start ()

        // Decided to Q server for the purpose of being able to accept multiple connections
        try {
            val q2 = Q2()
            Thread(q2).start()
        } catch (e: Exception) {
            println(e)
        }
    }

}