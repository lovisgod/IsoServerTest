package com.lovisgod.tranctiontest.utils

import Console
import HexUtil
import org.bouncycastle.crypto.engines.DESedeEngine
import org.bouncycastle.crypto.macs.CMac
import org.bouncycastle.crypto.params.KeyParameter
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.util.encoders.Hex
import java.security.GeneralSecurityException
import java.security.Security
import java.util.*
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec


object KCVCalculator {

    fun calculateKCV(keyHex: String): String {
        return try {
            // Convert the key from hex string to byte array
            // Convert the key from hex string to byte array
            val keyBytes = Hex.decode(keyHex)

            Console.log("key size is :",keyBytes.size.toString())

            // Create a CMac object using Triple DES algorithm
            val mac = CMac(DESedeEngine())

            // Calculate the KCV for each subkey separately
            val subKeySize = keyBytes.size / 3
            val kcvBytes = ByteArray(3)
            for (i in 0 until 3) {
                val subKey = keyBytes.copyOfRange(i * subKeySize, (i + 1) * subKeySize)
                val keyParam = KeyParameter(subKey)
                mac.init(keyParam)

                // Encrypt an all-zero block
                val zeroBlock = ByteArray(mac.macSize)
                mac.update(zeroBlock, 0, zeroBlock.size)
                mac.doFinal(kcvBytes, i * subKeySize)
            }

            // Take the first 3 bytes (6 hexadecimal characters) as the KCV
            val kcvFirst3Bytes = kcvBytes.copyOfRange(0, 3)

            // Convert the KCV bytes to a hexadecimal string
            Hex.toHexString(kcvFirst3Bytes).toUpperCase()
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    @Throws(GeneralSecurityException::class)
    fun kcv(key: ByteArray): String? {
        // Add Bouncy Castle Security Provider
        Security.addProvider(BouncyCastleProvider())
        // Construct a Secret Key from the given key
        val skey: SecretKey = SecretKeySpec(key, "DESede")
        // Instantiate a DESede Cipher
        val encrypter: Cipher = Cipher.getInstance("DESede/ECB/NoPadding", "BC")
        // Initialize the cipher with the key in Encrypt mode
        encrypter.init(Cipher.ENCRYPT_MODE, skey)
        // Encrypt an 8-byte null array with the cipher and return the first 6 Hex digits of the result
        return HexUtil.toHexString(encrypter.doFinal(ByteArray(8))).substring(0, 6).uppercase(Locale.getDefault())
    }
}


//CF3A9D0924D560323F69EEA21D601582
