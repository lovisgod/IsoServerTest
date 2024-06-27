package com.lovisgod.tranctiontest.services

import com.lovisgod.tranctiontest.utils.ApplicationUtils
import org.jpos.iso.ISOClientSocketFactory
import java.net.Socket
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class SocketFactoryUtils : ISOClientSocketFactory {
        override fun createSocket(host: String?, port: Int): Socket {
            val protocols = listOf("TLSv1.2")
            return SSLContext.getInstance(protocols[0]).apply {
                val trustCertificates = arrayOf<TrustManager>(object : X509TrustManager {
                    override fun checkClientTrusted(p0: Array<out X509Certificate>?, p1: String?) = Unit
                    override fun checkServerTrusted(p0: Array<out X509Certificate>?, p1: String?) = Unit
                    override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
                })

                init(null, trustCertificates, SecureRandom())
            }.socketFactory.createSocket(ApplicationUtils.NIBSS_IP, ApplicationUtils.NIBSS_PORT)
        }

    }