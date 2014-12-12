package edu.dce.nfc.cardemulator;

import android.content.Intent;
import android.util.Log;

import edu.dce.nfc.libhce.EmulatorService;
import edu.dce.nfc.libhce.common.Utils;

/**
 * Created by championswimmer on 6/10/14.
 */
public class MyService extends EmulatorService {
    public static final String TAG = "libHCEEmulator";

    @Override
    public String onReceiveCommand(String command) {
        Log.i(TAG, "onReceiveCommand called with command = " + command);
        String actualCommand = new String(Utils.HexStringToByteArray(command));
        Log.i(TAG, "actual command = " + actualCommand);
        if (actualCommand.contains("somecommand")) {
            return "someresult";
        }
        return "DATA_BASED_ON_COMMAND";
    }

    @Override
    public String onCardSelect(String command) {
        Log.i(TAG, "onCardSelect called with command = " + command);
        return "PATIENT_ID_HERE";
    }



}
