package edu.dce.nfc.libhce.emulator;

import android.nfc.cardemulation.HostApduService;
import android.os.Bundle;
import android.util.Log;

import edu.dce.nfc.libhce.common.Headers;
import edu.dce.nfc.libhce.common.Utils;

/**
 * Created by championswimmer on 5/9/14.
 */
public abstract class CardEmulationWrapperService extends HostApduService {

    public static final String TAG = "libHCEEmulator";

    @Override
    public byte[] processCommandApdu(byte[] bytes, Bundle bundle) {
        String s = Utils.ByteArrayToHexString(bytes);
        Log.d(TAG, "processCommandApdu : " + s);

        return Utils.ConcatArrays(onReceiveCommand(s).getBytes(), Headers.RESPONSE_SELECT_OK);
    }

    @Override
    public void onDeactivated(int i) {

    }

    public abstract String onReceiveCommand(String command);

}
