package com.example.lindroidcode.recyclerrelated;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.lindroidcode.R;
import com.example.lindroidcode.recyclerrelated.decoration.HalfTransparentDecoration;
import com.example.lindroidcode.recyclerrelated.decoration.LeftAndRightTagDecoration;
import com.example.lindroidcode.recyclerrelated.decoration.SimpleDividerDecoration;
import com.example.lindroidcode.recyclerrelated.decoration.SimplePaddingDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

//Preparation: maven implementation, Adapter, ViewHolder

public class RecyclerRelatedActivity extends AppCompatActivity {


    /**
     * {
     *     "commentList": [
     *         {
     *             "nickname": "很长很长很长很长很长很长的昵称",
     *             "userId": "userIdC",
     *             "content": "消息消息消息消息消息消息消息消息消息消息消息消息消息消息消息消息消息消息"
     *         },
     *         {
     *             "nickname": "longest_nicknamenicknamenickname",
     *             "userId": "userIdBuserIdBuserIdB",
     *             "content": "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
     *         }
     *     ],
     *     "result": {
     *         "msg": "成功",
     *         "code": 0,
     *         "timestamp": 1586152551531
     *     },
     *     "noticeList": [{"content": "直播间公告:直播间公告直播间公告直播间公告直播间公告直播间公告直播间公告直播间公告直播间公告直播间公告直播间公告直播间公告直播间公告"}],
     *     "offset": "mock-mock11",
     *     "entryList": [
     *         {"content": "aaa进入了直播间"},
     *         {"content": "bbb进入了直播间"}
     *     ],
     *     "serialNo": "1586152551525-172025218047-140643"
     * }
     * @param bundle
     */

    static String str = "{\n" +
            "\"commentList\": [\n" +
            "{\n" +
            "\"content\": \"消息消息消息消息消息消息消息消息消息消息消息消息消息消息消息消息消息消息\",\n" +
            "\"nickname\": \"很长很长很长很长很长很长的昵称\",\n" +
            "\"userId\": \"userIdC\"\n" +
            "},\n" +
            "{\n" +
            "\"content\": \"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\",\n" +
            "\"nickname\": \"longest_nicknamenicknamenickname\",\n" +
            "\"userId\": \"userIdBuserIdBuserIdB\"\n" +
            "},\n" +
            "{\n" +
            "\"content\": \"aaaaaaaaaaaaaa\",\n" +
            "\"nickname\": \"nickname\",\n" +
            "\"userId\": \"userIdB\"\n" +
            "},\n" +
            "{\n" +
            "\"content\": \"我来了\",\n" +
            "\"nickname\": \"这是昵称1\",\n" +
            "\"userId\": \"userIdA\"\n" +
            "}\n" +
            "],\n" +
            "\"entryList\": [\n" +
            "{\n" +
            "\"content\": \"aaa进入了直播间\"\n" +
            "},\n" +
            "{\n" +
            "\"content\": \"bbb进入了直播间\"\n" +
            "}\n" +
            "],\n" +
            "\"noticeList\": [\n" +
            "{\n" +
            "\"content\": \"直播间公告:直播间公告直播间公告直播间公告直播间公告直播间公告直播间公告直播间公告直播间公告直播间公告直播间公告直播间公告直播间公告\"\n" +
            "}\n" +
            "],\n" +
            "\"offset\": \"mock-mock\",\n" +
            "\"result\": {\n" +
            "\"code\": 0,\n" +
            "\"msg\": \"成功\",\n" +
            "\"timestamp\": 1585739300140\n" +
            "},\n" +
            "\"serialNo\": \"1585739300026-127000000001-174795\"\n" +
            "}";
    private RecyclerView mAbcRecycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_related);
        mAbcRecycler = findViewById(R.id.recycler_abc);
        int decorationId = 0;
        if (getIntent().getExtras() != null) {
            decorationId = getIntent().getExtras().getInt("decoration");
        }

        NormalRecyclerAdapter normalRecyclerAdapter = new NormalRecyclerAdapter(this);
        /**
         * Layout Manager(必选)
         * Adapter(必选)
         */
        mAbcRecycler.setAdapter(normalRecyclerAdapter);
        mAbcRecycler.setLayoutManager(new LinearLayoutManager(this));

        if (0 == decorationId) {
            mAbcRecycler.addItemDecoration(new SimplePaddingDecoration(this));
        }else if (1 == decorationId){
            mAbcRecycler.addItemDecoration(new SimpleDividerDecoration(this));
        }
        mAbcRecycler.addItemDecoration(new LeftAndRightTagDecoration(this));//decoration 可以叠加
        mAbcRecycler.addItemDecoration(new HalfTransparentDecoration());//decoration 可以叠加
        ArrayList<String> noticeAL = new ArrayList<>();
        ArrayList<String> entryAL = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(str);

            if (jsonObject.optJSONObject("result")!=null && jsonObject.optJSONObject("result").has("code")) {
                int codeResponse = jsonObject.optJSONObject("result").getInt("code");
                if (0 == codeResponse) {
                    JSONArray noticeJsonArray = jsonObject.optJSONArray("noticeList");
                    JSONArray entryJsonArray = jsonObject.optJSONArray("entryList");

                    if(noticeJsonArray != null && noticeJsonArray.length() > 0){
                        for(int i=0; i < noticeJsonArray.length(); i++){
                            JSONObject itemJson = noticeJsonArray.getJSONObject(i);
                            noticeAL.add(i, itemJson.optString("content"));
                        }
                    }

                    //用户进入
                    if(entryJsonArray != null && entryJsonArray.length() > 0){
                        for(int i=0; i < entryJsonArray.length(); i++){
                            JSONObject itemJson = entryJsonArray.getJSONObject(i);
                            entryAL.add(i, itemJson.optString("content"));
                        }
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        normalRecyclerAdapter.addItemList(noticeAL);
        normalRecyclerAdapter.addItemList(noticeAL);
        normalRecyclerAdapter.addItemList(noticeAL);
        normalRecyclerAdapter.addItemList(noticeAL);
        normalRecyclerAdapter.addItemList(noticeAL);
        normalRecyclerAdapter.addItemList(noticeAL);
        normalRecyclerAdapter.addItemList(noticeAL);
        normalRecyclerAdapter.addItemList(noticeAL);
        normalRecyclerAdapter.addItemList(noticeAL);
        normalRecyclerAdapter.addItemList(entryAL);
        normalRecyclerAdapter.addItemList(entryAL);
        normalRecyclerAdapter.addItemList(entryAL);
        normalRecyclerAdapter.addItemList(entryAL);
    }
}
