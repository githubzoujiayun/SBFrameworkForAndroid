package com.sb.framework.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

public class FetchImageUtils {
    /** The launch code when taking a picture */
    private static final int CAMERA_WITH_DATA = 3023;

    /** The launch code when picking a photo and the raw data is returned */
    private static final int PHOTO_PICKED_WITH_DATA = 3021;

    private static int DEFAULT_IMAGE_SIZE = 320;

    private static final File PHOTO_DIR = new File(
            Environment.getExternalStorageDirectory() + "/DCIM/Camera");

    private Activity mActivity;
    private File mCurrentPhotoFile;
    private OnPickFinishedCallback callback;
    private int photox = DEFAULT_IMAGE_SIZE;
    private int photoy = DEFAULT_IMAGE_SIZE;

    public static interface OnPickFinishedCallback {
        public void onPickSuccessed(Bitmap bm);

        public void onPickFailed();

        public void onPickCancel();
    }

    public FetchImageUtils(Activity activity) {
        mActivity = activity;
    }

    /**
     * 用户需要在Activity中的onActivityResult 方法中调用此方法。
     * 
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
        case PHOTO_PICKED_WITH_DATA:
            if (Activity.RESULT_CANCELED == resultCode) {
                if (callback != null) {
                    callback.onPickCancel();
                }
            } else {
                if (data == null) {
                    if (callback != null) {
                        callback.onPickFailed();
                    }
                    return;
                }
                Bitmap bm = data.getParcelableExtra("data");
                if (bm == null) {
                    if (mActivity == null) {
                        return;
                    }
                    Uri uri = data.getData();
                    Options options = new Options();
                    options.outWidth = DEFAULT_IMAGE_SIZE;
                    options.outHeight = DEFAULT_IMAGE_SIZE;
                    bm = compressSourceBitmap(uri);
                }
                if (callback != null) {
                    if (bm == null) {
                        callback.onPickFailed();
                    } else {
                        callback.onPickSuccessed(bm);
                    }
                }

            }
            break;
        case CAMERA_WITH_DATA:
            if (Activity.RESULT_CANCELED == resultCode) {
                if (callback != null) {
                    callback.onPickCancel();
                }
            } else {
                doCropPhoto(mCurrentPhotoFile);
            }
            break;
        }
    }

    private Bitmap compressSourceBitmap(Uri uri) {
        Bitmap bitmap = null;
        if (uri == null) {
            return null;
        }
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            bitmap = BitmapFactory.decodeStream(mActivity.getContentResolver()
                    .openInputStream(uri), null, options);
            options.inJustDecodeBounds = false;
            int rateX = (int) (options.outWidth / (float) DEFAULT_IMAGE_SIZE);
            int rateY = (int) (options.outHeight / (float) DEFAULT_IMAGE_SIZE);
            options.inSampleSize = Math.max(rateX, rateY);
            bitmap = BitmapFactory.decodeStream(mActivity.getContentResolver()
                    .openInputStream(uri), null, options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * Sends a newly acquired photo to Gallery for cropping
     */
    private void doCropPhoto(File f) {
        try {
            // Add the image to the media store
            MediaScannerConnection.scanFile(mActivity,
                    new String[] { f.getAbsolutePath() },
                    new String[] { null }, null);

            // Launch gallery to crop the photo
            final Intent intent = getCropImageIntent(Uri.fromFile(f));
            mActivity.startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
        } catch (Exception e) {
            e.printStackTrace();
            if (callback != null) {
                callback.onPickFailed();
            }
        }
    }

    /**
     * Constructs an intent for image cropping.
     */
    private Intent getCropImageIntent(Uri photoUri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(photoUri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", photox);
        intent.putExtra("aspectY", photoy);
        intent.putExtra("outputX", photox);
        intent.putExtra("outputY", photoy);
        intent.putExtra("return-data", true);
        return intent;
    }

    public void doTakePhoto(OnPickFinishedCallback callback) {
        doTakePhoto(callback, DEFAULT_IMAGE_SIZE, DEFAULT_IMAGE_SIZE);
    }

    /**
     * Launches Camera to take a picture and store it in a file.
     */
    public void doTakePhoto(OnPickFinishedCallback callback, int width,
            int height) {
        this.callback = callback;
        this.photox = width;
        this.photoy = height;
        try {
            // Launch camera to take photo for selected contact
            PHOTO_DIR.mkdirs();
            mCurrentPhotoFile = new File(PHOTO_DIR, getPhotoFileName());
            final Intent intent = getTakePickIntent(mCurrentPhotoFile);
            mActivity.startActivityForResult(intent, CAMERA_WITH_DATA);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            if (callback != null) {
                callback.onPickFailed();
            }
        }
    }

    public void doPickPhotoFromGallery(OnPickFinishedCallback callback) {
        doPickPhotoFromGallery(callback, DEFAULT_IMAGE_SIZE, DEFAULT_IMAGE_SIZE);
    }

    /**
     * Launches Gallery to pick a photo.
     */
    public void doPickPhotoFromGallery(OnPickFinishedCallback callback,
            int width, int height) {
        this.callback = callback;
        this.photox = width;
        this.photoy = height;
        try {
            // Launch picker to choose photo for selected contact
            final Intent intent = getPhotoPickIntent();
            mActivity.startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            if (callback != null) {
                callback.onPickFailed();
            }
        }
    }

    /**
     * Constructs an intent for picking a photo from Gallery, cropping it and
     * returning the bitmap.
     */
    private final Intent getPhotoPickIntent() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", photox);
        intent.putExtra("aspectY", photoy);
        intent.putExtra("outputX", photox);
        intent.putExtra("outputY", photoy);
        intent.putExtra("return-data", true);
        return intent;
    }

    /**
     * Create a file name for the icon photo using current time.
     */
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "'IMG'_yyyyMMdd_HHmmss", Locale.getDefault());
        return dateFormat.format(date) + ".jpg";
    }

    /**
     * Constructs an intent for capturing a photo and storing it in a temporary
     * file.
     */
    private Intent getTakePickIntent(File f) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        return intent;
    }
}
