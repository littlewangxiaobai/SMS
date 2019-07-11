package com.whx.sms.ui.adapter;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.whx.sms.R;
import com.whx.sms.data.SmsBean;
import com.whx.sms.util.TimeFormatUtils;

import java.util.ArrayList;

public class SmsRecyclerAdapter extends RecyclerView.Adapter {

    private ArrayList<SmsBean> smsBeanList;

    public  SmsRecyclerAdapter(ArrayList<SmsBean> smsBeanList){
        this.smsBeanList = smsBeanList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sms_recorder, parent, false);
        return new SMSViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        Log.e("whx", "onBindViewHolder: " +i);
        SmsBean data = smsBeanList.get(i);
        ((SMSViewHolder)viewHolder).tv_id.setText(data.getId());
        ((SMSViewHolder)viewHolder).tv_sender.setText(data.getSender());
        ((SMSViewHolder)viewHolder).tv_content.setText(data.getContent());
        ((SMSViewHolder)viewHolder).tv_data.setText(TimeFormatUtils.ms2DateOnlyDay1(Long.parseLong(data.getData())) );

    }

    @Override
    public int getItemCount() {
        Log.e("whx", "getItemCount: " + (this.smsBeanList == null ? 0 : this.smsBeanList.size()));
        return this.smsBeanList == null ? 0 : this.smsBeanList.size();
    }

    public void setSmsData(ArrayList<SmsBean> smsList) {
        smsBeanList = smsList;
    }

    private class SMSViewHolder extends RecyclerView.ViewHolder {

        TextView tv_id ;
        TextView tv_sender ;
        TextView tv_data ;
        TextView tv_content ;

        public SMSViewHolder(View layout) {
            super(layout);

            tv_id = layout.findViewById(R.id.tv_id);
            tv_sender = layout.findViewById(R.id.tv_sender);
            tv_data = layout.findViewById(R.id.tv_data);
            tv_content = layout.findViewById(R.id.tv_content);
        }
    }
}
