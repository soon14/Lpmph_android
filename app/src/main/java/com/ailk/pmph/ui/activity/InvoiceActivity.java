package com.ailk.pmph.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ai.ecp.app.resp.Ord00201Resp;
import com.ailk.pmph.BuildConfig;
import com.ailk.pmph.R;
import com.ailk.pmph.base.BaseActivity;
import com.ailk.pmph.ui.adapter.InvoiceAdapter;
import com.ailk.pmph.ui.view.CustomListView;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.DialogAnotherUtil;
import com.ailk.pmph.utils.DialogUtil;
import com.ailk.pmph.utils.ToastUtil;

import org.apache.commons.lang.StringUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 类注释:发票信息
 * 项目名:PMPH
 * 包名:com.ailk.pmph.ui.activity
 * 作者: Chrizz
 * 时间: 2016/3/29 11:17
 */
public class InvoiceActivity extends BaseActivity implements View.OnClickListener, InvoiceAdapter.CheckDetailInterface {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_paper)
    TextView tvPaper;
    @BindView(R.id.tv_electron)
    TextView tvElectron;
    @BindView(R.id.et_head_content)
    EditText etHeadContent;
    @BindView(R.id.tv_yes)
    TextView tvYes;
    @BindView(R.id.tv_no)
    TextView tvNo;
    @BindView(R.id.lv_bill_content)
    CustomListView lvBillContent;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;


    @BindView(R.id.radio_company)
    RadioButton radioCompany;
    @BindView(R.id.radio_person)
    RadioButton radioPerson;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    @BindView(R.id.et_taxpayer)
    EditText etTaxpayer;
    @BindView(R.id.layout_taxpayer)
    LinearLayout layoutTaxpayer;

    private String billTypeText;//发票类型文字
    private String billTitle;//发票抬头
    private String detailFlag;//是否附加明细 0为否 1为是
    private String billContent;//发票内容
    private String mTaxpayerNumber;//纳税人识别号
    private Ord00201Resp shop;
    private Bundle extras;
    private List<String> invoiceConList;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_bill;
    }

    @Override
    public void initView() {

        initRadioGroup();


    }

    private void initRadioGroup() {

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.radio_company:
                        layoutTaxpayer.setVisibility(View.VISIBLE);
                        etHeadContent.setHint("请输入单位名称");
                        etHeadContent.setText("");
                        etHeadContent.setFocusable(true);
                        etHeadContent.setFocusableInTouchMode(true);
                        etHeadContent.setEnabled(true);
                        break;
                    case R.id.radio_person:
                        etHeadContent.setHint("个人");
                        etHeadContent.setText("个人");
                        etHeadContent.setFocusable(false);
                        etHeadContent.setFocusableInTouchMode(false);
                        etHeadContent.setEnabled(false);
                        layoutTaxpayer.setVisibility(View.GONE);
                        mTaxpayerNumber = "";
                        break;
//                    default:
//                        layoutTaxpayer.setVisibility(View.VISIBLE);
//                        break;
                }
                etTaxpayer.setText(mTaxpayerNumber);
            }
        });


    }

    private void updateTaxpayernumber() {

        if (TextUtils.isEmpty(mTaxpayerNumber) && TextUtils.isEmpty(billTitle)) {
            //第一次进入，且
            //纳税人识别号为空
            //默认选择为公司
            radioGroup.check(R.id.radio_company);
//            radioPerson.setChecked(true);
        } else if (TextUtils.isEmpty(mTaxpayerNumber) && !TextUtils.isEmpty(billTitle)) {
            //非第一次进入，且之前选择为个人

            radioGroup.check(R.id.radio_person);
        } else if (!TextUtils.isEmpty(mTaxpayerNumber) && !TextUtils.isEmpty(billTitle)) {
            //都不为空，则非第一次进入，之前选择为公司
            radioGroup.check(R.id.radio_company);
        } else {
            //识别号非空，内容不为空
            //理论上不存在，默认为公司
            radioGroup.check(R.id.radio_company);

        }
    }

    @Override
    public void initData() {
        extras = new Bundle();
        Bundle bundle = getIntent().getExtras();
        shop = (Ord00201Resp) bundle.get("shop");
        if (shop != null) {
            billTypeText = shop.getBillTypeText();
            billTitle = shop.getBillTitle();
            detailFlag = shop.getDetailFlag();
            billContent = shop.getBillContent();
            mTaxpayerNumber = shop.getTaxpayerNo();
//            if (!TextUtils.isEmpty(mTaxpayerNumber)) {
//                updateTaxpayernumber();
//
//            }
        }

        updateTaxpayernumber();

        invoiceConList = (List<String>) bundle.get("invoiceConList");
        etHeadContent.setText(billTitle);
        if (billTypeText != null) {
            if (StringUtils.equals(billTypeText, "普通发票")) {
                choosePaper(tvPaper, tvElectron);
            } else if (StringUtils.equals(billTypeText, "电子发票")) {
                chooseElectron(tvPaper, tvElectron);
            }
        } else {
            choosePaper(tvPaper, tvElectron);
        }

        if (detailFlag != null) {
            if (StringUtils.equals(detailFlag, "1")) {
                chooseYes(tvYes, tvNo);
            } else if (StringUtils.equals(detailFlag, "0")) {
                chooseNo(tvYes, tvNo);
            }
        } else {
            chooseNo(tvYes, tvNo);
        }

        if (StringUtils.equals(tvPaper.getText().toString(), "普通发票")) {
            choosePaper(tvPaper, tvElectron);
        } else if (StringUtils.equals(tvElectron.getText().toString(), "电子发票")) {
            chooseElectron(tvPaper, tvElectron);
        }

        InvoiceAdapter adapter = new InvoiceAdapter(this, invoiceConList);
        adapter.setCheckDetailInterface(this);
        lvBillContent.setDivider(null);
        lvBillContent.setAdapter(adapter);
        if (StringUtils.isEmpty(billContent)) {
            billContent = invoiceConList.get(0);
            adapter.setSelectedItem(0);
        } else {
            for (int i = 0; i < invoiceConList.size(); i++) {
                if (StringUtils.equals(billContent, invoiceConList.get(i))) {
                    adapter.setSelectedItem(i);
                }
            }
        }
    }

    private void choosePaper(TextView tvPaper, TextView tvElectron) {
        tvPaper.setBackgroundResource(R.drawable.shape_round_delbutton);
        tvPaper.setText("普通发票");
        if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
            tvPaper.setTextColor(ContextCompat.getColor(this, R.color.orange_ff6a00));
        } else {
            tvPaper.setTextColor(ContextCompat.getColor(this, R.color.red));
        }
        tvElectron.setBackgroundResource(R.drawable.shape_round_textview);
        tvElectron.setText("电子发票");
        tvElectron.setTextColor(ContextCompat.getColor(this, R.color.gray_312B2D));
        shop.setBillTypeText(StringUtils.remove(tvPaper.getText().toString(), "√"));
    }

    private void chooseElectron(TextView tvPaper, TextView tvElectron) {
        tvPaper.setBackgroundResource(R.drawable.shape_round_textview);
        tvPaper.setText("普通发票");
        tvPaper.setTextColor(ContextCompat.getColor(this, R.color.gray_312B2D));
        tvElectron.setBackgroundResource(R.drawable.shape_round_delbutton);
        tvElectron.setText("电子发票");
        if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
            tvElectron.setTextColor(ContextCompat.getColor(this, R.color.orange_ff6a00));
        } else {
            tvElectron.setTextColor(ContextCompat.getColor(this, R.color.red));
        }
        shop.setBillTypeText(StringUtils.remove(tvElectron.getText().toString(), "√"));
    }

    private void chooseYes(TextView tvYes, TextView tvNo) {
        tvYes.setBackgroundResource(R.drawable.shape_round_delbutton);
        tvYes.setText("是");
        if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
            tvYes.setTextColor(ContextCompat.getColor(this, R.color.orange_ff6a00));
        } else {
            tvYes.setTextColor(ContextCompat.getColor(this, R.color.red));
        }
        tvNo.setBackgroundResource(R.drawable.shape_round_textview);
        tvNo.setText("否");
        tvNo.setTextColor(ContextCompat.getColor(this, R.color.gray_312B2D));
        shop.setDetailFlag("1");
    }

    private void chooseNo(TextView tvYes, TextView tvNo) {
        tvYes.setBackgroundResource(R.drawable.shape_round_textview);
        tvYes.setText("是");
        tvYes.setTextColor(ContextCompat.getColor(this, R.color.gray_312B2D));
        tvNo.setBackgroundResource(R.drawable.shape_round_delbutton);
        tvNo.setText("否");
        if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
            tvNo.setTextColor(ContextCompat.getColor(this, R.color.orange_ff6a00));
        } else {
            tvNo.setTextColor(ContextCompat.getColor(this, R.color.red));
        }
        shop.setDetailFlag("0");
    }

    @Override
    public void checkDetail(String detail, boolean isChecked) {
        if (isChecked) {
            shop.setBillContent(detail);
        }
    }

    @Override
    @OnClick({R.id.iv_back, R.id.tv_paper, R.id.tv_electron, R.id.tv_yes, R.id.tv_no, R.id.btn_confirm})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                if (StringUtils.isNotEmpty(etHeadContent.getText().toString())
                        && StringUtils.isNotEmpty(billTitle)
                        && StringUtils.isNotEmpty(billContent)
                        && StringUtils.isNotEmpty(billTypeText)
                        && StringUtils.isNotEmpty(detailFlag)) {
                    extras.putSerializable("shop", shop);
                    Intent intent = new Intent(this, OrderConfirmActivity.class);
                    intent.putExtras(extras);
                    setResult(1001, intent);
                    onBackPressed();
                } else {
                    DialogAnotherUtil.showCustomAlertDialogWithMessage(this, null,
                            "确认放弃填写发票信息吗？", "确定", "取消",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    DialogUtil.dismissDialog();
                                    shop.setBillType("2");
                                    shop.setBillTypeText("");
                                    shop.setDetailFlag("0");
                                    shop.setBillTitle("");
                                    shop.setBillContent("");
                                    extras.putSerializable("shop", shop);
                                    Intent intent = new Intent(InvoiceActivity.this, OrderConfirmActivity.class);
                                    intent.putExtras(extras);
                                    setResult(1001, intent);
                                    onBackPressed();
                                }
                            },
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    DialogUtil.dismissDialog();
                                }
                            });
                }
                break;
            case R.id.tv_paper:
                choosePaper(tvPaper, tvElectron);
                break;
            case R.id.tv_electron:
                chooseElectron(tvPaper, tvElectron);
                break;
            case R.id.tv_yes:
                chooseYes(tvYes, tvNo);
                break;
            case R.id.tv_no:
                chooseNo(tvYes, tvNo);
                break;
            case R.id.btn_confirm:
                if (StringUtils.isEmpty(etHeadContent.getText().toString())) {
                    ToastUtil.showCenter(this, "请填写发票抬头内容");
                    return;
                }

                //纳税人识别号校验
                if (radioCompany.isChecked() &&
                        (TextUtils.isEmpty(etTaxpayer.getText().toString()) || TextUtils.isEmpty(etTaxpayer.getText().toString().trim()))
                        ) {
                    ToastUtil.show(InvoiceActivity.this, "纳税人识别号不能为空");
                    return;
                }
                shop.setBillType("0");
                shop.setBillTitle(etHeadContent.getText().toString());
                if (invoiceConList.size() == 1) {
                    shop.setBillContent(invoiceConList.get(0));
                }
                //纳税人识别号
//                if (!TextUtils.isEmpty(etTaxpayer.getText().toString())) {
                shop.setTaxpayerNo(etTaxpayer.getText().toString());
                mTaxpayerNumber = etTaxpayer.getText().toString();

//                }
                extras.putSerializable("shop", shop);
                Intent intent = new Intent(this, OrderConfirmActivity.class);
                intent.putExtras(extras);
                setResult(1001, intent);
                onBackPressed();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (StringUtils.isNotEmpty(etHeadContent.getText().toString())
                    && StringUtils.isNotEmpty(billTitle)
                    && StringUtils.isNotEmpty(billContent)
                    && StringUtils.isNotEmpty(billTypeText)
                    && StringUtils.isNotEmpty(detailFlag)) {
                extras.putSerializable("shop", shop);
                Intent intent = new Intent(InvoiceActivity.this, OrderConfirmActivity.class);
                intent.putExtras(extras);
                setResult(1001, intent);
                onBackPressed();
            } else {
                DialogAnotherUtil.showCustomAlertDialogWithMessage(this, null,
                        "确认放弃填写发票信息吗？", "确定", "取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DialogUtil.dismissDialog();
                                shop.setBillType("2");
                                shop.setBillTypeText("");
                                shop.setDetailFlag("0");
                                shop.setBillTitle("");
                                shop.setBillContent("");
                                extras.putSerializable("shop", shop);
                                Intent intent = new Intent(InvoiceActivity.this, OrderConfirmActivity.class);
                                intent.putExtras(extras);
                                setResult(1001, intent);
                                onBackPressed();
                            }
                        },
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DialogUtil.dismissDialog();
                            }
                        });
            }
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
