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
        mScanFormatView = (TextView) activityContainer.findViewById(R.id.text_scan_format);
        mScanContentsView = (TextView) activityContainer.findViewById(R.id.text_scan_contents);

        // Check whether we're recreating a previously destroyed instance
        if (savedInstanceState != null) {
            // Restore value of members from saved state
            mScanFormat = savedInstanceState.getString(SCAN_FORMAT);
            mScanFormatView.setText(mScanFormat);
            mScanContents = savedInstanceState.getString(SCAN_CONTENTS);
            mScanContentsView.setText(mScanContents);
        }

        Button scanButton = (Button) activityContainer.findViewById(R.id.button_initiate_scan);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // This is the callback method that the system will invoke when your button is
                // clicked. Write the code to compose and send your Intent that will launch the
                // ZXing app here.
                // Hint: Use a Try/Catch block to handle the Intent dispatch gracefully
                try {
                    Log.d(TAG,"Sending Intent to Launch Barcode Scanner");
                    Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                    //You can restrict the scanning mode using Intent Extras , for example ...
                    //intent.putExtra("SCAN_MODE")
                    startActivityForResult(intent, 0);
                } catch (ActivityNotFoundException anfe) {
                    Log.w(TAG, "ZXING Package Not Installed. Redirecting to Google Play...");
                    Toast app_missing_toast = Toast.makeText(getApplicationContext(),
                            "App not found. Launching Google Play.",
                            Toast.LENGTH_SHORT);
                    app_missing_toast.show();
                    Uri marketUri = Uri.parse("market://details?id=com.google.zxing.client.android");
                    Intent marketIntent = new Intent(Intent.ACTION_VIEW, marketUri);
                    startActivity(marketIntent);
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save any existing scan information
        savedInstanceState.putString(SCAN_FORMAT, mScanFormat);
        savedInstanceState.putString(SCAN_CONTENTS, mScanContents);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                Log.d(TAG,"Received a scan result, Displaying ...");
                mScanFormat = intent.getStringExtra("SCAN_RESULT_FORMAT");
                mScanContents = intent.getStringExtra("SCAN_RESULT");
                mScanFormatView.setText(mScanFormat);
                mScanContentsView.setText(mScanContents);

                // Look for URLs and launch a browser if found.
                if(mScanContents.contains("http://")) {
                    Log.d(TAG, "Found a URL, attempting to open a browser");
                    Toast browser_toast = Toast.makeText(getApplicationContext(),
                            "URL found, Opening in browser.",
                            Toast.LENGTH_SHORT);
                    browser_toast.show();
                    openWebPage(mScanContents);
                }
            } else if (resultCode == RESULT_CANCELED) {
                // user backed out or the operation failed
                Log.d(TAG,"Scan Result Cancelled ...");
                Toast scan_fail_toast = Toast.makeText(getApplicationContext(),"Scan Failed.",
                        Toast.LENGTH_LONG);
                scan_fail_toast.show();
            }
        }
    }

    private void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
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
