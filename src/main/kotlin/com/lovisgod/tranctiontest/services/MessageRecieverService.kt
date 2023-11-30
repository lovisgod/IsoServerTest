package com.lovisgod.tranctiontest.services

import com.lovisgod.tranctiontest.listeners.RequestListener
import jakarta.annotation.PostConstruct
import org.jpos.iso.ISOServer
import org.jpos.iso.ServerChannel
import org.jpos.iso.channel.ASCIIChannel
import org.jpos.iso.channel.XMLChannel
import org.jpos.iso.packager.ISO87APackager
import org.jpos.iso.packager.XMLPackager
import org.jpos.util.LogSource
import org.jpos.util.Logger
import org.jpos.util.SimpleLogListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class MessageRecieverService {

    @Autowired lateinit var requestListener: RequestListener

    @PostConstruct
    fun initializeIsoServer() {
        val logger = Logger()
        val packager = ISO87APackager()
        logger.addListener(SimpleLogListener(System.out))
        val channel: ServerChannel = XMLChannel(XMLPackager())
        (channel as LogSource).setLogger(logger, "channel")
        val server = ISOServer(8083, channel, null)
        server.setLogger (logger, "server")
        server.addISORequestListener(requestListener)
        Thread (server).start ()
    }

}