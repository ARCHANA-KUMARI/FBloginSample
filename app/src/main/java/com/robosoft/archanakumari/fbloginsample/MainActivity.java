package com.robosoft.archanakumari.fbloginsample;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.ShareApi;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    CallbackManager callbackManager;
    LoginManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(getApplicationContext(),"Hi i am in onCreate()",Toast.LENGTH_LONG).show();
        FragmentManager framentmanager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = framentmanager.beginTransaction();
        BlankFragment fragment = new BlankFragment();
        fragmentTransaction.add(R.id.frame,fragment);
        fragmentTransaction.commit();
        FacebookSdk.sdkInitialize(getApplicationContext());

        callbackManager = CallbackManager.Factory.create();

        List<String> permissionNeeds = Arrays.asList("actions");
//  List<String> permissionNeeds = Arrays.asList("publish_actions");
        //this loginManager helps you eliminate adding a LoginButton to your UI
        manager = LoginManager.getInstance();

        manager.logInWithPublishPermissions(this, permissionNeeds);

        manager.registerCallback(callbackManager, new FacebookCallback<LoginResult>()
        {
            @Override
            public void onSuccess(LoginResult loginResult)
            {
                sharePhotoToFacebook();
                Log.i("HI", "HI I AM IN ONsUCCESS");
                Toast.makeText(getBaseContext(),"hI I AM IN ONsUCCESS",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancel()
            {
                //System.out.println("onCancel");
                Toast.makeText(getBaseContext(),"HI I AM IN ONCANCEL",Toast.LENGTH_LONG).show();
                Log.i("HI", "i AM IN ONcANCEL");
            }

            @Override
            public void onError(FacebookException exception)
            {
                Log.i("HI", "oN error");
            }
        });

    }
    private void sharePhotoToFacebook(){
        Bitmap image = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(image)
                .setCaption("Hi welocome to Android-facebook connectivity")
                .build();

        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();

        ShareApi.share(content, null);

    }
    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent data)
    {
        super.onActivityResult(requestCode, responseCode, data);
        callbackManager.onActivityResult(requestCode, responseCode, data);
    }



}
