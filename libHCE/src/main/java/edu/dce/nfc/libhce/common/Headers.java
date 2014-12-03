package edu.dce.nfc.libhce.common;

/**
 * Created by championswimmer on 5/9/14.
 */
public class Headers {
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
     * "FINAL" status word sent in response to GETDATA command (0x9006)
     */
    public static final byte[] RESPONSE_GETDATA_INTERMEDIATE = {(byte) 0x90, (byte) 0x05};

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
     * Build APDU for GET_DATA command. See ISO 7816-4.
     *
     * @return APDU for SELECT AID command
     */
    public static byte[] BuildGetDataApdu() {
        // Format: [CLASS | INSTRUCTION | PARAMETER 1 | PARAMETER 2 | LENGTH | DATA]
        return Utils.HexStringToByteArray(Headers.HEADER_GETDATA + "0FFF");
    }


}
