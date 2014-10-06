package edu.dce.nfc.libhce.reader;

import android.nfc.tech.IsoDep;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by championswimmer on 7/9/14.
 */
public class TransceiveResult {
    private int rlen;
    private byte[] result;
    private byte[] statusword;
    private byte[] payload;

    public TransceiveResult(IsoDep isoDep, byte[] command) throws IOException {
        result = isoDep.transceive(command);
        rlen = result.length;
        statusword = new byte[]{result[rlen - 2], result[rlen - 1]};
        payload = Arrays.copyOf(result, rlen - 2);
    }

    public static TransceiveResult get(IsoDep isoDep, byte[] command) throws IOException {
        return new TransceiveResult(isoDep, command);
    }

    public int getLength() {
        return rlen;
    }

    public byte[] getStatusword() {
        return statusword;
    }

    public byte[] getPayload() {
        return payload;
    }

}
