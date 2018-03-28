package jts.subj.kjh.jts_prj.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import jts.subj.kjh.jts_prj.Datas.DDayData;
import jts.subj.kjh.jts_prj.Datas.VIewHolderDataBinding;
import jts.subj.kjh.jts_prj.R;

/**
 * Created by kinjunghyun on 2018-03-27.
 */

public class MainRecyclerViewAdapter extends RecyclerView.Adapter<VIewHolderDataBinding> {

    List<DDayData> dDayDatas;
    Context context;

    public MainRecyclerViewAdapter(Context context, List<DDayData> dDayDatas) {
        this.context = context;
        this.dDayDatas = dDayDatas;
    }

    @Override
    public VIewHolderDataBinding onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.day_list_cover, parent, false);
        return new VIewHolderDataBinding(v);
    }

    @Override
    public void onBindViewHolder(final VIewHolderDataBinding holder, int position) {
        Picasso.with(context).load(dDayDatas.get(position).getCover_url()).into(holder.dlcb.background);
        holder.dlcb.termDay.setText("D" + compareDate(dDayDatas.get(position).getDate()));
        holder.dlcb.title.setText(dDayDatas.get(position).getTitle());
        holder.dlcb.date.setText(dDayDatas.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return dDayDatas.size();
    }


    public String compareDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        Date day1 = null;
        Date day2 = null;
        String result = "";
        try {
            day1 = format.parse(returnDate());
            day2 = format.parse(date);
            long a = day1.getDate() - day2.getDate();
            int compare = day1.compareTo(day2);

            if (a > 0) {
                result = String.valueOf("+" + a);
            } else if (a < 0) {
                result = String.valueOf(a);
            } else if (a == 0) {
                result = "-DAY";
            }
        } catch (Exception e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        }
        return result + "";
    }

    public String returnDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        String formattedDate = sdf.format(c.getTime());
        return formattedDate;
    }


}
