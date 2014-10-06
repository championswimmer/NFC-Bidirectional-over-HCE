package edu.dce.nfc.libhce;

import edu.dce.nfc.libhce.emulator.CardEmulationWrapperService;

/**
 * Created by championswimmer on 5/9/14.
 */
public abstract class EmulatorService extends CardEmulationWrapperService {

    /**
     * Abstract method that performs the transaction
     *
     * When in contact with a device having the reader app, it will send a command
     * that will call this function.
     *
     * You need to override this function, parse the received command
     * and send back a suitable message/data as per the needs of your applicaiton
     *
     * @param command - Command received from the Card Reader device
     * @return - The message that needs to be sent back to the reader.
     */
    public abstract String onReceiveCommand ( String command );

}
