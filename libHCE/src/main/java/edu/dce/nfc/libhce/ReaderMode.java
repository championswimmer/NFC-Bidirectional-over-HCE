package edu.dce.nfc.libhce;

import android.app.Activity;
import android.nfc.NfcAdapter;
import android.util.Log;

import edu.dce.nfc.libhce.reader.CardReader;

/**
 * Created by championswimmer on 5/9/14.
 */
public class ReaderMode {

    public static final String TAG = "libHCE-ReaderMode";
    public static int READER_FLAGS =
            NfcAdapter.FLAG_READER_NFC_A | NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK;

    private CardReader mCardReader;

    public ReaderMode(CardReader.ReadCallBack ac) {
        mCardReader = new CardReader(ac);
    }

    public void enableReaderMode(Activity act) {
        Log.i(TAG, "Enabling reader mode");

        NfcAdapter nfc = NfcAdapter.getDefaultAdapter(act);
        if (nfc != null) {
            nfc.enableReaderMode(act, mCardReader, READER_FLAGS, null);
        }
    }

    private void disableReaderMode(Activity act) {
        Log.i(TAG, "Disabling reader mode");
        NfcAdapter nfc = NfcAdapter.getDefaultAdapter(act);
        if (nfc != null) {
            nfc.disableReaderMode(act);
        }
    }
}
