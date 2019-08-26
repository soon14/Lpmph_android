package com.ailk.pmph.ui.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ai.ecp.app.resp.Staff110Resp;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.JsonService;
import com.ailk.integral.activity.InteMainActivity;
import com.ailk.integral.activity.InteOrdersActivity;
import com.ailk.pmph.AppContext;
import com.ailk.pmph.BuildConfig;
import com.ailk.pmph.R;
import com.ailk.pmph.base.BaseFragment;
import com.ailk.pmph.ui.activity.LoginPmphActivity;
import com.ailk.pmph.ui.activity.PmphCollectionActivity;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.ImageUtils;
import com.ailk.pmph.utils.LogUtil;
import com.ailk.pmph.utils.MoneyUtils;
import com.ailk.pmph.ui.activity.AllOrdersActivity;
import com.ailk.pmph.ui.activity.CouponListActivity;
import com.ailk.pmph.ui.activity.InfoActivity;
import com.ailk.pmph.ui.activity.LoginActivity;
import com.ailk.pmph.ui.activity.MessageActivity;
import com.ailk.pmph.ui.activity.MyCollectionActivity;
import com.ailk.pmph.ui.activity.UnCommentActivity;
import com.ailk.pmph.ui.activity.UnPayActivity;
import com.ailk.pmph.ui.activity.UnReceiveActivity;
import com.ailk.pmph.ui.activity.UnSendActivity;
import com.ailk.pmph.utils.PrefUtility;
import com.ailk.pmph.ui.activity.WalletActivity;
import com.ailk.pmph.ui.activity.ScoreActivity;
import com.ailk.tool.GlideUtil;

import org.apache.commons.lang.StringUtils;

import java.io.File;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Project : PMPH
 * Created by 王可 on 16/3/9.
 * 个人中心Fragment
 */
public class PersonalCenterFragment extends BaseFragment {

    @BindView(R.id.iv_set)
    ImageView ivSet;
    @BindView(R.id.iv_message)
    ImageView ivMessage;
    @BindView(R.id.personalcenter_myinfo)
    LinearLayout personalcenterMyinfo;
    @BindView(R.id.ll_all_orders_layout)
    LinearLayout llAllOrdersLayout;

    @BindView(R.id.ll_inte_orders_layout)
    LinearLayout llInteOrderLayout;

    @BindView(R.id.personalcenter_img)
    CircleImageView personalcenterImg;
    @BindView(R.id.personalcenter_staffmsg)
    TextView personalcenterStaffmsg;
    @BindView(R.id.personalcenter_name)
    TextView personalcenterName;
    @BindView(R.id.personalcenter_login)
    Button personalcenterLogin;
    View loginLayout;
    View unloginLayout;
    @BindView(R.id.personalcenter_money)
    TextView personalcenterMoney;
    @BindView(R.id.center_collect)
    LinearLayout centerCollect;

    @BindView(R.id.ll_coupon_layout)
    LinearLayout llCouponLayout;
    @BindView(R.id.ll_score_layout)
    LinearLayout llScoreLayout;
    @BindView(R.id.ll_count_layout)
    LinearLayout llCountLayout;
    @BindView(R.id.ll_inte_mall_layout)
    LinearLayout llInteMallLayout;
    @BindView(R.id.iv_coupon_line)
    ImageView ivCouponLine;
    @BindView(R.id.tv_score_mall)
    TextView tvScoreMall;

    Staff110Resp info;

    //订单部分
    @BindView(R.id.un_pay_layout)
    RelativeLayout unPayLayout;
    @BindView(R.id.un_sent_layout)
    RelativeLayout unSentLayout;
    @BindView(R.id.un_received_layout)
    RelativeLayout unReceivedLayout;
    @BindView(R.id.un_comment_layout)
    RelativeLayout unCommentLayout;

    ImageView markUnpay;
    ImageView markUnsent;
    ImageView markUnreceived;
    ImageView markUncomment;

    private static final int CHOOSE_PICTURE = 0;//相册选择
    private static final int TAKE_PICTURE = 1;//拍照
    private static final int CROP_SMALL_PICTURE = 2;//裁剪图片
    private static Uri tempUri;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_personal_center;
    }

    @Override
    public void initData() {

    }

    public void initView(final View view) {
        loginLayout = view.findViewById(R.id.layout_login);
        unloginLayout = view.findViewById(R.id.layout_unlogin);
        markUnpay = (ImageView) view.findViewById(R.id.mark_unpay);
        markUnsent = (ImageView) view.findViewById(R.id.mark_unsent);
        markUnreceived = (ImageView) view.findViewById(R.id.mark_unreceived);
        markUncomment = (ImageView) view.findViewById(R.id.mark_uncomment);

        personalcenterImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showPhotoView(view);
            }
        });
        loginLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launch(InfoActivity.class);
            }
        });

        configView();
    }

    private void updateUI() {
        PrefUtility.put("staffCode", info.getStaffCode());
        personalcenterName.setText(info.getStaffCode());
        personalcenterStaffmsg.setText("会员等级：" + info.getCustLevelName());
        GlideUtil.loadImg(getActivity(), info.getCustPic(), personalcenterImg);
        personalcenterMoney.setText("我的余额： " + MoneyUtils.GoodListPrice(info.getAcctTotal()));
        if (0 == info.getOrderPayCnt()){
            setInvisible(markUnpay);
        }else {
            setVisible(markUnpay);
        }
        if (0 == info.getOrderSendCnt()){
            setInvisible(markUnsent);
        }else {
            setVisible(markUnsent);
        }
        if (0 == info.getOrderRecept()){
            setInvisible(markUnreceived);
        }else {
            setVisible(markUnreceived);
        }
        if (0 == info.getOrderCommentCnt()){
            setInvisible(markUncomment);
        }else {
            setVisible(markUncomment);
        }
    }

    private void configView() {
        if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
            tvScoreMall.setTextColor(ContextCompat.getColor(getActivity(), R.color.orange_ff6a00));
        } else {
            tvScoreMall.setTextColor(ContextCompat.getColor(getActivity(), R.color.red));
        }
        ivSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!AppContext.isLogin) {
                    showLoginDialog(getActivity());
                } else {
                    launch(InfoActivity.class);
                }
            }
        });
        ivMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!AppContext.isLogin) {
                    showLoginDialog(getActivity());
                } else {
                    launch(MessageActivity.class);
                }
            }
        });
        unPayLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!AppContext.isLogin) {
                    showLoginDialog(getActivity());
                } else {
                    launch(UnPayActivity.class);
                }
            }
        });
        unSentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!AppContext.isLogin) {
                    showLoginDialog(getActivity());
                } else {
                    launch(UnSendActivity.class);
                }
            }
        });
        unReceivedLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!AppContext.isLogin) {
                    showLoginDialog(getActivity());
                } else {
                    launch(UnReceiveActivity.class);
                }
            }
        });
        unCommentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!AppContext.isLogin) {
                    showLoginDialog(getActivity());
                } else {
                    launch(UnCommentActivity.class);
                }
            }
        });
        llAllOrdersLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!AppContext.isLogin) {
                    showLoginDialog(getActivity());
                } else {
                    launch(AllOrdersActivity.class);
                }
            }
        });
        llInteOrderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!AppContext.isLogin) {
                    showLoginDialog(getActivity());
                } else {
                    launch(InteOrdersActivity.class);
                }
            }
        });
        personalcenterMyinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!AppContext.isLogin) {
                    showLoginDialog(getActivity());
                } else {
                    launch(InfoActivity.class);
                }

            }
        });

        llScoreLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!AppContext.isLogin) {
                    showLoginDialog(getActivity());
                } else {
                    launch(ScoreActivity.class);
                }
            }
        });
        llCouponLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!AppContext.isLogin) {
                    showLoginDialog(getActivity());
                } else {
                    launch(CouponListActivity.class);
                }
            }
        });
        if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
            setGone(ivCouponLine,llCountLayout);
        } else {
            setVisible(llCountLayout);
            llCountLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!AppContext.isLogin) {
                        showLoginDialog(getActivity());
                    } else {
                        launch(WalletActivity.class);
                    }
                }
            });
        }
        centerCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
                    if (!AppContext.isLogin) {
                        showLoginDialog(getActivity());
                    } else {
                        launch(PmphCollectionActivity.class);
                    }
                } else {
                    if (!AppContext.isLogin) {
                        showLoginDialog(getActivity());
                    } else {
                        launch(MyCollectionActivity.class);
                    }
                }
            }
        });
        personalcenterLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launch(LoginPmphActivity.class);
//                launch(LoginActivity.class);
            }
        });
        llInteMallLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launch(InteMainActivity.class);
                getActivity().finish();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateLoginState();
    }

    /**
     * 修改头像弹窗
     */
    private void showPhotoView(View view) {
        View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_head_portrait, null);
        View rootView = view.findViewById(R.id.root_main);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
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
            personalcenterImg.setImageBitmap(photo);
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

    private void updateLoginState(){
        if (!AppContext.isLogin) {
            setVisible(unloginLayout);
            setGone(loginLayout);
            setInvisible(markUnpay, markUncomment, markUnsent, markUnreceived);
            personalcenterMoney.setText("我的余额： ¥0.00");
        } else {
            getJsonService().requestStaff110(getActivity(), true, new JsonService.CallBack<Staff110Resp>() {
                @Override
                public void oncallback(Staff110Resp staff110Resp) {
                    info = staff110Resp;
                    setGone(unloginLayout);
                    setVisible(loginLayout);
                    updateUI();
                }

                @Override
                public void onErro(AppHeader header) {

                }
            });
        }
    }

}
