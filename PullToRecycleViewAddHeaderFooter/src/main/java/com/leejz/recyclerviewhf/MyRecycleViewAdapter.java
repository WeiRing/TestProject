package com.leejz.recyclerviewhf;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by LinJiawei on 2016/2/24.
 * Blog:http://blog.csdn.net/leejizhou
 * QQ:911926881
 * 说明：
 * 头部及尾部的条目可以直接通过修改mHeaderCount、mFooterCount值来设定...
 */
public class MyRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //item类型
    public static final int ITEM_TYPE_HEADER = 0;
    public static final int ITEM_TYPE_CONTENT = 1;
    public static final int ITEM_TYPE_FOOTER = 2;

    private LayoutInflater mLayoutInflater;
    private int mHeaderCount=1;//头部View个数
    private int mFooterCount =1;//底部View个数

    private Context mContext;
    private  List<String> dates;

    public MyRecycleViewAdapter(Context context,List<String> dates) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        this.dates = dates;
    }

    //内容长度
    public int getContentItemCount(){
        return dates.size();
    }
    //判断当前item是否是HeadView
    public boolean isHeaderView(int position) {
        return mHeaderCount != 0 && position < mHeaderCount;
    }
    //判断当前item是否是FooterView
    public boolean isFooterView(int position) {
        return mFooterCount != 0 && position >= (mHeaderCount + getContentItemCount());
    }


    /**
     * 添加头部跟尾部只是通过控制第一跟最后一个位置，进行itemView类型的变更而已.
     * 唯一要注意的就是要创建多种类型的Holder...
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_CONTENT) {
            View itemView = mLayoutInflater.inflate(R.layout.rv_item, parent, false);
            ContentViewHolder headHolder = new ContentViewHolder(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag();
                    Toast.makeText(mContext, position+": "+dates.get(position - mHeaderCount), Toast.LENGTH_SHORT).show();
                }
            });
            return headHolder;
        } else if (viewType ==ITEM_TYPE_HEADER) {
            return new HeaderViewHolder(mLayoutInflater.inflate(R.layout.rv_header, parent, false));
        }else if (viewType == ITEM_TYPE_FOOTER) {
            return new FooterViewHolder(mLayoutInflater.inflate(R.layout.rv_footer, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {

        } else if (holder instanceof ContentViewHolder) {
            ContentViewHolder contentHolder = (ContentViewHolder) holder;
            contentHolder.textView.setText(dates.get(position - mHeaderCount));
            contentHolder.itemView.setTag(position);
        } else if (holder instanceof FooterViewHolder) {

        }
    }

    //判断当前item类型
    @Override
    public int getItemViewType(int position) {
        int dataItemCount = getContentItemCount();
        if (isHeaderView(position)) {
            //头部View
            return ITEM_TYPE_HEADER;
        } else if (isFooterView(position)) {
            //底部View
            return ITEM_TYPE_FOOTER;
        } else {
            //内容View
            return ITEM_TYPE_CONTENT;
        }
    }

    @Override
    public int getItemCount() {
       return mHeaderCount + getContentItemCount() + mFooterCount;
    }


    //内容 ViewHolder
    public static class ContentViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        public ContentViewHolder(View itemView) {
            super(itemView);
            textView=(TextView)itemView.findViewById(R.id.tv_item_text);
        }
    }
    //头部 ViewHolder
    public static class HeaderViewHolder extends RecyclerView.ViewHolder {

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }
    //底部 ViewHolder
    public static class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }



}
