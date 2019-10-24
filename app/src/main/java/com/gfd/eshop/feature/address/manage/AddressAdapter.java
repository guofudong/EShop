package com.gfd.eshop.feature.address.manage;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.gfd.eshop.R;
import com.gfd.eshop.network.entity.Address;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 收货地址Adapter
 */
public abstract class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {

    private final List<Address> mAddressList = new ArrayList<>();

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_address, parent, false);
        return new ViewHolder(view);
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mAddressList.get(position));
    }

    @Override public int getItemCount() {
        return mAddressList.size();
    }

    public void reset(@Nullable List<Address> addresses) {
        mAddressList.clear();
        if (addresses != null) mAddressList.addAll(addresses);
        notifyDataSetChanged();
    }

    protected abstract void onSetDefault(Address address);

    protected abstract void onDelete(Address address);

    protected abstract void onEdit(Address address);

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_address_info1) TextView tvInfo1;
        @BindView(R.id.text_address_info2) TextView tvInfo2;
        @BindView(R.id.text_set_default) TextView tvSetDefault;

        private Address mAddress;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Address address) {
            mAddress = address;
            tvSetDefault.setSelected(mAddress.isDefault());
            tvInfo1.setText(String.format("%s (%s)", mAddress.getConsignee(), mAddress.getProvinceName()));
            tvInfo2.setText(String.format("%s - %s - %s", mAddress.getCityName(), mAddress.getDistrictName(), mAddress.getAddress()));
        }

        @OnClick({R.id.text_set_default, R.id.text_edit, R.id.text_delete})
        void onClick(View view) {
            switch (view.getId()) {
                case R.id.text_set_default:
                    if (tvSetDefault.isSelected()) return;
                    onSetDefault(mAddress);
                    break;
                case R.id.text_edit:
                    onEdit(mAddress);
                    break;
                case R.id.text_delete:
                    onDelete(mAddress);
                    break;
                default:
                    throw new UnsupportedOperationException();
            }
        }
    }

}
