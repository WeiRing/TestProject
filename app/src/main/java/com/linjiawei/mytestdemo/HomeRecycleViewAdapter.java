package com.linjiawei.mytestdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by LinJiawei on 2016/2/24.
 * Blog:http://blog.csdn.net/leejizhou
 * QQ:911926881
 * 说明：
 * 头部及尾部的条目可以直接通过修改mHeaderCount、mFooterCount值来设定...
 */
public class HomeRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //item类型
    public static final int ITEM_TYPE_HEADER = 0;
    public static final int ITEM_TYPE_CONTENT = 1;
    public static final int ITEM_TYPE_FOOTER = 2;

    private LayoutInflater mLayoutInflater;

    public static int mHeaderCount=1;//头部View个数
    public static int mFooterCount =1;//底部View个数

    private Context mContext;
    private List<ActivityParameter> dates;

    public HomeRecycleViewAdapter(Context context, List<ActivityParameter> dates) {
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
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int position = (int) v.getTag();
//                    Toast.makeText(mContext, position+": "+dates.get(position - mHeaderCount), Toast.LENGTH_SHORT).show();
//                }
//            });
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
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
            headerViewHolder.headTextView.setText("综合案例框架\nintegrated case framework");
//            if (dates.size()>0)
//                headerViewHolder.headTextView.setText(dates.get(position).getName());//头部名称
        } else if (holder instanceof ContentViewHolder) {
            ContentViewHolder contentHolder = (ContentViewHolder) holder;
            contentHolder.textView.setText(dates.get(position - mHeaderCount).getName());
        } else if (holder instanceof FooterViewHolder) {
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            footerViewHolder.footerTextView.setText("列表底部\nAt the bottom of the list");
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
        private TextView headTextView;
        public HeaderViewHolder(View itemView) {
            super(itemView);
            headTextView = (TextView) itemView.findViewById(R.id.headText);
        }
    }
    //底部 ViewHolder
    public static class FooterViewHolder extends RecyclerView.ViewHolder {
        private TextView footerTextView;
        public FooterViewHolder(View itemView) {
            super(itemView);
            footerTextView = (TextView) itemView.findViewById(R.id.footerText);
        }
    }



}
