package com.lovisgod.tranctiontest.utils

import com.lovisgod.tranctiontest.services.SocketFactoryUtils
import org.jpos.iso.BaseChannel
import org.jpos.iso.ISOException
import org.jpos.iso.ISOMsg
import org.jpos.iso.channel.PostChannel
import org.jpos.iso.packager.GenericPackager
import org.jpos.util.LogSource
import org.jpos.util.Logger
import org.jpos.util.SimpleLogListener
import java.io.*
import java.net.SocketTimeoutException
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException


class NetworkService(private var ip: String, private var port: Int, isSsl: Boolean) {
    var channel: BaseChannel
    private var timeout = 120000

    init {
        instance = this

        val logListener = SimpleLogListener()
        val logger = Logger()
        logger.addListener(logListener)

        channel = PostChannel()
        channel.setHost(this.ip, this.port)
        channel.timeout = timeout

        try {
            channel.packager = GenericPackager(javaClass.classLoader.getResourceAsStream("postpack.xml"))

//            if (isSsl) {
//                channel.socketFactory = SocketFactoryUtils()
//            }
        }
        catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: KeyManagementException) {
            e.printStackTrace()
        } catch (e: ISOException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        (channel as LogSource).setLogger(logger, "lovisgod-logger")
    }

    companion object {
        lateinit var instance: NetworkService
    }


    /**
     * connect socket
     * @return
     * @throws IOException
     */
    fun connect(): Int {

        println("CONNECTING... TO $ip:$port")

        try {
//			xmlc.setTimeout(20000);
            channel.connect()

            println("CONNECTED...")
        } 
        catch (e: FileNotFoundException) {
            e.printStackTrace()
            return -1
        } catch (e: IOException) {
            e.printStackTrace()
            return -1
        }

        return 0
    }


    /**
     * close socket
     */
    fun close(): Int {
        try {
            channel.disconnect()

            println("CONNECTION CLOSED...")

        }
        catch (e: IOException) {
            e.printStackTrace()
            return -1
        }

        return 0
    }


    /**
     * send data
     * @param data
     * @return
     */
    @Throws(IOException::class, ISOException::class)
    fun send(data: ISOMsg?) {
        println("SENDING DATA...")
//        println("ISO-MSG ${String(data!!.pack())}")

        channel.send(data)

        println("DATA SENT...")

//        ISOMsg().dump(System.out, "")
    }


    /**
     * receive data from server
     * @return
     * @throws IOException
     */
    fun receive(): ISOMsg {

        println("RECEIVING DATA...")

        var m = ISOMsg()
        try {
            m = channel.receive()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ISOException) {
            e.printStackTrace()
        } catch (e: EOFException) {
            e.printStackTrace()
        } catch (e: SocketTimeoutException) {
            println("\n\n Transaction timed out \n\n")
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return m
    }
}
