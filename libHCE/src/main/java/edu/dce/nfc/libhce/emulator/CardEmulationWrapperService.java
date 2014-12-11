package edu.dce.nfc.libhce.emulator;

import android.nfc.cardemulation.HostApduService;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import edu.dce.nfc.libhce.common.Headers;
import edu.dce.nfc.libhce.common.Utils;

/**
 * Created by championswimmer on 5/9/14.
 */
public abstract class CardEmulationWrapperService extends HostApduService {

    public static final String TAG = "libHCEEmulator";
    int sendCounter = 0;

    @Override
    public byte[] processCommandApdu(byte[] bytes, Bundle bundle) {
        String s = Utils.ByteArrayToHexString(bytes);
        Log.d(TAG, "processCommandApdu : " + s);
        if (s.contains("00A404")) {
            // This is select command
            return Utils.ConcatArrays(onCardSelect(s).getBytes(), Headers.RESPONSE_SELECT_OK);
        }

        String[] results = Utils.StringSplit255(onReceiveCommand(s));
        String result = sendResultPart(results);

        return Utils.ConcatArrays(onReceiveCommand(s).getBytes(), Headers.RESPONSE_SELECT_OK);
    }

    @Override
    public void onDeactivated(int i) {

    }

    public String sendResultPart (String[] results) {
        if (sendCounter < results.length) {
            sendCounter += 1;
            return results[sendCounter-1];
        } else {
            sendCounter = 0;
            return "END_OF_DATA";
        }

    }

    public abstract String onReceiveCommand(String command);
    public abstract String onCardSelect(String command);

}
