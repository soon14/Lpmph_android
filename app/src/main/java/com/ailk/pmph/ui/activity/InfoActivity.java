package com.ailk.pmph.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ai.ecp.app.req.Staff005Req;
import com.ai.ecp.app.req.Staff006Req;
import com.ai.ecp.app.resp.Staff003Resp;
import com.ai.ecp.app.resp.Staff005Resp;
import com.ai.ecp.app.resp.Staff006Resp;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.JsonService;
import com.ailk.integral.activity.InteMainActivity;
import com.ailk.integral.fragment.InteCartFragment;
import com.ailk.pmph.AppContext;
import com.ailk.pmph.BuildConfig;
import com.ailk.pmph.R;
import com.ailk.pmph.base.BaseActivity;
import com.ailk.pmph.pickerview.TimePickerDialog;
import com.ailk.pmph.pickerview.data.Type;
import com.ailk.pmph.pickerview.listener.OnDateSetListener;
import com.ailk.pmph.ui.fragment.ShopCartFragment;
import com.ailk.pmph.utils.AppUtility;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.ImageUtils;
import com.ailk.pmph.utils.LogUtil;
import com.ailk.pmph.utils.PrefUtility;
import com.ailk.pmph.utils.StringUtil;
import com.ailk.pmph.utils.ToastUtil;
import com.ailk.tool.GlideUtil;

import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Project : PMPH
 * Created by 王可 on 16/3/11.
 */
public class InfoActivity extends BaseActivity implements View.OnClickListener, OnDateSetListener{

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.head_layout)
    LinearLayout headLayout;
    @BindView(R.id.info_img)
    CircleImageView infoImg;
    @BindView(R.id.info_account)
    TextView infoAccount;
    @BindView(R.id.info_name)
    TextView infoName;
    @BindView(R.id.info_birth)
    TextView infoBirth;
    @BindView(R.id.info_sex)
    TextView infoSex;
    @BindView(R.id.info_layout_sex)
    LinearLayout infoLayoutSex;
    @BindView(R.id.info_layout_password)
    LinearLayout infoLayoutPassword;
    @BindView(R.id.info_logout)
    Button infoLogout;
    @BindView(R.id.ll_address_manager)
    LinearLayout llAddressManager;
    @BindView(R.id.info_layout_name)
    LinearLayout infoLayoutName;

    @BindView(R.id.info_layout_birth)
    LinearLayout infoLayoutBirth;
    TimePickerDialog timePickerDialog;

    AlertDialog alertDialog;
    private boolean isSetDate = false;
    int selectGenderIndex = -1;//选择性别dialog 不选择为-1
    private Staff003Resp info;

    private static final int CHOOSE_PICTURE = 0;//相册选择
    private static final int TAKE_PICTURE = 1;//拍照
    private static final int CROP_SMALL_PICTURE = 2;//裁剪图片
    private static Uri tempUri;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_info;
    }

    @Override
    public void initView() {
        timePickerDialog = new TimePickerDialog.Builder().setType(Type.YEAR_MONTH_DAY).setCallBack(this).build();
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        getJsonService().requestStaff003(InfoActivity.this, true, new JsonService.CallBack<Staff003Resp>() {
            @Override
            public void oncallback(Staff003Resp staff003Resp) {
                info = staff003Resp;
                updateInfo();
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    @Override
    @OnClick({R.id.iv_back,R.id.head_layout,R.id.info_layout_name,R.id.info_layout_birth,R.id.info_layout_sex,
            R.id.ll_address_manager,R.id.info_layout_password,R.id.info_logout})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
//                launch(MainActivity.class);
                onBackPressed();
                break;
            case R.id.head_layout:
//                showPhotoView();
                break;
            case R.id.info_layout_name:
                Bundle bundle = new Bundle();
                bundle.putString("nickname", info.getNickname());
                launch(ChangeNameActivity.class, bundle);
                break;
            case R.id.info_layout_birth:
                timePickerDialog.show(getSupportFragmentManager(), "year_month_day");
                break;
            case R.id.info_layout_sex:
                changeSex();
                break;
            case R.id.ll_address_manager:
                launch(AddressActivity.class);
                break;
            case R.id.info_layout_password:
                if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
                    launch(ChangePwdPmphActivity.class);
                } else {
                    launch(ChangePsdActivity.class);
                }
                break;
            case R.id.info_logout:
                if (null == AppUtility.getInstance().getSessionId()) {
                    finish();
                    return;
                }
                Staff005Req req = new Staff005Req();
                req.setTocken(AppUtility.getInstance().getSessionId());
                getJsonService().requestStaff005(InfoActivity.this, req, true, new JsonService.CallBack<Staff005Resp>() {
                    @Override
                    public void oncallback(Staff005Resp staff005Resp) {
                        if (staff005Resp.isFlag()) {
                            AppUtility.getInstance().setSessionId(null);
                            if (PrefUtility.contains("token")) {
                                PrefUtility.remove("token");
                            }
                            if (PrefUtility.contains("staffId")) {
                                PrefUtility.remove("staffId");
                            }
                            if (PrefUtility.contains("staffCode")) {
                                PrefUtility.remove("staffCode");
                            }
                            AppContext.isLogin = false;
                            sendBroadcast(new Intent(ShopCartFragment.REFRESH_CART));
                            sendBroadcast(new Intent(MainActivity.DIMISS_CART_GOODS_NUM));

                            sendBroadcast(new Intent(InteCartFragment.REFRESH_INTE_CART));
                            sendBroadcast(new Intent(InteMainActivity.DIMISS_INTE_CART_GOODS_NUM));
                            ToastUtil.show(InfoActivity.this, "退出登录成功");
                            launch(MainActivity.class);
                        }
                    }
                    @Override
                    public void onErro(AppHeader header) {
                        LogUtil.e(header);
                        ToastUtil.show(InfoActivity.this, "退出登录失败，请重试");
                    }
                });
                break;
        }
    }

    /**
     * 修改头像弹窗
     */
    private void showPhotoView() {
        View popupView = LayoutInflater.from(InfoActivity.this).inflate(R.layout.dialog_head_portrait, null);
        View rootView = findViewById(R.id.root_main);
        TextView tvPhoto = (TextView) popupView.findViewById(R.id.tv_photo);
        TextView tvPhotoAlbum = (TextView) popupView.findViewById(R.id.tv_photo_album);
        TextView tvCancel = (TextView) popupView.findViewById(R.id.tv_cancel);
        final PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setAnimationStyle(R.style.dialog_animation);
        setBackgroundAlpha(0.5f);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(rootView, Gravity.BOTTOM | Gravity.START, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                // popupWindow隐藏时恢复屏幕正常透明度
                setBackgroundAlpha(1.0f);
            }
        });
        tvPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                tempUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "image.jpg"));
                // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                startActivityForResult(openCameraIntent, TAKE_PICTURE);
            }
        });
        tvPhotoAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
                openAlbumIntent.setType("image/*");
                startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TAKE_PICTURE:
                    startPhotoZoom(tempUri); // 开始对图片进行裁剪处理
                    break;
                case CHOOSE_PICTURE:
                    startPhotoZoom(data.getData()); // 开始对图片进行裁剪处理
                    break;
                case CROP_SMALL_PICTURE:
                    if (data != null) {
                        setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
                    }
                    break;
            }
        }
    }

    /**
     * 裁剪图片方法实现
     * @param uri
     */
    private void startPhotoZoom(Uri uri) {
        if (uri != null) {
            tempUri = uri;
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(uri, "image/*");
            // 设置裁剪
            intent.putExtra("crop", "true");
            // aspectX aspectY 是宽高的比例
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            // outputX outputY 是裁剪图片宽高
            intent.putExtra("outputX", 150);
            intent.putExtra("outputY", 150);
            intent.putExtra("return-data", true);
            startActivityForResult(intent, CROP_SMALL_PICTURE);
        }
    }

    /**
     * 保存裁剪之后的图片数据
     * @param data
     */
    private void setImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            photo = ImageUtils.toRoundBitmap(photo, tempUri);
            infoImg.setImageBitmap(photo);
            uploadHeadPic(photo);
        }
    }

    /**
     * 上传头像
     * @param bitmap
     */
    private void uploadHeadPic(Bitmap bitmap) {
        String imagePath = ImageUtils.savePhoto(bitmap,Environment.getExternalStorageDirectory().getAbsolutePath(),String.valueOf(System.currentTimeMillis()));
        LogUtil.e("imagePath==============="+imagePath);
        if (imagePath!=null) {
            //TODO 上传头像
        }
    }

    /**
     * 修改性别
     */
    private void changeSex() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(InfoActivity.this);
        builder.setTitle("修改性别");
        final int defaultIndex;
        if (StringUtils.isEmpty(info.getGender())) {
            defaultIndex = -1;
            selectGenderIndex = defaultIndex;
        } else if (StringUtils.equals("M", info.getGender())) {
            defaultIndex = 0;
            selectGenderIndex = defaultIndex;
        } else {
            defaultIndex = 1;
            selectGenderIndex = defaultIndex;
        }
        String[] genders = {"男", "女"};

        builder.setSingleChoiceItems(genders, defaultIndex, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectGenderIndex = which;
                String gender;
                if (0 == selectGenderIndex) {
                    gender = "M";
                } else if (1 == selectGenderIndex) {
                    gender = "F";
                } else {
                    ToastUtil.show(InfoActivity.this, "选择异常");
                    LogUtil.e("性别选择异常");
                    return;
                }
                Staff006Req req = new Staff006Req();
                req.setGender(gender);
                getJsonService().requestStaff006(InfoActivity.this, req, true, new JsonService.CallBack<Staff006Resp>() {
                    @Override
                    public void oncallback(Staff006Resp staff006Resp) {
                        if (staff006Resp.isFlag()) {
                            ToastUtil.show(InfoActivity.this, "修改性别成功");
                            alertDialog.dismiss();
                            updateUI();
                        } else {
                            ToastUtil.show(InfoActivity.this, "修改性别失败");
                            alertDialog.dismiss();
                        }
                    }

                    @Override
                    public void onErro(AppHeader header) {
                        ToastUtil.show(InfoActivity.this, "修改性别失败");
                        alertDialog.dismiss();
                    }
                });
            }
        });
        builder.setCancelable(true);
        alertDialog = builder.show();
    }

    private void updateInfo() {
        infoLogout.setVisibility(View.VISIBLE);
        GlideUtil.loadImg(this, info.getCustPic(), infoImg);
        infoName.setText(info.getNickname());
        infoAccount.setText(info.getStaffCode());
        if (StringUtil.equals("M", info.getGender())) {
            infoSex.setText("男");
        } else if (StringUtil.equals("F", info.getGender())) {
            infoSex.setText("女");
        } else {
            infoSex.setText("请选择");
        }
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            infoBirth.setText(df.format(info.getCustBirthday().getTime()));
        } catch (Exception e) {
            infoBirth.setText("");
        }
    }

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(millseconds);
        String birthDay = sf.format(date);
        Staff006Req req = new Staff006Req();
        req.setCustBirthday(birthDay);
        getJsonService().requestStaff006(this, req, true, new JsonService.CallBack<Staff006Resp>() {
            @Override
            public void oncallback(Staff006Resp staff006Resp) {
                if (staff006Resp.isFlag()) {
                    ToastUtil.show(InfoActivity.this, "修改成功");
                    updateUI();
                } else {
                    ToastUtil.show(InfoActivity.this, "修改失败");
                }
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            launch(MainActivity.class);
            onBackPressed();
            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
