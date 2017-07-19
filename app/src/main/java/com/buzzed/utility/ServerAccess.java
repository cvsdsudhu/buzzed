package com.buzzed.utility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.buzzed.BuildConfig;


import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.buzzed.utility.CommonUtilities.BuzzURL;
import static com.buzzed.utility.CommonUtilities.Pref_isLogin;


public class ServerAccess {

    static boolean dialogIsShown=false;
    public static void getResponse(final Activity context, final String method, String tag_json_obj, final JSONObject params, boolean progress, final VolleyCallback callback) {

        final Dialog dialog;
        dialog = new Dialog(context);
        if (progress) {
            if (!dialog.isShowing())
                dialog.setCancelable(false);
            dialog.show();
        }
        CommonUtilities.log("URL :: " + BuzzURL + method);
        CommonUtilities.log("params :: " + params.toString());
        JsonObjectRequest stringRequest = new JsonObjectRequest(BuzzURL + method, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        CommonUtilities.log("response :: " + response.toString());

//                        Crashlytics.log("URL :: " + FydoURL + method);
//                        Crashlytics.log("params :: " + params.toString());
//                        Crashlytics.log("response :: " + response.toString());

                        if (dialog.isShowing())
                            dialog.dismiss();
                        callback.onSuccess(response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CommonUtilities.log("Error: " + error.getMessage());
                dialog.dismiss();
                callback.onError(error.getMessage());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Content-Type", "application/json; charset=utf-8");
                header.put("Accept", "application/json");
                header.put("DID", CommonUtilities.getDeviceId(context));
                header.put("AppVer", BuildConfig.VERSION_NAME);

//                if (CommonUtilities.getPreference(context, Pref_isLogin).equals("1")) {
//                    header.put("TK", CommonUtilities.getTokenKey(context, Pref_Login_TokenKey));
//                } else {
//                    header.put("TK", CommonUtilities.getTokenKey(context, Pref_DeviceReg_TokenKey));
//                }
//                header.put("HGUID", CommonUtilities.getTokenKey(context, Pref_HGUID));
                header.put("DT", "2");
                header.put("UID", CommonUtilities.getPreference(context, CommonUtilities.Pref_User_Id));
                CommonUtilities.log("Headers :: " + header.toString());

                return header;
            }
        };

        call_webService(context,stringRequest,dialog, tag_json_obj);

    }


    public static void call_webService(final Activity context, final JsonObjectRequest stringRequest, final Dialog dialog, final String tag_json_obj) {
        int socketTimeout = 20000;//20 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        if (CommonUtilities.isConnectingToInternet(context)) {
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            stringRequest.setRetryPolicy(policy);
            requestQueue.add(stringRequest);
//            AppController.getInstance().addToRequestQueue(stringRequest, tag_json_obj);
        } else {
            if (dialog.isShowing())
                dialog.dismiss();
            Log.e("", context.getResources().getConfiguration().orientation + "");

            }

    }

    public interface VolleyCallback {
        void onSuccess(String result);

        void onError(String error);

    }

}

