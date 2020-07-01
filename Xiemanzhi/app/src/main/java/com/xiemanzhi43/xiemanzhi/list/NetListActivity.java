package com.xiemanzhi43.xiemanzhi.list;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;




import com.google.gson.Gson;
import com.xiemanzhi43.xiemanzhi.HttpProxy;
import com.xiemanzhi43.xiemanzhi.NetInputUtils;
import com.xiemanzhi43.xiemanzhi.R;
import com.xiemanzhi43.xiemanzhi.SignActivity;
import com.xiemanzhi43.xiemanzhi.WebActivity;
import com.xiemanzhi43.xiemanzhi.bean.VideoInfo;
import com.xiemanzhi43.xiemanzhi.bean.VideoListResponse;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class NetListActivity extends AppCompatActivity {
    private static final String TAG = "NetListActivity";
    private VideoAdapter mAdapter;
    private Handler mHandler = new Handler();
    private List<VideoInfo> mDataList;
    private TextView mTextName;
    private TextView mSign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_list);

        ListView mListView = (ListView) findViewById(R.id.lv);
        View headLayout = buildListHeader();
        mTextName = (TextView) headLayout.findViewById(R.id.txt_name);
        mSign = (TextView) headLayout.findViewById(R.id.txt_sign);
        mListView.addHeaderView(headLayout);
        mDataList = new ArrayList<>();
        mAdapter = new VideoAdapter(mDataList,this);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    VideoInfo videoInfo = mDataList.get(position - 1);
                    Intent intent = new Intent(NetListActivity.this, WebActivity.class);
                    intent.putExtra(WebActivity.WEB_URL, videoInfo.getFilePath());
                    startActivity(intent);
                }
            }
        });
        initData();
    }
    private View buildListHeader() {
        View headLayout = LayoutInflater.from(this).inflate(R.layout.activity_main,null);
        TextView signTV = (TextView) headLayout.findViewById(R.id.txt_sign);
        signTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NetListActivity.this,"去设置签名",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(NetListActivity.this, SignActivity.class);
                startActivityForResult(intent,11);
            }
        });
        return headLayout;
    }

    private void initData() {
        String movieUrl = "http://ramedia.sinaapp.com/res/DouBanMovie2.json";
        HttpProxy.getInstance().load(movieUrl,new HttpProxy.NetInputCallback(){
            @Override
            public void onSuccess(InputStream inputStream){
                String respJson = null;
                try{
                    respJson = NetInputUtils.readString(inputStream);
                    Log.i(TAG,"----response json:\n" +respJson);
                    VideoListResponse videoListResponse = convertJsonToBean(respJson);
                    final   List<VideoInfo> list = videoListResponse.getList();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.setData(list);
                            mAdapter.notifyDataSetChanged();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        Intent intent = getIntent();
        mTextName.setText(intent.getStringExtra("loginName"));
    }
    private VideoListResponse convertJsonToBean(String json){
        Gson gson = new Gson();
        VideoListResponse response = gson.fromJson(json,VideoListResponse.class);
        return response;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 11: {
                if (RESULT_OK == resultCode) {
                    String sign = data.getStringExtra("mySign");
                    mSign.setText(sign);
                } else {
                    Toast.makeText(this, "您取消了设置", Toast.LENGTH_SHORT).show();
                }
            }
            break;
        }
    }
}

