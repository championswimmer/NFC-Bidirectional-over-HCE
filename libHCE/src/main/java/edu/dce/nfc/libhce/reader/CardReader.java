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
    private static final String TAG = "libHCEReader";

    TransceiveResult mResult;
    IsoDep isoDep;


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
        Log.i(TAG, "onTagDiscovered");
        // Android's Host-based Card Emulation (HCE) feature implements the ISO-DEP (ISO 14443-4)
        // protocol.
        //
        // In order to communicate with a device using HCE, the discovered tag should be processed
        // using the IsoDep class.
        isoDep = IsoDep.get(tag);
        if (isoDep != null) {

            try {
                // Connect to the remote NFC device
                isoDep.connect();
                Log.i(TAG, "isoDep connected");


                // Select the card
                byte[] selCommand = Headers.BuildSelectApdu(Headers.CARD_AID);
                mResult = TransceiveResult.get(isoDep, selCommand);

                Log.d(TAG, "result = "
                        + Arrays.toString(mResult.getPayload())
                        + "\n statusword =  " + new String(mResult.getStatusword())
                        + "\n payload = " + new String(mResult.getPayload())
                        + "\n length = " + mResult.getLength()
                );

                // If AID is successfully selected, 0x9000 is returned as the status word (last 2
                // bytes of the mResult) by convention. Everything before the status word is
                // optional payload
                if (Arrays.equals(Headers.RESPONSE_SELECT_OK, mResult.getStatusword())) {
                    Log.d(TAG, "response = OK");
                    mAccountCallback.get().onHceStarted(isoDep);
                }

            } catch (IOException e) {
                Log.e(TAG, "Error communicating with card: " + e.toString());
            }
        }
    }



    public interface ReadCallBack {
        public String transactNfc (IsoDep isoDep, String sendCommand) throws IOException;
        public void onHceStarted(IsoDep isoDep);
    }

}
