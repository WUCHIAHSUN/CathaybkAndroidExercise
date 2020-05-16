package com.example.cathaybkandroidexercise;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.util.LruCache;
import android.widget.ImageView;
import java.io.IOException;
import java.net.URL;

public class Utils {

    private static LruCache<String, Bitmap> mMemoryCache;

    public static Utils getInstance(){
        final int maxMemorySize = (int)Runtime.getRuntime().maxMemory() / 1024;
        final int cacheSize = maxMemorySize / 10;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount() / 1024;
            }
        };
        return new Utils();
    }

    public void loadBitmap(final ImageView imageView, final String url) {
            new AsyncTask<String, Void, Bitmap>() {
                @Override
                protected Bitmap doInBackground(String... params) {
                    Bitmap bitmap = null;
                    try {
                        URL newurl = new URL(url);
                        bitmap = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return bitmap;
                }

                @Override
                protected void onPostExecute(Bitmap bitmap) {
                    bitmap = toRoundBitmap(bitmap);
                    imageView.setImageBitmap(bitmap);
                    setBitmapMemory(url, bitmap);
                    super.onPostExecute(bitmap);
                }
            }.execute(url);
    }

    public static Bitmap getBitmapMemory(String key){
        return mMemoryCache.get(key);
    }

    public static void setBitmapMemory(String key, Bitmap bitmap){
        if (getBitmapMemory(key) == null){
            mMemoryCache.put(key, bitmap);
        }
    }

    public Bitmap toRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
         int r = 0;
         if (width > height) {
             r = height;
         } else {
             r = width;
         }
         Bitmap backgroundBmp = Bitmap.createBitmap(width,
                 height, Bitmap.Config.ARGB_8888);
         Canvas canvas = new Canvas(backgroundBmp);
         Paint paint = new Paint();
         paint.setAntiAlias(true);
         RectF rect = new RectF(0, 0, r, r);
         canvas.drawRoundRect(rect, r / 2, r / 2, paint);
         paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
         canvas.drawBitmap(bitmap, null, rect, paint);
         return backgroundBmp;
    }
}
