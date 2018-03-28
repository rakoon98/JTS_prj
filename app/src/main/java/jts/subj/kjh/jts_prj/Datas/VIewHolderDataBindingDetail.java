package jts.subj.kjh.jts_prj.Datas;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import jts.subj.kjh.jts_prj.databinding.DayListCoverBinding;
import jts.subj.kjh.jts_prj.databinding.DetailListItemBinding;

/**
 * Created by kinjunghyun on 2018-03-27.
 */

public class VIewHolderDataBindingDetail extends RecyclerView.ViewHolder {
    public DetailListItemBinding dlcb2;

    public VIewHolderDataBindingDetail(View itemView) {
        super(itemView);
        dlcb2 = DataBindingUtil.bind(itemView);
    }


}
