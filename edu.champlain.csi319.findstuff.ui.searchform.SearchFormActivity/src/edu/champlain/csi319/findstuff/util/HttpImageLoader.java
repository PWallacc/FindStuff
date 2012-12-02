package edu.champlain.csi319.findstuff.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class HttpImageLoader
{
    private static final String TAG = HttpImageLoader.class.getSimpleName();
    
    private static final BitmapFactory.Options OPTIONS = new BitmapFactory.Options();
    
    /**
     * No instantiate constructor
     */
    private HttpImageLoader() {}
    
    /**
     * 
     * @param urlString
     * @param width Desired image width in pixels
     * @param height Desired image height in pixels
     * @return
     */
    public static Bitmap decodeUrl(String urlString, int width, int height)
    {
        try
        {
            URL url = new URL(urlString);
            InputStream inputStream = (InputStream)url.getContent();
            
            OPTIONS.inJustDecodeBounds = true;  
            BitmapFactory.decodeStream(inputStream, null, OPTIONS);
            
            if(OPTIONS.outWidth > 0 && OPTIONS.outHeight > 0)
            {
                OPTIONS.inSampleSize = calculateInSampleSize(OPTIONS, width, height);
                OPTIONS.inJustDecodeBounds = false;
                inputStream = (InputStream)url.getContent();              
                
                return BitmapFactory.decodeStream(inputStream, null, OPTIONS);
            }
        }
        catch(MalformedURLException e)
        {
            Log.e(TAG, e.getMessage() + ":" + urlString);
        }
        catch(IOException e)
        {
            Log.e(TAG, e.getMessage() + ":" + urlString);
        }
        
        return null;
    }
    
    /**
     * http://developer.android.com/training/displaying-bitmaps/load-bitmap.html
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) 
    {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
    
        if(height > reqHeight || width > reqWidth) 
        {
            if(width > height) 
            {
                inSampleSize = Math.round((float)height / (float)reqHeight);
            } 
            else 
            {
                inSampleSize = Math.round((float)width / (float)reqWidth);
            }
        }
        
        return inSampleSize;
    }
}
