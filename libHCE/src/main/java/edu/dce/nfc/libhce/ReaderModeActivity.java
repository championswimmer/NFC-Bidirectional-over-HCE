package edu.dce.nfc.libhce;

import android.app.Activity;
import android.nfc.NfcAdapter;
import android.util.Log;

import edu.dce.nfc.libhce.reader.CardReader;

/**
 * Created by championswimmer on 5/9/14.
 */
public class ReaderModeActivity extends Activity {

    public static final String TAG = "libHCE-ReaderModeActivity";
    public static int READER_FLAGS =
            NfcAdapter.FLAG_READER_NFC_A | NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK;

    private CardReader mCardReader;

    public ReaderModeActivity(CardReader.ReadCallBack ac) {
        mCardReader = new CardReader(ac);
    }

    protected void enableReaderMode() {
        Log.i(TAG, "Enabling reader mode");

        NfcAdapter nfc = NfcAdapter.getDefaultAdapter(this);
        if (nfc != null) {
            nfc.enableReaderMode(this, mCardReader, READER_FLAGS, null);
        }
    }

    protected void disableReaderMode() {
        Log.i(TAG, "Disabling reader mode");
        NfcAdapter nfc = NfcAdapter.getDefaultAdapter(this);
        if (nfc != null) {
            nfc.disableReaderMode(this);
        }
    }
}
