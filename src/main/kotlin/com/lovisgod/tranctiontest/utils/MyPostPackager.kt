/*
 * jPOS Project [http://jpos.org]
 * Copyright (C) 2000-2014 Alejandro P. Revilla
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
//package com.teqpace.com.teqpace.capitalsage.packager;
package com.lovisgod.tranctiontest.utils

import org.jpos.iso.*
import org.jpos.util.Logger

/*
 * $Log$
 * Revision 1.6  2005/12/19 22:47:50  apr
 * Applied changes suggested by Mladen in http://groups-beta.google.com/group/jpos-users/browse_thread/thread/e60807e917c8c170/e29a681592d63a2b#e29a681592d63a2b and confirmed by Murtuza and Jeff
 *
 * Revision 1.5  2003/10/13 10:34:16  apr
 * Tabs expanded to 8 spaces
 *
 * Revision 1.4  2003/05/16 04:15:14  alwyns
 * Import cleanups.
 *
 * Revision 1.3  2001/01/13 19:26:09  victor
 * changed bitmap size to 16
 *
 * Revision 1.2  2000/11/02 12:09:18  apr
 * Added license to every source file
 *
 * Revision 1.1  2000/04/16 22:12:33  apr
 * New packagers location org.jpos.iso.packager
 *
 * Revision 1.6  2000/03/01 14:44:45  apr
 * Changed package name to org.jpos
 *
 * Revision 1.5  2000/02/28 11:11:37  apr
 * Added inner ISOMsg logging
 *
 * Revision 1.4  2000/02/27 14:28:05  apr
 * PostPackager compatible with new jPOS ISOMsgFieldPackager
 * Support for new Postillion version [Victor]
 *
 * Revision 1.3  1999/09/20 12:33:03  apr
 * Removed external PostPrivatePackager - now Victor uses ISOMsgFieldPackager
 * with inner PostPrivatePackager
 *
 */
/**
 * ISO 8583 v1987 Packager for Postilion
 *
 * @author Victor A. Salaman <salaman></salaman>@teknos.com>
 * @version Id: PostPackager.java,v 1.9 1999/09/17 12:08:02 salaman Exp
 * @see ISOPackager
 *
 * @see ISOBasePackager
 *
 * @see ISOComponent
 */
class MyPostPackager : ISOBasePackager() {
    protected var p127 = PostPrivatePackager()
    protected var fld = arrayOf(
        IFA_NUMERIC(4, "MESSAGE TYPE INDICATOR"),
        IFB_BITMAP(16, "BIT MAP"),
        IFA_LLNUM(19, "PAN - PRIMARY ACCOUNT NUMBER"),
        IFA_NUMERIC(6, "PROCESSING CODE"),
        IFA_NUMERIC(12, "AMOUNT, TRANSACTION"),
        IFA_NUMERIC(12, "AMOUNT, SETTLEMENT"),
        IFA_NUMERIC(12, "AMOUNT, CARDHOLDER BILLING"),
        IFA_NUMERIC(10, "TRANSMISSION DATE AND TIME"),
        IFA_NUMERIC(8, "AMOUNT, CARDHOLDER BILLING FEE"),
        IFA_NUMERIC(8, "CONVERSION RATE, SETTLEMENT"),
        IFA_NUMERIC(8, "CONVERSION RATE, CARDHOLDER BILLING"),
        IFA_NUMERIC(6, "SYSTEM TRACE AUDIT NUMBER"),
        IFA_NUMERIC(6, "TIME, LOCAL TRANSACTION"),
        IFA_NUMERIC(4, "DATE, LOCAL TRANSACTION"),
        IFA_NUMERIC(4, "DATE, EXPIRATION"),
        IFA_NUMERIC(4, "DATE, SETTLEMENT"),
        IFA_NUMERIC(4, "DATE, CONVERSION"),
        IFA_NUMERIC(4, "DATE, CAPTURE"),
        IFA_NUMERIC(4, "MERCHANTS TYPE"),
        IFA_NUMERIC(3, "ACQUIRING INSTITUTION COUNTRY CODE"),
        IFA_NUMERIC(3, "PAN EXTENDED COUNTRY CODE"),
        IFA_NUMERIC(3, "FORWARDING INSTITUTION COUNTRY CODE"),
        IFA_NUMERIC(3, "POINT OF SERVICE ENTRY MODE"),
        IFA_NUMERIC(3, "CARD SEQUENCE NUMBER"),
        IFA_NUMERIC(3, "NETWORK INTERNATIONAL IDENTIFIEER"),
        IFA_NUMERIC(2, "POINT OF SERVICE CONDITION CODE"),
        IFA_NUMERIC(2, "POINT OF SERVICE PIN CAPTURE CODE"),
        IFA_NUMERIC(1, "AUTHORIZATION IDENTIFICATION RESP LEN"),
        IFA_AMOUNT(9, "AMOUNT, TRANSACTION FEE"),
        IFA_AMOUNT(9, "AMOUNT, SETTLEMENT FEE"),
        IFA_AMOUNT(9, "AMOUNT, TRANSACTION PROCESSING FEE"),
        IFA_AMOUNT(9, "AMOUNT, SETTLEMENT PROCESSING FEE"),
        IFA_LLNUM(11, "ACQUIRING INSTITUTION IDENT CODE"),
        IFA_LLNUM(11, "FORWARDING INSTITUTION IDENT CODE"),
        IFA_LLCHAR(28, "PAN EXTENDED"),
        IFA_LLNUM(37, "TRACK 2 DATA"),
        IFA_LLLCHAR(104, "TRACK 3 DATA"),
        IF_CHAR(12, "RETRIEVAL REFERENCE NUMBER"),
        IF_CHAR(6, "AUTHORIZATION IDENTIFICATION RESPONSE"),
        IF_CHAR(2, "RESPONSE CODE"),
        IF_CHAR(3, "SERVICE RESTRICTION CODE"),
        IF_CHAR(8, "CARD ACCEPTOR TERMINAL IDENTIFICACION"),
        IF_CHAR(15, "CARD ACCEPTOR IDENTIFICATION CODE"),
        IF_CHAR(40, "CARD ACCEPTOR NAME/LOCATION"),
        IFA_LLCHAR(25, "ADITIONAL RESPONSE DATA"),
        IFA_LLCHAR(76, "TRACK 1 DATA"),
        IFA_LLLCHAR(999, "ADITIONAL DATA - ISO"),
        IFA_LLLCHAR(999, "ADITIONAL DATA - NATIONAL"),
        IFA_LLLCHAR(999, "ADITIONAL DATA - PRIVATE"),
        IF_CHAR(3, "CURRENCY CODE, TRANSACTION"),
        IF_CHAR(3, "CURRENCY CODE, SETTLEMENT"),
        IF_CHAR(3, "CURRENCY CODE, CARDHOLDER BILLING"),  //            new IFB_BINARY  (  16, "PIN DATA"   ),
        IFB_BINARY(8, "PIN DATA"),
        IFB_BINARY(48, "SECURITY RELATED CONTROL INFORMATION"),
        IFA_LLLCHAR(120, "ADDITIONAL AMOUNTS"),
        IFA_LLLCHAR(999, "RESERVED ISO"),
        IFA_LLLCHAR(999, "RESERVED ISO"),
        IFA_LLLCHAR(999, "RESERVED NATIONAL"),
        IFA_LLLCHAR(999, "RESERVED NATIONAL"),
        IFA_LLLCHAR(999, "RESERVED NATIONAL"),
        IFA_LLLCHAR(999, "RESERVED PRIVATE"),
        IFA_LLLCHAR(999, "RESERVED PRIVATE"),
        IFA_LLLCHAR(999, "RESERVED PRIVATE"),
        IFA_LLLCHAR(999, "RESERVED PRIVATE"),
        IFA_BINARY(8, "MESSAGE AUTHENTICATION CODE FIELD"),
        IFA_BINARY(8, "BITMAP, EXTENDED"),
        IFA_NUMERIC(1, "SETTLEMENT CODE"),
        IFA_NUMERIC(2, "EXTENDED PAYMENT CODE"),
        IFA_NUMERIC(3, "RECEIVING INSTITUTION COUNTRY CODE"),
        IFA_NUMERIC(3, "SETTLEMENT INSTITUTION COUNTRY CODE"),
        IFA_NUMERIC(3, "NETWORK MANAGEMENT INFORMATION CODE"),
        IFA_NUMERIC(4, "MESSAGE NUMBER"),
        IFA_NUMERIC(4, "MESSAGE NUMBER LAST"),
        IFA_NUMERIC(6, "DATE ACTION"),
        IFA_NUMERIC(10, "CREDITS NUMBER"),
        IFA_NUMERIC(10, "CREDITS REVERSAL NUMBER"),
        IFA_NUMERIC(10, "DEBITS NUMBER"),
        IFA_NUMERIC(10, "DEBITS REVERSAL NUMBER"),
        IFA_NUMERIC(10, "TRANSFER NUMBER"),
        IFA_NUMERIC(10, "TRANSFER REVERSAL NUMBER"),
        IFA_NUMERIC(10, "INQUIRIES NUMBER"),
        IFA_NUMERIC(10, "AUTHORIZATION NUMBER"),
        IFA_NUMERIC(12, "CREDITS, PROCESSING FEE AMOUNT"),
        IFA_NUMERIC(12, "CREDITS, TRANSACTION FEE AMOUNT"),
        IFA_NUMERIC(12, "DEBITS, PROCESSING FEE AMOUNT"),
        IFA_NUMERIC(12, "DEBITS, TRANSACTION FEE AMOUNT"),
        IFA_NUMERIC(16, "CREDITS, AMOUNT"),
        IFA_NUMERIC(16, "CREDITS, REVERSAL AMOUNT"),
        IFA_NUMERIC(16, "DEBITS, AMOUNT"),
        IFA_NUMERIC(16, "DEBITS, REVERSAL AMOUNT"),
        IFA_NUMERIC(42, "ORIGINAL DATA ELEMENTS"),
        IF_CHAR(1, "FILE UPDATE CODE"),
        IF_CHAR(2, "FILE SECURITY CODE"),
        IF_CHAR(5, "RESPONSE INDICATOR"),
        IF_CHAR(7, "SERVICE INDICATOR"),
        IF_CHAR(42, "REPLACEMENT AMOUNTS"),
        IFA_BINARY(8, "MESSAGE SECURITY CODE"),
        IFA_AMOUNT(17, "AMOUNT, NET SETTLEMENT"),
        IF_CHAR(25, "PAYEE"),
        IFA_LLNUM(11, "SETTLEMENT INSTITUTION IDENT CODE"),
        IFA_LLNUM(11, "RECEIVING INSTITUTION IDENT CODE"),
        IFA_LLCHAR(17, "FILE NAME"),
        IFA_LLCHAR(28, "ACCOUNT IDENTIFICATION 1"),
        IFA_LLCHAR(28, "ACCOUNT IDENTIFICATION 2"),
        IFA_LLLCHAR(100, "TRANSACTION DESCRIPTION"),
        IFA_LLLCHAR(999, "RESERVED ISO USE"),
        IFA_LLLCHAR(999, "RESERVED ISO USE"),
        IFA_LLLCHAR(999, "RESERVED ISO USE"),
        IFA_LLLCHAR(999, "RESERVED ISO USE"),
        IFA_LLLCHAR(999, "RESERVED ISO USE"),
        IFA_LLLCHAR(999, "RESERVED ISO USE"),
        IFA_LLLCHAR(999, "RESERVED ISO USE"),
        IFA_LLLCHAR(999, "RESERVED NATIONAL USE"),
        IFA_LLLCHAR(999, "RESERVED NATIONAL USE"),
        IFA_LLLCHAR(999, "RESERVED NATIONAL USE"),
        IFA_LLLCHAR(999, "RESERVED NATIONAL USE"),
        IFA_LLLCHAR(999, "RESERVED NATIONAL USE"),
        IFA_LLLCHAR(999, "RESERVED NATIONAL USE"),
        IFA_LLLCHAR(999, "RESERVED NATIONAL USE"),
        IFA_LLLCHAR(999, "RESERVED NATIONAL USE"),
        IFA_LLLCHAR(999, "RESERVED PRIVATE USE"),
        IFA_LLLCHAR(999, "RESERVED PRIVATE USE"),
        IFA_LLLCHAR(999, "RESERVED PRIVATE USE"),
        IFA_LLLCHAR(999, "RESERVED PRIVATE USE"),
        IFA_LLLCHAR(999, "RESERVED PRIVATE USE"),
        IFA_LLLCHAR(999, "RESERVED PRIVATE USE"),
        IFA_LLLCHAR(999, "RESERVED PRIVATE USE"),
        ISOMsgFieldPackager(
            IFA_LLLLLLBINARY(999999, "RESERVED PRIVATE USE"),
            p127
        ),
        IFA_LLLCHAR(999, "MAC 2")
    )

    protected class PostPrivatePackager : ISOBasePackager() {
        protected var fld127 = arrayOf(
            IF_CHAR(0, "PLACEHOLDER"),
            IFB_BITMAP(8, "BITMAP"),
            IFA_LLCHAR(32, "SWITCH KEY"),
            IF_CHAR(48, "ROUTING INFORMATION"),
            IF_CHAR(22, "POS DATA"),
            IF_CHAR(73, "SERVICE STATION DATA"),
            IFA_NUMERIC(2, "AUTHORIZATION PROFILE"),
            IFA_LLCHAR(70, "CHECK DATA"),
            IFA_LLLCHAR(999, "RETENTION DATA"),
            IFA_LLLCHAR(255, "ADDITIONAL NODE DATA"),
            IFA_NUMERIC(3, "CVV2"),
            IFA_LLCHAR(32, "ORIGINAL KEY"),
            IFA_LLCHAR(25, "TERMINAL OWNDER"),
            IF_CHAR(17, "POS GEOGRAPHIC DATA"),
            IF_CHAR(8, "SPONSOR BANK"),
            IFA_LLCHAR(29, "AVS REQUEST"),
            IF_CHAR(1, "AVS RESPONSE"),
            IFA_LLCHAR(50, "CARDHOLDER INFORMATION"),
            IFA_LLCHAR(50, "VALIDATION DATA"),
            IF_CHAR(31, "BANK DETAILS"),
            IFA_NUMERIC(8, "AUTHORIZER DATE SETTLEMENT"),
            IFA_LLCHAR(12, "RECORD IDENTIFICATION"),
            IFA_LLLLLCHAR(99999, "STRUCTURED DATA"),
            IF_CHAR(253, "PAYEE NAME AND ADDRESS"),
            IFA_LLCHAR(28, "PAYER ACCOUNT INFORMATION"),
            IFA_LLLLCHAR(9999, "ICC DATA"),
            IFA_LLCHAR(20, "ORIGINAL NODE"),
            IF_CHAR(1, "CARD VERIFICATION RESULT"),
            IF_CHAR(4, "AMERICAN EXPRESS CARD IDENTIFIER"),
            IFB_BINARY(40, "SECURED DATA"),
            IF_CHAR(1, "SECURED RESULT"),
            IFA_LLCHAR(11, "ISSUER NETWORK ID"),
            IFA_LLCHAR(33, "UCAF DATA"),
            IF_CHAR(4, "EXTENDED TRANSACTION TYPE"),
            IF_CHAR(2, "ACCOUNT TYPE QUALIFIERS"),
            IFA_LLCHAR(11, "ACQUIRER NETWORK ID"),
            IFA_LLCHAR(25, "CUSTOMER ID"),
            IF_CHAR(4, "EXTENDED RESPONSE CODE"),
            IFA_LLCHAR(99, "ADDITIONAL POS DATA CODE"),
            IF_CHAR(2, "ORIGINAL RESPONSE CODE"),
            IF_CHAR(512, "TRANSACTION REFERENCE"),
            IFA_LLCHAR(99, "ORIGINAL REMOTE ADDRESS"),
            IFA_LLCHAR(10, "TRANSACTION NUMBER"),
            IFA_LLCHAR(25, "EXTENDED CARD ACCEPTOR TERMINAL ID"),
            IFA_LLCHAR(25, "EXTENDED CARD ACCEPTOR ID CODE"),
            IF_CHAR(8, "ORIGINAL CARD ACCEPTOR TERMINAL ID"),
            IF_CHAR(15, "ORIGINAL CARD ACCEPTOR ID CODE")
        )

        init {
            setFieldPackager(fld127)
        }
    }

    init {
        setFieldPackager(fld)
    }

    override fun setLogger(logger: Logger, realm: String) {
        super.setLogger(logger, realm)
        p127.setLogger(logger, "$realm.PostPrivatePackager")
    }
}
