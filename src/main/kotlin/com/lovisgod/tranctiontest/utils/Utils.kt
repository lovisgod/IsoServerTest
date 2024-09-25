import java.io.StringWriter


object Utils {
    fun ByteArray.toHexString(): String {
        return joinToString("") { "%02x".format(it).uppercase() }
    }

//    fun String.toByteArray(): ByteArray {
//        return this.toByteArray(StandardCharsets.UTF_8)
//    }


     fun getRIDAsXML(rid: String): String? {
        val xmlString = StringWriter()
        xmlString.append("""212ORIGINAL_RID290<?xml version="1.0" encoding="UTF-8" standalone="yes"?><ORIGINAL_RID>""")
            .append(rid)
            .append("</ORIGINAL_RID>")
        return xmlString.toString()
    }


    fun getIccStructureData(amount: String): String? {
        var iccData = """<?xml version="1.0" encoding="UTF-8" standalone="yes"?><IccData><IccRequest><AmountAuthorized>${amount}</AmountAuthorized><AmountOther>000000000000</AmountOther><ApplicationInterchangeProfile>3900</ApplicationInterchangeProfile><ApplicationTransactionCounter>006B</ApplicationTransactionCounter><Cryptogram>20A677E6F45DA1D3</Cryptogram><CryptogramInformationData>40</CryptogramInformationData><CvmResults>440302</CvmResults><IssuerApplicationData>0110676003020000A06F00000000000000FF</IssuerApplicationData><TerminalCapabilities>E0F8C8</TerminalCapabilities><TerminalCountryCode>566</TerminalCountryCode><TerminalVerificationResult>0000008000</TerminalVerificationResult><TransactionCurrencyCode>566</TransactionCurrencyCode><TransactionDate>240408</TransactionDate><TransactionType>00</TransactionType><UnpredictableNumber>E994AB50</UnpredictableNumber></IccRequest></IccData>
        """.trimIndent()
        return iccData
    }
}