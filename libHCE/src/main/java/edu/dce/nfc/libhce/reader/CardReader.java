package edu.dce.nfc.libhce.reader;

import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.util.Log;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Arrays;

import edu.dce.nfc.libhce.common.Headers;
import edu.dce.nfc.libhce.common.Utils;

/**
 * Created by championswimmer on 5/9/14.
 */
public class CardReader implements NfcAdapter.ReaderCallback {
    private static final String TAG = "LoyaltyCardReader";

    int mMaxTransceiveLength;
    boolean mExtendedApduSupported;
    int mTimeout;

    TransceiveResult mResult;


    String gotData = "", finalGotData = "";

    long timeTaken = 0;

    // Weak reference to prevent retain loop. mAccountCallback is responsible for exiting
// foreground mode before it becomes invalid (e.g. during onPause() or onStop()).
    private WeakReference<ReadCallBack> mAccountCallback;

    public CardReader(ReadCallBack readCallBack) {
        mAccountCallback = new WeakReference<ReadCallBack>(readCallBack);
    }

    /**
     * Callback when a new tag is discovered by the system.
     * <p/>
     * <p>Communication with the card should take place here.
     *
     * @param tag Discovered tag
     */
    @Override
    public void onTagDiscovered(Tag tag) {
        Log.i(TAG, "New tag discovered");
        // Android's Host-based Card Emulation (HCE) feature implements the ISO-DEP (ISO 14443-4)
        // protocol.
        //
        // In order to communicate with a device using HCE, the discovered tag should be processed
        // using the IsoDep class.
        IsoDep isoDep = IsoDep.get(tag);
        if (isoDep != null) {
            try {
                // Connect to the remote NFC device
                isoDep.connect();
                mExtendedApduSupported = isoDep.isExtendedLengthApduSupported();
                mMaxTransceiveLength = isoDep.getMaxTransceiveLength();
                mTimeout = isoDep.getTimeout();
                isoDep.setTimeout(3600);
                mTimeout = isoDep.getTimeout();

                // Build SELECT AID command for our loyalty card service.
                // This command tells the remote device which service we wish to communicate with.
                byte[] selCommand = Headers.BuildSelectApdu(Headers.SAMPLE_LOYALTY_CARD_AID);

                // Send command to remote device and fetch result
                mResult = TransceiveResult.get(isoDep, selCommand);

                // If AID is successfully selected, 0x9000 is returned as the status word (last 2
                // bytes of the mResult) by convention. Everything before the status word is
                // optional payload, which is used here to hold the account number.
                int resultLength = mResult.getLength();
                if (Arrays.equals(Headers.RESPONSE_SELECT_OK, mResult.getStatusword())) {
                    // The remote NFC device will immediately respond with its stored account number
                    String accountNumber = new String(mResult.getPayload(), "UTF-8");
                    Log.i(TAG, "Received: " + accountNumber);
                    // Inform CardReaderFragment of received account number
                    timeTaken = System.currentTimeMillis();
                    while (!(gotData.contains("END"))) {
                        byte[] getCommand = Headers.BuildGetDataApdu();
                        Log.i(TAG, "Sending: " + Utils.ByteArrayToHexString(getCommand));
                        mResult = TransceiveResult.get(isoDep, getCommand);
                        resultLength = mResult.getLength();
                        Log.i(TAG, "Received rlen : " + resultLength);
                        byte[] statusWordNew = mResult.getStatusword();
                        if (Arrays.equals(Headers.RESPONSE_SELECT_OK, statusWordNew)) {
                            gotData = new String(mResult.getPayload(), "UTF-8");
                            Log.i(TAG, "Received: " + gotData);
                            finalGotData = finalGotData + gotData;
                            Log.i(TAG, "Data transferred : " + finalGotData.length());
                            Log.i(TAG, "Time taken: " + (System.currentTimeMillis() - timeTaken));

                        }
                    }
                    mAccountCallback.get().onDataReceived(gotData);

                }
            } catch (IOException e) {
                Log.e(TAG, "Error communicating with card: " + e.toString());
            }
        }
    }

    public interface ReadCallBack {
        public void onDataReceived(String account);
    }


}
