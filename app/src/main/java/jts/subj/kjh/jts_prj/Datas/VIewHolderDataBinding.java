package jts.subj.kjh.jts_prj.Datas;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import jts.subj.kjh.jts_prj.R;
import jts.subj.kjh.jts_prj.databinding.DayListCoverBinding;

/**
 * Created by kinjunghyun on 2018-03-27.
 */

public class VIewHolderDataBinding extends RecyclerView.ViewHolder{
    public DayListCoverBinding dlcb;

    public VIewHolderDataBinding(View itemView) {
        super(itemView);
        dlcb = DataBindingUtil.bind(itemView);
    }


}
