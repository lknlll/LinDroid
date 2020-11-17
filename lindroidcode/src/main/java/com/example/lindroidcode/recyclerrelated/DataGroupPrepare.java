package com.example.lindroidcode.recyclerrelated;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DataGroupPrepare {

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

    private static String str = "{\n" +
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

    private static ArrayList<String> noticeAL = new ArrayList<>();
    private static ArrayList<String> entryAL = new ArrayList<>();

    public static ArrayList<String> getNoticeAL() {
        prepareData();
        return noticeAL;
    }

    public static ArrayList<String> getEntryAL() {
        prepareData();
        return entryAL;
    }

    public static void prepareData(){

        noticeAL = new ArrayList<>();
        entryAL = new ArrayList<>();
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
    }
}
