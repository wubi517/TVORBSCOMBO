package com.gold.kds517.tvorbs;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import android.os.Bundle;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import com.gold.kds517.tvorbs.activity.LoginActivity;
import com.gold.kds517.tvorbs.activity.SplashActivity;
import com.gold.kds517.tvorbs.apps.Constants;
import com.gold.kds517.tvorbs.apps.FirstServer;
import com.gold.kds517.tvorbs.apps.MyApp;
import com.gold.kds517.tvorbs.dialog.UpdateDlg;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.gold.kds517.tvorbs.apps.MyApp.num_server;

public class MainActivity extends Activity implements View.OnClickListener{
    SharedPreferences serveripdetails;
    String version,app_Url;
    VideoView videoView;
    LinearLayout main_lay;
    ImageButton icon1,icon2,icon3;//
    static {
        System.loadLibrary("notifications");
    }
    public native String getZero();
    public native String getOne();
    public native String getTwo();
    public native String getThree();
    public native String getFour();
    public native String getFive();
    public native String getSix();
    public native String getSeven();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if(MyApp.instance.getPreference().get(Constants.PIN_CODE)==null){
            MyApp.instance.getPreference().put(Constants.PIN_CODE,"0000");
        }
        if(MyApp.instance.getPreference().get(Constants.OSD_TIME)==null){
            MyApp.instance.getPreference().put(Constants.OSD_TIME,10);
        }
        serveripdetails = this.getSharedPreferences("serveripdetails", Context.MODE_PRIVATE);

        main_lay = findViewById(R.id.main_lay);
        main_lay.setVisibility(View.GONE);
        icon1 = findViewById(R.id.icon1);
        icon2 = findViewById(R.id.icon2);
        icon3 = findViewById(R.id.icon3);
        if (num_server!=1){
            icon1.setOnClickListener(this);
            icon2.setOnClickListener(this);
            icon3.setOnClickListener(this);

            Picasso.with(this).load(getFive())
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .error(R.drawable.icon)
                    .into(icon1);

            Picasso.with(this).load(getSix())
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .error(R.drawable.ad1)
                    .into(icon2);

            Picasso.with(this).load(getSeven())
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .error(R.drawable.ad2)
                    .into(icon3);
        }else {
            icon1.setVisibility(View.GONE);
            icon2.setVisibility(View.GONE);
            icon3.setVisibility(View.GONE);
        }


        videoView = findViewById(R.id.video_view);
//        videoView.setVisibility(View.GONE);
        main_lay.setVisibility(View.VISIBLE);

        if (MyApp.is_video_played) {
            videoView.setVisibility(View.GONE);
            main_lay.setVisibility(View.VISIBLE);
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
                CheckSDK23Permission();
            }else if (num_server==1){
                getRespond1();
            }
        }else {
            String path = "android.resource://" + getPackageName() + "/" + R.raw.intro;
            videoView.setVideoURI(Uri.parse(path));
            videoView.start();
            videoView.setOnCompletionListener(mp -> {
                MyApp.is_video_played=true;
                videoView.setVisibility(View.GONE);
                main_lay.setVisibility(View.VISIBLE);
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
                    CheckSDK23Permission();
                }else if (num_server==1){
                    getRespond1();
                }
            });

            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) videoView.getLayoutParams();
            params.width =  metrics.widthPixels;
            params.height = metrics.heightPixels;
            params.leftMargin = 0;
            videoView.setLayoutParams(params);
        }
        try {
            SharedPreferences.Editor server_editor = serveripdetails.edit();
            byte[] decodeValue20 = Base64.decode(getZero(),Base64.DEFAULT);
            server_editor.putString("url1",new String (decodeValue20));
            Log.e("url1",new String (decodeValue20));
            byte[] decodeValue30 = Base64.decode(getOne(),Base64.DEFAULT);
            server_editor.putString("url2",new String (decodeValue30));
            Log.e("url2",new String (decodeValue30));
            byte[] decodeValue40 = Base64.decode(getTwo(),Base64.DEFAULT);
            server_editor.putString("url3",new String (decodeValue40));
            Log.e("url3",new String (decodeValue40));
            server_editor.apply();
        }catch (Exception e){
            Toast.makeText(MainActivity.this, "Server Error!", Toast.LENGTH_SHORT).show();
        }

        try {
            SharedPreferences.Editor server_editor = serveripdetails.edit();
            byte[] decodeValue20 = Base64.decode(getThree().substring(27),Base64.DEFAULT);
            byte[] decodeVaue21 = Base64.decode(new String(decodeValue20).substring(19),Base64.DEFAULT);
            server_editor.putString("autho1",new String (decodeVaue21));
            byte[] decodeValue30 = Base64.decode(getFour().substring(22),Base64.DEFAULT);
            byte[] decodeVaue31 = Base64.decode(new String(decodeValue30).substring(21),Base64.DEFAULT);
            server_editor.putString("autho2",new String (decodeVaue31));
            server_editor.apply();
        }catch (Exception e){
            Toast.makeText(MainActivity.this, "Server Error!", Toast.LENGTH_SHORT).show();
        }
    }

    private void getRespond1(){
        String key = "";
        switch (MyApp.firstServer){
            case first:
                key=Constants.GetUrl1(this);
                break;
            case second:
                key=Constants.GetUrl2(this);
                break;
            case third:
                key=Constants.GetUrl3(this);
                break;
        }
        try {
            String response = MyApp.instance.getIptvclient().login(key);
            Log.e("response",response);
            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("status")) {
                    JSONObject data_obj = object.getJSONObject("data");
                    String url = (String) data_obj.get("url");
                    if (url.startsWith("http://") || url.startsWith("https://")) {
                        if (url.endsWith("/")){
                            url = url;
                        }else {
                            url = url+"/";
                        }
                        JSONArray array = data_obj.getJSONArray("image_urls");
                        SharedPreferences.Editor server_editor = serveripdetails.edit();
                        server_editor.putString("ip", url);
                        version = (String )data_obj.get("version");
                        app_Url = (String )data_obj.get("app_url");
                        String dual_screen=data_obj.getString("pin_2");
                        String tri_screen=data_obj.getString("pin_3");
                        String four_way_screen=data_obj.getString("pin_4");
                        server_editor.putString("dual_screen",dual_screen);
                        server_editor.putString("tri_screen",tri_screen);
                        server_editor.putString("four_way_screen",four_way_screen);
                        server_editor.putString("i",(String) array.get(0));
                        server_editor.putString("m",(String )array.get(1));
                        server_editor.putString("l",(String) array.get(2));
                        server_editor.putString("d1",(String)array.get(3));
                        server_editor.putString("d2",(String )array.get(4));
                        server_editor.apply();
                        if(MyApp.instance.getPreference().get(Constants.getSORT())==null){
                            MyApp.instance.getPreference().put(Constants.getSORT(),0);
                        }
                        if(MyApp.instance.getPreference().get(Constants.getCurrentPlayer())==null){
                            MyApp.instance.getPreference().put(Constants.getCurrentPlayer(),0);
                        }
                        getUpdate();
                    } else {
                        Toast.makeText(MainActivity.this, "Invalid Server URL!", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(MainActivity.this, "Server Error!", Toast.LENGTH_SHORT).show();
                }
            }catch (JSONException e){
                e.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void CheckSDK23Permission() {
        List<String> permissionsNeeded = new ArrayList<String>();
        final List<String> permissionsList = new ArrayList<String>();
        if (!addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE))
            permissionsNeeded.add("READ / WRITE SD CARD");
        if (!addPermission(permissionsList, Manifest.permission.READ_PHONE_STATE))
            permissionsNeeded.add("READPHONE");
        if (permissionsList.size() > 0) {
            requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                    124);
        }else if (num_server==1){
            getRespond1();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean addPermission(List<String> permissionsList, String permission) {
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            if (!shouldShowRequestPermissionRationale(permission))
                return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.e("PermissionsResult", "onRequestPermissionsResult");
        if (num_server==1){
            getRespond1();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void getUpdate(){
        MyApp.instance.versionCheck();
        double code = 0.0;
        try {
            code = Double.parseDouble(version);
        }catch (Exception e){
            code = 0.0;
        }
        MyApp.instance.loadVersion();
        double app_vs = Double.parseDouble(MyApp.version_name);
        if (code > app_vs) {
            UpdateDlg updateDlg = new UpdateDlg(this, new UpdateDlg.DialogUpdateListener() {
                @Override
                public void OnUpdateNowClick(Dialog dialog) {
                    dialog.dismiss();
                    new versionUpdate().execute(app_Url);
                }
                @Override
                public void OnUpdateSkipClick(Dialog dialog) {
                    dialog.dismiss();
                    startNextActivity();
                }
            });
            updateDlg.show();
        }else {
            startNextActivity();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.icon1:
                MyApp.firstServer = FirstServer.first;
                break;
            case R.id.icon2:
                MyApp.firstServer = FirstServer.second;
                break;
            case R.id.icon3:
                MyApp.firstServer = FirstServer.third;
                break;
        }
        getRespond1();
    }

    class versionUpdate extends AsyncTask<String, Integer, String> {
        ProgressDialog mProgressDialog;
        File file;

        @Override
        protected void onPreExecute() {
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setMessage(getResources().getString(R.string.request_download));
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);
            mProgressDialog.setProgress(values[0]);
        }

        @Override
        protected String doInBackground(String... params) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                int fileLength = connection.getContentLength();
                input = connection.getInputStream();
                String destination = Environment.getExternalStorageDirectory() + "/";
                String fileName = "supanewui.apk";
                destination += fileName;
                final Uri uri = Uri.parse("file://" + destination);
                file = new File(destination);
                if(file.exists()){
                    file.delete();
                }
                output = new FileOutputStream(file, false);

                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    if (fileLength > 0)
                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }
            } catch (Exception e) {
                return e.toString();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }
                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            mProgressDialog.dismiss();
            if (result != null) {
                Toast.makeText(getApplicationContext(),"Update Failed",Toast.LENGTH_LONG).show();
                startNextActivity();
            } else
                startInstall(file);
        }
    }

    private void startNextActivity() {
        switch (MyApp.firstServer){
            case first:
            case second:
            case third:
                if (MyApp.instance.getPreference().get(Constants.getLoginInfo())!=null)
                    startActivity(new Intent(MainActivity.this, SplashActivity.class));
                else startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
                break;
        }
    }

    private void startInstall(File fileName) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(FileProvider.getUriForFile(MainActivity.this,BuildConfig.APPLICATION_ID + ".provider",fileName), "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        View view = getCurrentFocus();
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_BACK:
                    finish();
                    System.exit(0);
                    android.os.Process.killProcess(android.os.Process.myPid());
                    break;
            }
        }
        return super.dispatchKeyEvent(event);
    }
}
