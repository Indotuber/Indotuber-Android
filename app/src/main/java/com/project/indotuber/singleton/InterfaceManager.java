package com.project.indotuber.singleton;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.project.indotuber.R;

/**
 * Created by yoasfs on 8/3/15.
 */
public class InterfaceManager {
    private static final InterfaceManager INTERFACEMANAGER = new InterfaceManager();
    View loadingFrameLayout;
    EditText invisibleEditText;
    boolean isErrMsgShown = false;
    public static InterfaceManager sharedInstance(){
        return INTERFACEMANAGER;
    }
    public InterfaceManager(){

    }

//    public  void showErrorMessage(Context ctx, String errMsg){
//        if(!isErrMsgShown){
//            isErrMsgShown = true;
//            AlertDialog.Builder alrt = new AlertDialog.Builder(ctx);
//            alrt.setTitle(AppController.getInstance().getResString(R.string.suresell_error)).setMessage(errMsg);
//            alrt.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    isErrMsgShown = false;
//                }
//            });
//            alrt.show();
//        }
//    }

    public void showSuccessMessage(Context ctx, String successMsg){
        AlertDialog.Builder alrt = new AlertDialog.Builder(ctx);
        alrt.setTitle("Train With Tanya").setMessage(successMsg);
        alrt.setPositiveButton("OK",null);
        alrt.show();
    }

    public void showSuccessMessageWithBlock(Context ctx, String successMsg){
        AlertDialog.Builder alrt = new AlertDialog.Builder(ctx);
        alrt.setTitle("SureSell").setMessage(successMsg);
        alrt.setPositiveButton("OK", null);
        alrt.show();
    }

//    public void showLoadingWithoutLostFocus(FrameLayout rootFrameLayout,Context context){
//        LayoutInflater inflater = (LayoutInflater)   context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        loadingFrameLayout = inflater.inflate(R.layout.item_loading, null);
//        ProgressBar progressBar = (ProgressBar)loadingFrameLayout.findViewById(R.id.progressBar);
//        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#f15a24"), PorterDuff.Mode.SRC_ATOP);
//        rootFrameLayout.addView(loadingFrameLayout);
//        loadingFrameLayout.setVisibility(View.VISIBLE);
//        invisibleEditText = (EditText)loadingFrameLayout.findViewById(R.id.invisibleEditText);
//        Button invisibleButton = (Button)loadingFrameLayout.findViewById(R.id.invisibleButton);
//        invisibleEditText.setVisibility(View.GONE);
//        invisibleButton.setVisibility(View.GONE);
//    }
//
    public void showLoading(FrameLayout rootFrameLayout,Context context){
        LayoutInflater inflater = (LayoutInflater)   context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        loadingFrameLayout = inflater.inflate(R.layout.item_loading, null);
        ProgressBar progressBar = (ProgressBar)loadingFrameLayout.findViewById(R.id.progressBar);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#B14BA9"), PorterDuff.Mode.SRC_ATOP);
        rootFrameLayout.addView(loadingFrameLayout);
        loadingFrameLayout.setVisibility(View.VISIBLE);
        invisibleEditText = (EditText)loadingFrameLayout.findViewById(R.id.invisibleEditText);
        invisibleEditText.requestFocus();
    }

    public void hideLoading( ){
        if(loadingFrameLayout!=null){
            loadingFrameLayout.setVisibility(View.GONE);
        }
    }

    //size converter
    public int dpToPx(Context ctx, int dp)
    {
        DisplayMetrics displayMetrics = ctx.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    public int pxToDp(Context ctx, int px)
    {
        DisplayMetrics displayMetrics = ctx.getResources().getDisplayMetrics();
        int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return dp;

    }

    public int getDeviceWidth(Activity activity){
        final int version = Build.VERSION.SDK_INT;
        final int width;
        Display display = activity.getWindowManager().getDefaultDisplay();
        if (version >= 13)
        {
            Point size = new Point();
            display.getSize(size);
            width = size.x;
        }
        else
        {
            width = display.getWidth();
        }
        return width;
    }

    public void openURLWithNativeBrowser(String url,Activity activity){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        try
        {
            activity.startActivity(intent);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void sendEmailWithNativeMailComposer(String recipient ,String subject,String text,String cc, Activity activity){
        Intent intent=new Intent(Intent.ACTION_SEND);
        String[] recipients={recipient};
        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
        intent.putExtra(Intent.EXTRA_SUBJECT,subject);
        intent.putExtra(Intent.EXTRA_TEXT,"Android "+ Build.VERSION.RELEASE+", Device "+getDeviceName()+"\n"+text+"\n\n");
        intent.putExtra(Intent.EXTRA_CC,cc);
        intent.setType("text/html");
        activity.startActivity(Intent.createChooser(intent, "Send mail"));
    }

    public String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }


    private String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    public void showErrorMessage(Context ctx, String errorMessage){
        if(!isErrMsgShown){
            isErrMsgShown=true;
            AlertDialog.Builder alrt = new AlertDialog.Builder(ctx);
            alrt.setTitle("Message").setMessage(errorMessage);
            alrt.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    isErrMsgShown=false;
                }
            });
            alrt.show();
        }
    }




}
