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
        if (actualCommand.contains("hello")) {
            return "e7tZiyLc85vA1MYFYyKMq79dwVHHFWep7XIgBohOxVxeShZesVJ2DFn1nQ5ch60n1xmQTk1ruIsvmZCbN9pyUfs4l4AVvmQkNsz2Sd3wwTsGo7b9MghZ4mHMp2T9rvhZfJKaEhOgZpoqXlW9XMGJucb3FwTTB0sdsv7L0fFlyoz4EKHrvRqyZreYA77iXqbjBuwfQ3hIJxp6Zjia3F30t7L8v4jJlJQ1Qv9Z17TJPA6tcgQjBkKaSDbJ1wYiEVs5DR9yKYoTkTEJFHjggj9Fvj7oPRpWiBP3PhUJ5wjsDiLcTGoRT9ebzX5fBOoIgUPVh1ant4juxCYn1mzhWsmNW7v8ni2LZBehRXad65xCGdwjKjoWywzt04Mlgz5rXIzXKq7CtFhSSijWAzz5";
        }
        return "DATA_BASED_ON_COMMAND";
    }

    @Override
    public String onCardSelect(String command) {
        Log.i(TAG, "onCardSelect called with command = " + command);
        return "PATIENT_ID_HERE";
    }



}
