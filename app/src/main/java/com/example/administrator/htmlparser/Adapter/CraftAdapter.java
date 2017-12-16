package com.example.administrator.htmlparser.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;

import com.example.administrator.htmlparser.R;

/**
 * Created by Administrator on 2017/7/26.
 */

public class CraftAdapter extends BaseAdapter {
    Context context;
    String [] message ;
    public CraftAdapter(Context context){
        this.context=context;
    }
    public void setMessage(String [] message ){
        this.message=message;
    }
    @Override
    public int getCount() {
        return message==null?0:message.length;
    }

    @Override
    public Object getItem(int position) {
        return message[position];
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
            convertView= LayoutInflater.from(context).inflate(R.layout.list_craft,parent,false);
            hondler.webView= (WebView) convertView.findViewById(R.id.list_craft);
            convertView.setTag(hondler);
        }else {
            hondler= (Hondler) convertView.getTag();
        }
        hondler.webView.loadDataWithBaseURL(null, message[position], "text/html", "utf-8", null);
        return convertView;
    }
    public class Hondler{
        WebView webView;
    }
}
