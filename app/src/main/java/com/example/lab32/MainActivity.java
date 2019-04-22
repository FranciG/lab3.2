package com.example.lab32;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MainActivity extends AppCompatActivity {
EditText editText;
Button map;
Button call;
Button go;
ImageView imageView;

//Type variable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
imageView=findViewById(R.id.imageView);
    map=findViewById(R.id.map);
//Asign something to the variable map
map.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
goMap();

    }
});

call=findViewById(R.id.call);
call.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        callBtn();

    }
});
editText = findViewById(R.id.editText);
go=findViewById(R.id.go);
go.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
webGo();
    }
});
loadImageFromUrl();


    }

    private void loadImageFromUrl() {
        Picasso.get().load("https://www.oamk.fi/files/3115/2887/8059/Toimistokayttoon_Suomeksi-06.png").into(imageView);

    }

    private void goMap() {
        Uri location = Uri.parse("https://www.google.com/maps/search/?api=1&query=oamk+kotkantie");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);


        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(mapIntent, 0);
        boolean isIntentSafe = activities.size() > 0;

        String googleMap = "com.google.android.apps.maps/com.google.android.maps.MapsActivity";

        Intent gMapIntent = Intent.createChooser(mapIntent, googleMap);

        Log.d("From List", ""+activities);

        for(int i = 0; i<activities.size(); i++){
            ResolveInfo resolveInfo = activities.get(i);
            String packageName = resolveInfo.activityInfo.packageName;
            if(packageName.contains("android.maps")){
                mapIntent.setPackage(packageName);
            }
        }

        Log.d("Map intent info", ""+mapIntent);

        if (isIntentSafe) {
//            This will popups the available options for the map
            startActivity(mapIntent);
        }
    }

    private void callBtn() {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);

        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(callIntent, 0);
        boolean isIntentSafe = activities.size() > 0;

        if(isIntentSafe)
            startActivity(callIntent);

    }

    public void webGo() {
        String address = editText.getText().toString();
        String httpAddress = "http://" + address ;
        Uri webpage = Uri.parse(httpAddress);
        Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
        startActivity(webIntent);

    }
}
