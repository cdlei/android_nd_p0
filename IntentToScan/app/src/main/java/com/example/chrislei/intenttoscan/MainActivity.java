package com.example.chrislei.intenttoscan;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    private static final String TAG = "INTENT_TO_SCAN_ACTIVITY";
    private static final String SCAN_FORMAT = "scanFormat";
    private static final String SCAN_CONTENTS = "scanContents";

    private String mScanFormat = "Format:";
    private String mScanContents = "Contents:";

    private TextView mScanFormatView;
    private TextView mScanContentsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewGroup activityContainer = (ViewGroup) findViewById(R.id.activity_main_container);
        Button scanButton = (Button) activityContainer.findViewById(R.id.button_initiate_scan);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                 * TODO: To Be Completed By Udacity Student
                 * This is the callback method that the system will invoke when your button is
                 * clicked. Implement the code necessary to compose and send your Intent that will
                 * launch the ZXing app here.
                 * HINT: Use a Try/Catch block to dispatch the Intent and redirect the user to the
                 * Google Play store if the app is not installed.
                 */
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        /*
         * TODO: To Be Completed By Udacity Student
         * This is the method that system will call when the user is finished using the
         * ZXing activity.
         * Implement the code necessary to process the scan result here.
         * Remember to:
         * - Display the barcode format and contents on the main activity
         * - Parse the barcode contents for a URL and launch the browser if found.
         * - Notify the user when launching the browser
         * - Notify the user if the scan failed.
         */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
