package edu.dce.nfc.cardemulator;

import android.content.Intent;
import android.util.Log;

import edu.dce.nfc.libhce.EmulatorService;

/**
 * Created by championswimmer on 6/10/14.
 */
public class MyService extends EmulatorService {
    public static final String TAG = "libHCEEmulator";

    @Override
    public String onReceiveCommand(String command) {
        Log.i(TAG, "onReceiveCommand called with command = " + command);
        return "OOOH LALALA";
    }



}
