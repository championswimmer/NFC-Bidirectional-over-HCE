package edu.dce.nfc.libhce.emulator;

import android.nfc.cardemulation.HostApduService;
import android.os.Bundle;

/**
 * Created by championswimmer on 5/9/14.
 */
public class CardEmulatorService extends HostApduService {

    @Override
    public byte[] processCommandApdu(byte[] bytes, Bundle bundle) {
        return new byte[0];
    }

    @Override
    public void onDeactivated(int i) {

    }
}
