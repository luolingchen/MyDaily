package com.luolingchen.mydaily.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.luolingchen.mydaily.R;
import com.luolingchen.mydaily.utils.Constant;
import com.luolingchen.mydaily.utils.HttpUtils;
import com.luolingchen.mydaily.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;

import cz.msebera.android.httpclient.Header;

public class SplashActivity extends Activity {
    private ImageView iv_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        iv_start = (ImageView) findViewById(R.id.iv_start);
        initImage();
    }

    private void initImage() {
        File dir = getFilesDir();
        final File imageFile = new File(dir, "start.jpg");
        if (imageFile.exists()) {
            iv_start.setImageBitmap(BitmapFactory.decodeFile(imageFile.getAbsolutePath()));
        } else {
            iv_start.setImageResource(R.mipmap.start);
        }

        ScaleAnimation animation =
                new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setFillAfter(true);
        animation.setDuration(3000);
        animation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (HttpUtils.isNetworkConnected(SplashActivity.this)) {
                    HttpUtils.get(Constant.START, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            try {
                                JSONObject jsonobj = new JSONObject(new String(responseBody));
                                String url = jsonobj.getString("img");
                                String[] urls = url.split("\\/\\/");
                                url = "http://"+urls[1];
                                LogUtils.e("New Url=" + url);
                                HttpUtils.get(url, new BinaryHttpResponseHandler() {
                                    @Override
                                    public void onSuccess(int statusCode, Header[] headers, byte[] binaryData) {
                                        saveImage(imageFile, binaryData);
                                        LogUtils.e("成功");
                                        startActivity();
                                    }

                                    @Override
                                    public void onFailure(int statusCode, Header[] headers, byte[] binaryData, Throwable error) {
                                        LogUtils.e("失败"+statusCode);
                                        startActivity();
                                    }
                                });
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                        }
                    });
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        iv_start.startAnimation(animation);
    }

    private void startActivity() {
        LogUtils.e("进入主界面");
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

    private void saveImage(File imageFile, byte[] binaryData) {
        LogUtils.e("保存图片");
        if (imageFile.exists()) {
            imageFile.delete();
        }
        try {
            FileOutputStream fos = new FileOutputStream(imageFile);
            fos.write(binaryData);
            fos.flush();
            fos.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
