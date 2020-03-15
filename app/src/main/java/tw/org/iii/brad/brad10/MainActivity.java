package tw.org.iii.brad.brad10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private File sdroot,approot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        123);
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
        } else {
            // Permission has already been granted
            Log.v("brad","debug1");
            //有拿到授權就做事
            init();
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode ==123){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.v("brad","debug2");
                //有拿到授權就做事
                init();
            }else {
                Log.v("brad","debug3");
                finish();
            }
        }
    }
    private  void init(){
        String state = Environment.getExternalStorageState();
        //掛載
        Log.v("brad",state);//mounted or removed
        //
        sdroot = Environment.getExternalStorageDirectory();
        Log.v("brad",sdroot.getAbsolutePath());

        //賦予放置的資料位置
        approot = new File(sdroot,
                "Android/data/"+getPackageName());
        //將資料放在外類 sd中
        if (!approot.exists()){
            approot.mkdirs();
        }

    }

    public void text1(View view) {
        try {
            FileOutputStream fout = new FileOutputStream(sdroot.getAbsolutePath()+"/001.txt");
            fout.write("hello,world".getBytes());
            fout.flush();
            fout.close();
            Toast.makeText(this,"ok",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.v("brad",e.toString());
        }
    }

    public void text2(View view) {
        try {
            FileOutputStream fout = new FileOutputStream(approot.getAbsolutePath()+"/002.txt");
            fout.write("hello,world".getBytes());
            fout.flush();
            fout.close();
            Toast.makeText(this,"ok",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.v("brad",e.toString());
        }
    }

    public void text3(View view) {
        try {
            Log.v("brad",sdroot.getAbsolutePath());
            FileInputStream fin =
                    new FileInputStream(sdroot.getAbsolutePath()+"/001.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fin));
            String line = reader.readLine();
            fin.close();
            Log.v("brad",line);
        }catch (Exception e){
            Log.v("brad",e.toString());

        }
    }

    public void text4(View view) {
        try {
            FileInputStream fin =
                    new FileInputStream(approot.getAbsolutePath()+"/002.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fin));
            String line = reader.readLine();
            fin.close();
            Log.v("brad",line);
        }catch (Exception e){
            Log.v("brad",e.toString());

        }
    }

    public void text5(View view) {
        File musicDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_ALARMS);
        if (musicDir.exists()&&musicDir.isDirectory()){
            File[] musicts =musicDir.listFiles();
            for (File music:musicts
                 ) {
                Log.v("brad",music.getAbsolutePath());
            }
        }else {
            Log.v("brad","沒東西");
        }
    }
}
