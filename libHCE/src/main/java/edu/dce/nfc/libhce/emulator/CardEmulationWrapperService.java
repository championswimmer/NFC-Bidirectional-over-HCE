package edu.dce.nfc.libhce.emulator;

import android.nfc.cardemulation.HostApduService;
import android.os.Bundle;
import android.util.Log;

import edu.dce.nfc.libhce.common.Utils;

/**
 * Created by championswimmer on 5/9/14.
 */
public abstract class CardEmulationWrapperService extends HostApduService {

    public static final String TAG = "libHCE - CardEmulationWrapperService";

    @Override
    public byte[] processCommandApdu(byte[] bytes, Bundle bundle) {
        String s = new String(bytes);
        Log.d(TAG, "processCommandApdu : " + s + bytes[0] + bytes[1] + bytes[2] + bytes[3]);

        return onReceiveCommand(s).getBytes();
    }

    @Override
    public void onDeactivated(int i) {

    }

    public abstract String onReceiveCommand(String command);

}
