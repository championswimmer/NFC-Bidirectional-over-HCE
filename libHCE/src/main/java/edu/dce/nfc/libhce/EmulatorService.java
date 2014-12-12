package edu.dce.nfc.libhce;

import android.content.Intent;
import android.util.Log;

import edu.dce.nfc.libhce.emulator.CardEmulationWrapperService;

/**
 * Created by championswimmer on 5/9/14.
 */
public abstract class EmulatorService extends CardEmulationWrapperService {
    public static final String TAG = "libHCEEmulator";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Service started");
        return START_STICKY;
    }

    /**
     * Abstract method that performs the transaction
     * <p/>
     * When in contact with a device having the reader app, it will send a command
     * that will call this function.
     * This is reached only after a successful first iteration of
     * onCardSelect is done
     * <p/>
     * You need to override this function, parse the received command
     * and send back a suitable message/data as per the needs of your applicaiton
     *
     * @param command - Command received from the Card Reader device
     * @return - The message that needs to be sent back to the reader.
     */
    public abstract String onReceiveCommand(String command);

    /**
     * Abstract method that runs on card selection
     * <p/>
     * When first contact with a device having the reader app,
     * the select command is sent.
     * This is best used to exchange id's of of reader and card
     * <p/>
     * You need to override this function, parse the received command
     * and send back a suitable message/data as per the needs of your applicaiton
     *
     * @param command - Command received from the Card Reader device
     * @return - The message that needs to be sent back to the reader.
     */
    public abstract String onCardSelect(String command);

}
