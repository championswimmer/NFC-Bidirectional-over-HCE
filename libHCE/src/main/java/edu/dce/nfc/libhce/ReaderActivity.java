package edu.dce.nfc.libhce;

import android.app.Activity;
import android.nfc.NfcAdapter;
import android.nfc.tech.IsoDep;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.Arrays;

import edu.dce.nfc.libhce.common.Headers;
import edu.dce.nfc.libhce.common.Utils;
import edu.dce.nfc.libhce.reader.CardReader;
import edu.dce.nfc.libhce.reader.TransceiveResult;

/**
 * Created by championswimmer on 5/9/14.
 */
public abstract class ReaderActivity extends Activity implements CardReader.ReadCallBack {

    public static final String TAG = "libHCEReader";
    public static int READER_FLAGS =
            NfcAdapter.FLAG_READER_NFC_A | NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK;

    private CardReader mCardReader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCardReader = new CardReader(this);
        Log.i(TAG, "onCreate CardReader instance made");
    }

    /**
     * Method to start the reader mode.
     * <p/>
     * If reader mode isn't enabled, devices wont interact, even when tapped together.
     * Reader mode should be enabled before tapping to be able to detect the other device.
     */
    protected void enableReaderMode() {
        Log.i(TAG, "Enabling reader mode");

        NfcAdapter nfc = NfcAdapter.getDefaultAdapter(this);
        if (nfc != null) {
            nfc.enableReaderMode(this, mCardReader, READER_FLAGS, null);
        }
        //Toast.makeText(getBaseContext(), "Enabled Reader Mode", Toast.LENGTH_SHORT).show();
    }

    /**
     * Method to stop reader mode.
     * <p/>
     * Advisable to free up the reader mode when leaving the activity.
     * If reader mode is engaged, other NFC functions like Beam and other Wallet like
     * apps wont function, so always close reader mode safely before leaving the app.
     */
    protected void disableReaderMode() {
        Log.i(TAG, "Disabling reader mode");
        NfcAdapter nfc = NfcAdapter.getDefaultAdapter(this);
        if (nfc != null) {
            nfc.disableReaderMode(this);
        }
        //Toast.makeText(getBaseContext(), "Disabled Reader Mode", Toast.LENGTH_SHORT).show();
    }


    /**
     * Method to start a transaction with the emulator app on the other device
     * <p/>
     * Send a custom "sendCommand" that will be read by emulator, which will
     * accordingly send back a message, that will be returned by this function.
     * <p/>
     * Usually you do not need to override this function.
     * If data is larger than 255 chars, this method will ideally
     * internally iterate over the calls to send the data.
     *
     * @param command - A string command to send to card emulator device.
     * @return - The message sent back by emulator device after receiving the command
     */
    @Override
    public String transactNfc (IsoDep isoDep, String command) throws IOException {
        Log.d(TAG, "transactNFC started");

        int resultLength = 0;
        String gotData = "", finalGotData = "";
        long timeTaken = 0;
        TransceiveResult mResult;

        Log.d(TAG, "doing transactNfc with command = " + command);

        // Split command into 255 byte parts
        String[] commandParts = Utils.StringSplit255(command);

        // Keep fetching until we reach the end
        for (int i = 0; i < commandParts.length; i++) {
            Log.d(TAG, "we are inside commandParts loop");
            Log.d(TAG, "commandParts.length = " + commandParts.length);
            Log.d(TAG, "commandParts = " + commandParts[i]);
            byte[] sendCommand = Headers.BuildSendDataApdu(i, commandParts[i]);
            Log.i(TAG, "Sending: " + Utils.ByteArrayToHexString(sendCommand));
            mResult = TransceiveResult.get(isoDep, sendCommand);
            resultLength = mResult.getLength();
            Log.i(TAG, "Received rlen : " + resultLength);
            byte[] statusWordNew = mResult.getStatusword();
            if (Arrays.equals(Headers.RESPONSE_SENDCOMMAND_OK, statusWordNew)) {
                gotData = new String(mResult.getPayload(), "UTF-8");
                Log.i(TAG, "Received: " + gotData);
            }
        }

        byte[] sendCommandFinal = Headers.BuildSendDataApdu(commandParts.length, "END_OF_COMMAND");
        Log.i(TAG, "Sending: " + Utils.ByteArrayToHexString(sendCommandFinal));
        mResult = TransceiveResult.get(isoDep, sendCommandFinal);
        resultLength = mResult.getLength();
        Log.i(TAG, "Received rlen : " + resultLength);
        byte[] statusWordNew = mResult.getStatusword();
        if (Arrays.equals(Headers.RESPONSE_SENDCOMMAND_PROCESSED, statusWordNew)) {
            gotData = new String(mResult.getPayload(), "UTF-8");
            Log.i(TAG, "Received: " + gotData);
        }

        while (true) {
            Log.d(TAG, "we are inside getData loop");
            byte[] getCommand = Headers.BuildGetDataApdu();
            Log.i(TAG, "Sending: " + Utils.ByteArrayToHexString(getCommand));
            mResult = TransceiveResult.get(isoDep, getCommand);
            resultLength = mResult.getLength();
            Log.i(TAG, "Received rlen : " + resultLength);
            statusWordNew = mResult.getStatusword();
            if (Arrays.equals(Headers.RESPONSE_GETDATA_INTERMEDIATE, statusWordNew)) {
                gotData = new String(mResult.getPayload(), "UTF-8");
                Log.i(TAG, "Received: " + gotData);
                finalGotData = finalGotData + gotData;
                Log.i(TAG, "Data transferred : " + finalGotData.length());
                Log.i(TAG, "Time taken: " + (System.currentTimeMillis() - timeTaken));
                Log.d(TAG, "Final data = " + finalGotData);
            } else if (Arrays.equals(Headers.RESPONSE_GETDATA_FINAL, statusWordNew)) {
                break;
            }
        }
        return finalGotData;

    }

    public abstract void onHceStarted (IsoDep isoDep);

}
