package com.helen.andbase.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;

import com.helen.andbase.application.HConstant;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by 李晓伟 on 2015/10/16.
 * 图像工具类
 */
public class ImageUtil {

    /**
     *
     * 2015-4-8 下午12:00:37
     *  根据uri获取图片路径
     */
    public static String getRealFilePath( final Context context, final Uri uri ) {
        if ( null == uri ) return null;
        String filePath=null;
        try{
            ContentResolver cr = context.getContentResolver();
            InputStream is=cr.openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(is, null, null);
            is.close();
            byte[] datas = extractThumbnail(bitmap,0);
            if(datas!=null){
                File file=FileUtil.getInstance().createFileInSDCard(HConstant.DIR_IMAGE, System.currentTimeMillis()+".jpg");
                FileOutputStream fileOutputStream=new FileOutputStream(file);
                fileOutputStream.write(datas);
                fileOutputStream.flush();
                fileOutputStream.close();
                filePath=file.getAbsolutePath();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return filePath;
    }
    /**
     *
     * 
     * 2015-3-20 下午3:22:49
     *  生成缩略图
     */
    public static byte[] extractThumbnail(Bitmap bitmap,int size) throws Exception{
        if(size <= 0){
            size = 1024*1024;
        }
        float scale=calScale(bitmap);
        bitmap= ThumbnailUtils.extractThumbnail(bitmap, (int) (bitmap.getWidth() / scale), (int) (bitmap.getHeight() / scale), ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 90;
        while (baos.toByteArray().length > size &&options>0) {  //循环判断如果压缩后图片是否大于1M,大于继续压缩
            baos.reset();//重置baos即清空baos
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        bitmap.recycle();
        baos.flush();
        byte[] resource =baos.toByteArray();
        baos.close();
        return resource;
    }
    /**
     *
     * 
     * 2015-3-20 上午10:13:18
     *  根据宽高获取缩放比例
     */
    private static float calScale(Bitmap bitmap){
        int width=480,height=800;//一般分辨率480*800
        float w = bitmap.getWidth();
        float h = bitmap.getHeight();
        if(w>=1920||h>=1920){//1920*1080
            width=1080;
            height=1920;
        }else if(w>=1280||h>=1280){//720*1280
            width=720;
            height=1280;
        }
        float be = 1.0f;//be=1表示不缩放
        if (w > h && w > width) {//如果宽度大的话根据宽度固定大小缩放
            be =  (w / width);
        } else if (w < h && h > height) {//如果高度高的话根据宽度固定大小缩放
            be =  (h / height);
        }
        if (be <= 0)
            be = 1;
        return be;
    }
}
