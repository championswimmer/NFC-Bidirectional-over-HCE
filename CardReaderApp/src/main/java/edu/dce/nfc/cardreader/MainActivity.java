package edu.dce.nfc.cardreader;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import edu.dce.nfc.libhce.ReaderActivity;
import edu.dce.nfc.libhce.reader.CardReader;


public class MainActivity extends ReaderActivity {

    public MainActivity(CardReader.ReadCallBack ac) {
        super(ac);
    }

    @Override
    public void onHceStarted() {
        //
        // TODO:
        // Start sending the commands here
        // Using transactNFC(command); calls



    }

    @Override
    public void onDataReceived(String data) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
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
