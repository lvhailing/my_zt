package com.crecg.staffshield.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Html;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crecg.crecglibrary.RemoteFactory;
import com.crecg.crecglibrary.network.CommonObserverAdapter;
import com.crecg.crecglibrary.network.CommonRequestProxy;
import com.crecg.crecglibrary.network.model.CommonResultModel;
import com.crecg.crecglibrary.network.model.ReturnOnlyTrueOrFalseModel;
import com.crecg.crecglibrary.utils.ToastUtil;
import com.crecg.crecglibrary.utils.encrypt.DESUtil;
import com.crecg.staffshield.R;
import com.crecg.staffshield.common.BaseActivity;
import com.crecg.staffshield.dialog.SelectPhotoDialog;
import com.crecg.staffshield.utils.PhotoUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 上传工作证明
 */

public class UploadWorkProofActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_name;  // 姓名
    private TextView tv_id_card;  // 身份证号
    private TextView tv_phone_number;  // 电话
    private LinearLayout ll_upload_left;  // 上传工作证明 布局
    private LinearLayout ll_upload_right;  // 上传工会盖章证明材料 布局
    private ImageView iv_work_certificate;   // 工作证明
    private ImageView iv_trade_union_certificate;// 工会盖章证明材料
    private Button btn_join_immediately;  //  立即加入

    //表示选择的是相册--2
    private static int GALLERY_REQUEST_CODE = 2;
    //表示选择的是裁剪--3
    private static int CROP_REQUEST_CODE = 3;
    //图片保存SD卡位置
    private final static String IMG_PATH = Environment.getExternalStorageDirectory() + "/jiaokan/imgs/";
    //获取到的图片路径
    private String picPath;
    private Uri photoUri;
    //使用照相机拍照获取图片
    public static final int SELECT_PIC_BY_TACK_PHOTO = 1;
    //使用相册中的图片
    public static final int SELECT_PIC_BY_PICK_PHOTO = 2;
    private String workCertificate;
    private Bitmap newZoomImage; // ImageView最终要展示的bitmap
    private ImageView iv_back;
    private TextView tv_common_title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_upload_work_proof);

        initView();
    }

    private void initView() {
        setTitle();

        tv_name = findViewById(R.id.tv_name);
        tv_id_card = findViewById(R.id.tv_id_card);
        tv_phone_number = findViewById(R.id.tv_phone_number);
        TextView tv_tips = findViewById(R.id.tv_tips);
        ll_upload_left = findViewById(R.id.ll_upload_left);
        ll_upload_right = findViewById(R.id.ll_upload_right);
        iv_work_certificate = findViewById(R.id.iv_work_certificate);
        iv_trade_union_certificate = findViewById(R.id.iv_trade_union_certificate);
        btn_join_immediately = findViewById(R.id.btn_join_immediately);

        String str = "请上传<font color = '#4A67F5'>工作证明</font>" + "或<font color = '#4A67F5'>工会盖章的证明材料</font>等照片";
        tv_tips.setTextSize(15);
        tv_tips.setText(Html.fromHtml(str));

        if (!TextUtils.isEmpty(workCertificate)) {
            File file = new File(IMG_PATH);
            if (file.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(IMG_PATH + "Test.png");
                iv_work_certificate.setImageBitmap(bitmap);
            } else {
                new ImageViewService().execute(workCertificate);
            }
        }
//        else {
//            iv_work_certificate.setImageDrawable(getResources().getDrawable(R.mipmap.ic_launcher));
//        }

        iv_back.setOnClickListener(this);
        ll_upload_left.setOnClickListener(this);
        ll_upload_right.setOnClickListener(this);
        btn_join_immediately.setOnClickListener(this);
    }

    private void setTitle() {
        iv_back = findViewById(R.id.iv_back);
        tv_common_title = findViewById(R.id.tv_common_title);

        iv_back.setImageResource(R.mipmap.img_close);
        tv_common_title.setText(getResources().getString(R.string.title_work_certificate));
    }

    /**
     * 获取网落图片资源
     */
    class ImageViewService extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap data = getImageBitmap(params[0]);
            return data;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            if (result != null) {
                iv_work_certificate.setImageBitmap(result);
                saveBitmap2(result);
            }
//            else {
//                iv_work_certificate.setImageDrawable(getResources().getDrawable(R.mipmap.ic_launcher));
//            }
        }
    }

    private Uri saveBitmap2(Bitmap bm) {
        File tmpDir = new File(IMG_PATH);
        if (!tmpDir.exists()) {
            tmpDir.mkdirs();
        }
        File img = new File(IMG_PATH + "Test.png");
        try {
            FileOutputStream fos = new FileOutputStream(img);
            bm.compress(Bitmap.CompressFormat.PNG, 70, fos);
            fos.flush();
            fos.close();
            return Uri.fromFile(img);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    private Bitmap getImageBitmap(String url) {
        URL imgUrl = null;
        Bitmap bitmap = null;
        try {
            imgUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) imgUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
//            bitmap = ImageUtils.toRoundBitmap(bitmap); // 把图片处理成圆形
            is.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_upload_left: // 上传工作证明
                selectPhoto();
                break;
            case R.id.ll_upload_right: // 上传工会盖章证明材料
                selectPhoto();
                break;
            case R.id.btn_join_immediately: // 立即加入

                break;
        }
    }

    private void selectPhoto() {
        SelectPhotoDialog mDialog = new SelectPhotoDialog(this, new SelectPhotoDialog.OnSelectPhotoChanged() {
            @Override
            public void onAlbum() { // 相册
                pickPhoto();
            }

            @Override
            public void onCamera() { // 相机
                takePhoto();
            }

        });
        mDialog.show();
    }

    /***
     *  从相册选取图片
     */
    private void pickPhoto() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }

    /**
     * 拍照获取图片
     */
    private void takePhoto() {
        //执行拍照前，应该先判断SD卡是否存在
        String sdState = Environment.getExternalStorageState();
        if (sdState.equals(Environment.MEDIA_MOUNTED)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); // "android.media.action.IMAGE_CAPTURE"
            /***
             * 需要说明一下，以下操作使用照相机拍照，拍照后的图片会存放在相册中的
             * 这里使用的这种方式有一个好处就是获取的图片是拍照后的原图
             * 如果不使用ContentValues存放照片路径的话，拍照后获取的图片为缩略图不清晰
             */
            /*//设置图片的保存路径,作为全局变量
            String imageFilePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/filename.jpg";
			File temp = new File(imageFilePath);
			photoUri = Uri.fromFile(temp);//获取文件的Uri*/
            ContentValues values = new ContentValues();
            photoUri = this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            Log.i("aa", "photoUri: -- " + photoUri);
            // 例：（三星手机）photoUri = content://media/external/images/media/27388
            // 例：（华为手机）photoUri = content://media/external/images/media/539797

            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(intent, SELECT_PIC_BY_TACK_PHOTO);
        } else {
            Toast.makeText(this, "内存卡不存在", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 根据用户选择，返回图片资源
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//			doPhoto(requestCode, data);
        if (requestCode == SELECT_PIC_BY_TACK_PHOTO) {  // 表示用的相机
            Bitmap photoBmp = null;
            File file = null;
            file = getFileFromMediaUri(this, photoUri);

            if (photoUri != null) {
                try {
                    photoBmp = getBitmapFormUri(UploadWorkProofActivity.this, photoUri);
                    int degree = PhotoUtils.readPictureDegree(file.getAbsolutePath());
//                    Log.i("aaa", "degree:  " + degree);
                    // 把图片旋转为正的方向
                    Bitmap newBitmap = PhotoUtils.rotateBitmap(photoBmp, degree);
                    if (newBitmap != null) {
                        dialog.setmLoadingTip("正在上传照片，请稍后……");
                        startLoading();
                    }
                    newZoomImage = newBitmap;
                    sendImage(newBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == GALLERY_REQUEST_CODE) {  // 表示用的相册
            if (data == null) {
                return;
            }
            dialog.setmLoadingTip("正在上传照片，请稍后……");
            startLoading();
            Uri mImageCaptureUri = data.getData();
            Bitmap photoBmp = null;
            if (mImageCaptureUri != null) {
                try {
                    photoBmp = getBitmapFormUri(UploadWorkProofActivity.this, mImageCaptureUri);
                    newZoomImage = photoBmp;

                    // 图片上传到服务器
                    sendImage(photoBmp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == CROP_REQUEST_CODE) { // 裁剪
            if (data == null) {
                return;
            }
            Bundle extras = data.getExtras();
            if (extras == null) {
                return;
            }
            Bitmap bm = extras.getParcelable("data");
//            newZoomImage = zoomImage(bm, 600, 300);
			sendImage( bm);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 通过Uri获取文件
     */
    public static File getFileFromMediaUri(Context ac, Uri uri) {
        if (uri.getScheme().toString().compareTo("content") == 0) {
            ContentResolver cr = ac.getContentResolver();
            Cursor cursor = cr.query(uri, null, null, null, null);// 根据Uri从数据库中找
            if (cursor != null) {
                cursor.moveToFirst();
                String filePath = cursor.getString(cursor.getColumnIndex("_data"));// 获取图片路径
                cursor.close();
                if (filePath != null) {
                    return new File(filePath);
                }
            }
        } else if (uri.getScheme().toString().compareTo("file") == 0) {
            return new File(uri.toString().replace("file://", ""));
        }
        return null;
    }

    /**
     * 通过uri获取图片并进行压缩
     */
    public static Bitmap getBitmapFormUri(Activity ac, Uri uri) throws FileNotFoundException, IOException {
//        InputStream input = ac.getContentResolver().openInputStream(uri);
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inPreferredConfig = Bitmap.Config.RGB_565;
//        return BitmapFactory.decodeStream(input, null, options);

        InputStream input = ac.getContentResolver().openInputStream(uri);
        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither = true;//optional
        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();
        //开始按比例缩放图片
        onlyBoundsOptions.inJustDecodeBounds = false;
        int originalWidth = onlyBoundsOptions.outWidth;
        int originalHeight = onlyBoundsOptions.outHeight;
        float maxSize = 1200;
        int be = 1;
        if (originalWidth >= originalHeight && originalWidth > maxSize) {//缩放比,用高或者宽其中较大的一个数据进行计算
            be = (int) (onlyBoundsOptions.outWidth / maxSize);
            be++;
        } else if (originalWidth < originalHeight && originalHeight > maxSize) {
            be = (int) (onlyBoundsOptions.outHeight / maxSize);
            be++;
        }
        //比例压缩
        onlyBoundsOptions.inSampleSize = be;//设置缩放比例
        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//该模式是默认的,可不设
        onlyBoundsOptions.inPurgeable = true;// 同时设置才会有效
        onlyBoundsOptions.inInputShareable = true;//当系统内存不够时候图片自动被回收
        input = ac.getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();
        return compressImage(bitmap);//再进行质量压缩
    }

    /**
     * 质量压缩方法
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int options = 100;
        image.compress(Bitmap.CompressFormat.JPEG, options, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        while (baos.toByteArray().length >200 *1024) {  //循环判断如果压缩后图片是否大于200kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            options -= 5;//图片质量每次减少5
            if (options <= 5) {
                options = 5;//如果图片质量小于5，为保证压缩后的图片质量，图片最底压缩质量为5
            }
            //第一个参数 ：图片格式 ，第二个参数： 图片质量，100为最高，0为最差  ，第三个参数：保存压缩后的数据的流
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            if (options == 5){
                break;//如果图片的质量已降到最低则，不再进行压缩
            }
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据保存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    /**
     * 调接口，上传图片到服务器
     */
    private void sendImage(Bitmap bm) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        bm.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//        byte[] bytes = stream.toByteArray();
//        String img = new String(Base64.encodeToString(bytes, Base64.DEFAULT));

        int options = 100;
        bm.compress(Bitmap.CompressFormat.JPEG, options, stream);//质量压缩方法，把压缩后的数据存放到baos中 (100表示不压缩，0表示压缩到最小)
            Log.i("hh", "options1 = " + options);
        while (stream.toByteArray().length > 200 * 1024) {//循环判断如果压缩后图片是否大于指定大小,大于继续压缩
            stream.reset();//重置baos即让下一次的写入覆盖之前的内容
            options -= 5;//图片质量每次减少5
            if (options <= 5) { //如果图片质量小于5，为保证压缩后的图片质量，图片最底压缩质量为5
                options = 5;
            }
            bm.compress(Bitmap.CompressFormat.JPEG, options, stream);//将压缩后的图片保存到baos中
            Log.i("hh", "options2 = " + options);
            if (options == 5) { //如果图片的质量已降到最低则，不再进行压缩
                break;
            }
        }
        byte[] bytes = stream.toByteArray();
        String img = new String(Base64.encodeToString(bytes, Base64.DEFAULT));
        Log.i("hh", "bytes = " + bytes.length);
        Log.i("hh", "img = " + img.length());


        HashMap<String, Object> param = new HashMap<>();
        param.put("photo", img);
        param.put("id", "8");
        param.put("name", "workPhoto.jpg");
        param.put("photoType", "gzzm");  // 身份证正面照-zmz，身份证反面照-fmz，工作证明-gzzm，　工会盖章证明-ghzm
        String data = DESUtil.encMap(param);

        HashMap<String, Object> paramWrapper = new HashMap<>();
        paramWrapper.put("requestKey", data);
        RemoteFactory.getInstance().getProxy(CommonRequestProxy.class)
//                .uploadImage1(img,"8","workPhoto.jpg","gzzm")
                .uploadImage(paramWrapper)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CommonObserverAdapter<String>() {
            @Override
            public void onMyError() {
                ToastUtil.showCustom("上传图片接口获取数据失败");
                stopLoading();
            }

            @Override
            public void onMySuccess(String result) {
                if (result == null) {
                    return;
                }
                CommonResultModel<ReturnOnlyTrueOrFalseModel> upLoadImgModel = new Gson().fromJson(result, new TypeToken<CommonResultModel<ReturnOnlyTrueOrFalseModel>>() {
                }.getType());
                if (upLoadImgModel.data == null) {
                    return;
                }
                if (Boolean.parseBoolean(upLoadImgModel.data.flag)) {
                    stopLoading();
                    iv_work_certificate.setImageBitmap(newZoomImage);
                    saveBitmap2(newZoomImage);
                    ToastUtil.showCustom(upLoadImgModel.data.message);
                } else {
                    ToastUtil.showCustom(upLoadImgModel.data.message);
                }
            }
        });
    }


}
