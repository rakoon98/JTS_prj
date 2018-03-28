package jts.subj.kjh.jts_prj.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import jts.subj.kjh.jts_prj.Datas.DetailData;
import jts.subj.kjh.jts_prj.Datas.VIewHolderDataBindingDetail;
import jts.subj.kjh.jts_prj.R;

/**
 * Created by kinjunghyun on 2018-03-27.
 */

public class DetailRecyclerViewAdapter extends RecyclerView.Adapter<VIewHolderDataBindingDetail> {

    Context context;
    List<DetailData> detailDataList;

    public DetailRecyclerViewAdapter(Context context, List<DetailData> detailDataList){
        this.context = context;
        this.detailDataList = detailDataList;
    }

    @Override
    public VIewHolderDataBindingDetail onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.detail_list_item, parent, false);

        return new VIewHolderDataBindingDetail(v);
    }

    @Override
    public void onBindViewHolder(VIewHolderDataBindingDetail holder, int position) {
        holder.dlcb2.title.setText(detailDataList.get(position).getTitle());
        holder.dlcb2.date.setText(detailDataList.get(position).getDate());
        holder.dlcb2.termDate.setText(detailDataList.get(position).getTerm());
    }

    @Override
    public int getItemCount() {
        return detailDataList.size();
    }

}
