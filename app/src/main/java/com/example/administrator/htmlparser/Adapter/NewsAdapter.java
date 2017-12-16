package com.example.administrator.htmlparser.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.htmlparser.R;
import com.example.administrator.htmlparser.entity.Data;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/24.
 */

public class NewsAdapter extends BaseAdapter {
    Context context;
    ArrayList<Data> list;
    public  NewsAdapter(Context context){
        this.context=context;
    }

    public void setList(ArrayList<Data> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Hondler hondler=null;
        if (null==convertView){
            hondler=new Hondler();
            convertView= LayoutInflater.from(context).inflate(R.layout.list,parent,false);
            hondler.textView= (TextView) convertView.findViewById(R.id.list_title);
            convertView.setTag(hondler);
        }else {
            hondler= (Hondler) convertView.getTag();
        }
        hondler.textView.setText(list.get(position).getTitle());
        return convertView;
    }
    public class Hondler{
        TextView textView;
    }
}
