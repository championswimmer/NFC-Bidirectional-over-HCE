package edu.dce.nfc.cardreader;

import android.nfc.tech.IsoDep;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;

import edu.dce.nfc.libhce.ReaderActivity;
import edu.dce.nfc.libhce.reader.CardReader;


public class MainActivity extends ReaderActivity {

    public static int mMaxTransceiveLength;
    public static boolean mExtendedApduSupported;
    public static int mTimeout;


    @Override
    public void onHceStarted(IsoDep isoDep) {
        Log.d("NFC Reader", "onHCEStarted");

        mExtendedApduSupported = isoDep.isExtendedLengthApduSupported();
        mMaxTransceiveLength = isoDep.getMaxTransceiveLength();
        mTimeout = isoDep.getTimeout();
        isoDep.setTimeout(3600);
        mTimeout = isoDep.getTimeout();

        Log.d(TAG,
                "  Extended APDU Supported = " + mExtendedApduSupported +
                "  Max Transceive Length = " + mMaxTransceiveLength +
                "  Timeout = " + mTimeout);

        // TODO:
        // Start sending the commands here
        // Using transactNFC(command); calls
        try {
            transactNfc(isoDep, "somecommand");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("NFC Reader", "starting reader");
        enableReaderMode();
    }

    @Override
    protected void onPause() {
        super.onPause();
        disableReaderMode();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
