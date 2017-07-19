package com.buzzed.utility;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.text.InputFilter;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.buzzed.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CommonUtilities {

    public static final String BuzzURL = "http://www.google.com/";  //demowork / local
    public static final String MyPREFERENCES = "MyPrefs", Security_PREFERENCES = "SecurityPrefs";
    public static final String Pref__TokenKey = "TokenKey";
    public static final String Pref_DeviceReg_TokenKey = "DeviceRegTokenKey";
    public static final String Pref_Login_TokenKey = "LoginTokenKey";
    public static final String Pref_isLogin = "LoginKey";
    public static final String Pref_User_Id = "User_Id";

    public static SharedPreferences sharedpreferences, security_sharedpreferences;

    public static int socketTimeout = 10000;
    static boolean dialogShown = false;

    public static void setPreference(Context context, String key, String value) {
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putString(key, value);
        editor.commit();
    }

    public static String getPreference(Context context, String key) {
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES,
                Context.MODE_PRIVATE);
        return sharedpreferences.getString(key, "");
    }

    public static void setTokenKey(Context context, String key, String value) {
        security_sharedpreferences = context.getSharedPreferences(Security_PREFERENCES,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = security_sharedpreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getTokenKey(Context context, String key) {
        security_sharedpreferences = context.getSharedPreferences(Security_PREFERENCES,
                Context.MODE_PRIVATE);
        return security_sharedpreferences.getString(key, "");
    }

    public static void setBooleanPreference(Context context, String key, Boolean flag) {
        security_sharedpreferences = context.getSharedPreferences(MyPREFERENCES,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = security_sharedpreferences.edit();
        editor.putBoolean(key, flag);
        editor.commit();
    }

    public static boolean getBooleanPreference(Context context, String key) {
        security_sharedpreferences = context.getSharedPreferences(MyPREFERENCES,
                Context.MODE_PRIVATE);

        return security_sharedpreferences.getBoolean(key, false);
    }

    public static void RemovePreferenceKey(Context context, String key) {
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.remove(key);
        editor.commit();
    }

    public static void RemoveTokenKey(Context context, String key) {
        security_sharedpreferences = context.getSharedPreferences(Security_PREFERENCES,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = security_sharedpreferences.edit();
        editor.remove(key);
        editor.commit();
    }

    public static void RemovePreference(Context context, String name) {
        sharedpreferences = context.getSharedPreferences(name,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.clear();
        editor.commit();
    }

    public static boolean isConnectingToInternet(Context _context) {
        ConnectivityManager connectivity = (ConnectivityManager) _context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }

    public static void setFilter(EditText text, int length) {

        text.setFilters(new InputFilter[]{new InputFilter.LengthFilter(length), new InputFilter.AllCaps()});

    }


    public static String getDeviceId(Context mContext) {
        String DeviceId = Settings.Secure.getString(mContext.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return DeviceId;
    }

    public static void alertdialog(Context context, String msg) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setTitle(R.string.app_name);
        builder1.setIcon(R.mipmap.ic_launcher);
        builder1.setMessage(msg);
        builder1.setCancelable(false);
        builder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alert11 = builder1.create();
        alert11.show();
        Button bq = alert11.getButton(DialogInterface.BUTTON_NEGATIVE);
        bq.setTextColor(Color.parseColor("#0053A8"));
    }

    public static void alertdialogPermission(final Activity context) {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setTitle(R.string.app_name);
        builder1.setIcon(R.mipmap.ic_launcher);
        builder1.setMessage("Unable to change image. Tap on SETTINGS\n> Go to application info \n> Permissions then allow permissions and try again:");
        builder1.setCancelable(false);
        builder1.setPositiveButton("SETTINGS", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                intent.setData(uri);
                context.startActivity(intent);
//                context.finish();
            }
        });
        builder1.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                dialogShown = false;
            }
        });


        if (dialogShown) {
            return;
        } else {
            dialogShown = true;
            AlertDialog alert11 = builder1.create();
            alert11.show();
            Button bq = alert11.getButton(DialogInterface.BUTTON_NEGATIVE);
            Button b = alert11.getButton(DialogInterface.BUTTON_POSITIVE);
            bq.setTextColor(context.getResources().getColor(R.color.colorAccent));
            b.setTextColor(context.getResources().getColor(R.color.colorAccent));
        }


    }

    public static void ShowCustomToast(Context context, String msg, boolean success) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        View layout = inflater.inflate(R.layout.toast,
                null);
        TextView text = (TextView) layout.findViewById(R.id.text);
        LinearLayout toast_layout_root = (LinearLayout) layout.findViewById(R.id.toast_layout_root);
        text.setText(msg);

        if (success) {
            toast_layout_root.setBackgroundColor(context.getResources().getColor(android.R.color.holo_green_light));
        } else {
            toast_layout_root.setBackgroundColor(context.getResources().getColor(android.R.color.holo_red_light));
        }

        Toast toast = new Toast(context);
        toast.setGravity(Gravity.TOP | Gravity.FILL_HORIZONTAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    public static boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static void showSnackbar(View view, String msg) {
        Snackbar snackbar = Snackbar
                .make(view, msg, Snackbar.LENGTH_LONG);
        view = snackbar.getView();
        view.setBackgroundColor(Color.parseColor("#0053A8"));
        snackbar.show();
    }

    public static void log(String msg) {
        Log.e("BUZZED", "Buzz :: " + msg);
    }

    public static void setFontFamily(Context c, TextView txt, String font) {
        Typeface typeface = Typeface
                .createFromAsset(c.getAssets(), font);
        txt.setTypeface(typeface);
    }

    public static void ShowToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static void setFontFamily(Context c, Button txt, String font) {
        Typeface typeface = Typeface
                .createFromAsset(c.getAssets(), font);
        txt.setTypeface(typeface);
    }

    public static void setFontFamily(Context c, EditText txt, String font) {
        Typeface typeface = Typeface
                .createFromAsset(c.getAssets(), font);
        txt.setTypeface(typeface);
    }


    public static void setPermission(Context context, String[] permission) {
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = permission;
        if (!hasPermissions(context, PERMISSIONS)) {

            ActivityCompat.requestPermissions((Activity) context, PERMISSIONS, PERMISSION_ALL);
        }
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void hideSoftKeyboard(Activity act, EditText edt) {

        InputMethodManager im = (InputMethodManager) act
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(edt.getWindowToken(), 0);

    }

    public static void showoftKeyboard(Activity act, EditText edt) {
        InputMethodManager imm = (InputMethodManager) act.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(edt, InputMethodManager.SHOW_FORCED);
    }

    public static void hideSoftKeyboard(Activity act) {
        InputMethodManager im = (InputMethodManager) act
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(act.getCurrentFocus().getWindowToken(), 0);
    }

    public static void hideKeyboard(Context ctx) {
        InputMethodManager inputManager = (InputMethodManager) ctx
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View v = ((Activity) ctx).getCurrentFocus();
        if (v == null)
            return;

        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }


    public static String getText(String value) {
        String returnedText = "";
        if (value != null && value.equals("") || value.length() < 1 || value.startsWith(",")) {
            returnedText = "-";
        } else {
            returnedText = value;
        }
        return returnedText;
    }

    public static String getAddress(String address1, String address2, String suburb, String state, String pincode) {
        String address = "";
        String[] add1;

        add1 = new String[]{"" + address1,
                "" + address2};

        for (int i = 0; i < add1.length; i++) {
            if (!add1[i].equals("")) {
                address += add1[i] + ",\n";
            }
        }

        if (!state.equals("")) {
            state = state + "\n";
        }
        if (!suburb.equals("")) {
            suburb = suburb + "\n";
        }
        address += suburb + state + pincode;

       /* if (address != null && !address.equals("")) {
            address = address.substring(0, address.length() - 2);
        }
        */

        return address.trim();
    }

    public static String getPhoneNumber(String number, int type) {

        String returnedNumber = "";
        String main = number;
        if (number != null && !number.equals("")) {
            if (type == 1) {
                if (number.length() >= 10) {
                    String firsttwo = main.substring(0, 2);
                    String secfour = main.substring(2, 6);
                    String lastfour = main.substring(6, 10);
                    returnedNumber = "(" + firsttwo + ")" + secfour + "-" + lastfour;
                } else if (number.length() >= 2 && number.length() <= 6) {
                    String firsttwo = main.substring(0, 2);
                    String others = main.substring(2, number.length());
                    returnedNumber = "(" + firsttwo + ")" + others;
                } else {
                    returnedNumber = "";
                }
            } else {
                if (number.length() >= 10) {
                    String firstfour = main.substring(0, 4);
                    String secthree = main.substring(4, 7);
                    String lastthree = main.substring(7, 10);
                    returnedNumber = firstfour + "-" + secthree + "-" + lastthree;
                }
            }
        }
        return returnedNumber;
    }

    public static boolean isKeyboardisShown(Context c) {
        InputMethodManager imm = (InputMethodManager)
                c.getSystemService(Context.INPUT_METHOD_SERVICE);

        if (imm.isAcceptingText()) {
            return true;
        } else {
            return false;
        }
    }

    public static int referralDateValidation(String date_String) {

        int year;
        int month;
        int day;

        String[] dateParts = date_String.split("/");

        day = Integer.parseInt(dateParts[0]);
        month = Integer.parseInt(dateParts[1]);
        year = Integer.parseInt(dateParts[2]);


        Calendar dob = Calendar.getInstance();
        dob.set(year, month, day);

        Calendar today = Calendar.getInstance();

        int monthsBetween = 0;
        int dateDiff = today.get(Calendar.DAY_OF_MONTH) - dob.get(Calendar.DAY_OF_MONTH);

        if (dateDiff < 0) {
            int borrrow = today.getActualMaximum(Calendar.DAY_OF_MONTH);
            dateDiff = (today.get(Calendar.DAY_OF_MONTH) + borrrow) - dob.get(Calendar.DAY_OF_MONTH);
            monthsBetween--;

            if (dateDiff > 0) {
                monthsBetween++;
            }
        } else {
            monthsBetween++;
        }
        monthsBetween += today.get(Calendar.MONTH) - dob.get(Calendar.MONTH);
        monthsBetween += (today.get(Calendar.YEAR) - dob.get(Calendar.YEAR)) * 12;
        return monthsBetween;

    }


}
