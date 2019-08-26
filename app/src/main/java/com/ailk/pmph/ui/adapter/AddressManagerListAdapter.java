package com.ailk.pmph.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.ai.ecp.app.resp.CustAddrResDTO;
import com.ailk.pmph.R;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.adapter
 * 作者: Chrizz
 * 时间: 2016/4/3 21:29
 */
public class AddressManagerListAdapter extends BaseAdapter {

    private List<CustAddrResDTO> mList;
    private Context mContext;
    private Long addrId;
    private HashMap<String,Boolean> states = new HashMap<>();
    private CheckDefaultInterface checkDefaultInterface;
    private EditAddressInterface editAddressInterface;
    private DeleteAddressInterface deleteAddressInterface;

    public AddressManagerListAdapter(Context context, List<CustAddrResDTO> list) {
        this.mContext = context;
        this.mList = list;
    }

    public void setCheckDefaultInterface(CheckDefaultInterface checkDefaultInterface) {
        this.checkDefaultInterface = checkDefaultInterface;
    }

    public void setEditAddressInterface(EditAddressInterface editAddressInterface) {
        this.editAddressInterface = editAddressInterface;
    }

    public void setDeleteAddressInterface(DeleteAddressInterface deleteAddressInterface) {
        this.deleteAddressInterface = deleteAddressInterface;
    }

    public void deleteItem(CustAddrResDTO address){
        mList.remove(address);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public CustAddrResDTO getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        @BindView(R.id.tv_contact_name)   TextView tvContactName;
        @BindView(R.id.tv_contact_phone)  TextView tvContactPhone;
        @BindView(R.id.tv_address)        TextView tv_address;
        @BindView(R.id.rb_check_default)  RadioButton rbCheckDefault;
        @BindView(R.id.tv_default_set)    TextView tvDefault;
        @BindView(R.id.tv_edit_address)   TextView tvEdit;
        @BindView(R.id.tv_delete_address) TextView tvDelete;
        @BindView(R.id.ll_address_info)   LinearLayout llAddressInfo;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_address_manager, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final CustAddrResDTO addrResDTO = mList.get(position);
        holder.tvContactName.setText(addrResDTO.getContactName());
        holder.tvContactPhone.setText(addrResDTO.getContactPhone());
        holder.tv_address.setText(addrResDTO.getPccName() + addrResDTO.getChnlAddress());

        if (StringUtils.equals("1", addrResDTO.getUsingFlag())) {
            holder.rbCheckDefault.setChecked(true);
            holder.tvDefault.setText("默认地址");
        } else {
            holder.rbCheckDefault.setChecked(false);
            holder.tvDefault.setText("设为默认");
        }

        holder.rbCheckDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDefaultInterface.setDefault(holder.rbCheckDefault, holder.tvDefault,
                        addrResDTO, holder.rbCheckDefault.isChecked());
            }
        });
//        holder.llAddressInfo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                checkDefaultInterface.setDefault(holder.rbCheckDefault, holder.tvDefault,
//                        addrResDTO, holder.rbCheckDefault.isChecked());
//            }
//        });
        holder.tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editAddressInterface.editAddress(addrResDTO);
            }
        });

        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAddressInterface.deleteAddress(addrResDTO);
            }
        });
        return convertView;
    }

    public interface CheckDefaultInterface{
        void setDefault(RadioButton check, TextView text, CustAddrResDTO addrResDTO, boolean isChecked);
    }


    public interface EditAddressInterface{
        void editAddress(CustAddrResDTO custAddrResDTO);
    }

    public interface DeleteAddressInterface{
        void deleteAddress(CustAddrResDTO custAddrResDTO);
    }

}
