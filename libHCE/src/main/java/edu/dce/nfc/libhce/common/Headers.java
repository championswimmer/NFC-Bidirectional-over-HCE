package edu.dce.nfc.libhce.common;

import android.util.Log;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Created by championswimmer on 5/9/14.
 */
public class Headers {
    public static final String TAG = "libHCEReader";

    /**
     * AID for our loyalty card service.
     */
    public static final String CARD_AID = "F222222244";

    /**
     * ISO-DEP command HEADER for selecting an AID.
     * Format: [Class | Instruction | Parameter 1 | Parameter 2]
     */
    public static final String HEADER_SELECT = "00A40400";

    /**
     * ISO-DEP command HEADER for selecting an AID.
     * Format: [Class | Instruction | Parameter 1 | Parameter 2]
     */
    public static final String HEADER_SENDCOMMAND = "00CC0000";

    /**
     * ISO-DEP command HEADER for selecting an AID.
     * Format: [Class | Instruction | Parameter 1 | Parameter 2]
     */
    public static final String HEADER_GETDATA = "00CA0000";

    /**
     * "OK" status word sent in response to SELECT AID command (0x9000)
     */
    public static final byte[] RESPONSE_SELECT_OK = {(byte) 0x90, (byte) 0x00};

    /**
     * "FINAL" status word sent in response to GETDATA command (0x9006)
     */
    public static final byte[] RESPONSE_GETDATA_FINAL = {(byte) 0x90, (byte) 0x06};
    /**
     * "INTERMEDIATE" status word sent in response to GETDATA command (0x9006)
     */
    public static final byte[] RESPONSE_GETDATA_INTERMEDIATE = {(byte) 0x90, (byte) 0x05};

    /**
     * "OK" status word sent in response to SENDCOMMAND command (0x9010)
     */
    public static final byte[] RESPONSE_SENDCOMMAND_OK = {(byte) 0x90, (byte) 0x10};
    /**
     * "OK" status word sent in response to SENDCOMMAND command (0x9010)
     */
    public static final byte[] RESPONSE_SENDCOMMAND_PROCESSED = {(byte) 0x90, (byte) 0x11};

    /**
     * Build APDU for SELECT AID command. This command indicates which service a reader is
     * interested in communicating with. See ISO 7816-4.
     *
     * @param aid Application ID (AID) to select
     * @return APDU for SELECT AID command
     */
    public static byte[] BuildSelectApdu(String aid) {
        // Format: [CLASS | INSTRUCTION | PARAMETER 1 | PARAMETER 2 | LENGTH | DATA]
        return Utils.HexStringToByteArray(Headers.HEADER_SELECT + String.format("%02X", aid.length() / 2) + aid);
    }

    /**
     * Build APDU for sending command. See ISO 7816-4.
     *
     * @return APDU for SELECT AID command
     */
    public static byte[] BuildSendDataApdu(int n, String command) {
        // Format: [CLASS | INSTRUCTION | PARAMETER 1 | PARAMETER 2 | LENGTH | DATA]
        String hexLength = Integer.toHexString(n);
        switch (hexLength.length()) {
            case 1: hexLength = "000" + hexLength; break;
            case 2: hexLength = "00" + hexLength; break;
            case 3: hexLength = "0" + hexLength; break;
            default: break;
        }
        Log.d(TAG, "hexlength = " + hexLength);
        byte[] intermediateReturn = Utils.HexStringToByteArray(Headers.HEADER_SENDCOMMAND + hexLength);
        byte[] commandByteArray = command.getBytes();
        return Utils.ConcatArrays(intermediateReturn, command.getBytes());
    }

    /**
     * Build APDU for getting response. See ISO 7816-4.
     *
     * @return APDU for SELECT AID command
     */
    public static byte[] BuildGetDataApdu() {
        // Format: [CLASS | INSTRUCTION | PARAMETER 1 | PARAMETER 2 | LENGTH | DATA]
        return Utils.HexStringToByteArray(Headers.HEADER_GETDATA);
    }


}
