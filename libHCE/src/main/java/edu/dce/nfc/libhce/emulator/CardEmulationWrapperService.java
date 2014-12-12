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
    String[] results;
    String result;

    @Override
    public byte[] processCommandApdu(byte[] bytes, Bundle bundle) {
        String s = Utils.ByteArrayToHexString(bytes);
        Log.d(TAG, "processCommandApdu : " + s);
        String commandClass = s.substring(0, Math.min(s.length(), 8));

        if (commandClass.equalsIgnoreCase(Headers.HEADER_SELECT)) {
            // This is select command
            return Utils.ConcatArrays(onCardSelect(s).getBytes(), Headers.RESPONSE_SELECT_OK);
        } else if (commandClass.equalsIgnoreCase(Headers.HEADER_SENDCOMMAND)) {
            if (s.contains("END_OF_COMMAND")) {
                results = Utils.StringSplit255(onReceiveCommand(s));
                return Utils.ConcatArrays(onCardSelect(s).getBytes(), Headers.RESPONSE_SENDCOMMAND_PROCESSED);
            }
            return Utils.ConcatArrays(onCardSelect(s).getBytes(), Headers.RESPONSE_SENDCOMMAND_OK);
        } else if (commandClass.equalsIgnoreCase(Headers.HEADER_GETDATA)) {

            result = sendResultPart(results);

        }




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
