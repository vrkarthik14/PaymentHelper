package com.example.dum;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Intent intent = new Intent();
//        intent.setAction(Intent.ACTION_VIEW);
//        intent.setData(Uri.parse(getUPIString("vrkarthik@ybl","V R Karthik","asdfas","aaf","asdf","asdf","300","INR","https:/asdf")));
//        Intent chooser = Intent.createChooser(intent, "Pay with...");
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//            startActivityForResult(chooser, 1, null);
//        }


        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        // Get deep link from result (may be null if no link is found)
                        Uri deepLink = null;
                        if (pendingDynamicLinkData != null) {
                            deepLink = pendingDynamicLinkData.getLink();
                            //price
                            String price = deepLink.getQueryParameter("price");
                            String message = deepLink.getQueryParameter("message");
                            String upiID = deepLink.getQueryParameter("upiID");

                            Log.i("tag22",price+" "+message+" "+upiID);

                                    Intent intent = new Intent();
                                    intent.setAction(Intent.ACTION_VIEW);
                                    intent.setData(Uri.parse(getUPIString(upiID,"V R Karthik","kker","as123afe","23adf1123fas",message,price,"INR","https://asdf")));
                                    Intent chooser = Intent.createChooser(intent, "Pay with...");
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                        startActivityForResult(chooser, 1, null);
                                    }
                        }


                        // Handle the deep link. For example, open the linked
                        // content, or apply promotional credit to the user's
                        // account.
                        // ...

                        // ...
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("tag1", "getDynamicLink:onFailure", e);
                    }
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        try
        {
            Log.e("UPI RESULT ",""+requestCode);
            Log.e("UPI RESULT ",""+resultCode);
            Log.e("UPI RESULT ",""+data);



            if(resultCode == -1)
            {

                // 200 Success

            }
            else
            {
                // 400 Failed
            }


            MainActivity.this.finish();


        }
        catch(Exception e)
        {
            Log.e("Error in UPI ",""+e.getMessage());
        }
    }

    private String getUPIString(String payeeAddress, String payeeName, String payeeMCC, String trxnID, String trxnRefId,
                                String trxnNote, String payeeAmount, String currencyCode, String refUrl) {
        String UPI = "upi://pay?pa=" + payeeAddress + "&pn=" + payeeName
                + "&mc=" + payeeMCC + "&tid=" + trxnID + "&tr=" + trxnRefId
                + "&tn=" + trxnNote + "&am=" + payeeAmount + "&cu=" + currencyCode
                + "&refUrl=" + refUrl;
        return UPI.replace(" ", "+");
    }
}
