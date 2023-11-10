package com.example.lindroidcode.javaknowledge;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lindroidcode.R;
import com.example.lindroidcode.databinding.ActivityLearnJavaBinding;
import com.example.lindroidcode.javaknowledge.beans.ItemChooseFinalModel;
import com.example.lindroidcode.mvp.beans.User;
import com.example.lindroidcode.utils.TextOperateUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class LearnJavaActivity extends AppCompatActivity {

    private static final String TAG = LearnJavaActivity.class.getSimpleName();
    private short sA;
    private short sB;

    private Short sSA;
    private Integer sIA;
    private TextView mTvVarargs;
    private String targetPattern = "(京东|京东金融)(啊|呢|吗|呀)";

    private ActivityLearnJavaBinding mViewBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String foodData = "[]";
//        String foodData = "\"[]\"";
        List<ItemChooseFinalModel> selectedFood = new ArrayList<>();
        if (!TextUtils.isEmpty(foodData)) {
            selectedFood = new Gson().fromJson(foodData, new TypeToken<ArrayList<ItemChooseFinalModel>>() {
            }.getType());
        }


        mViewBinding = ActivityLearnJavaBinding.inflate(getLayoutInflater());
        View view = mViewBinding.getRoot();
        setContentView(view);
        List<User> srcList = new ArrayList<>();
        User user = new User();
        user.setPassword("1");
        user.setUsername("a");
        User userB = new User();
        userB.setPassword("2");
        userB.setUsername("B");
        srcList.add(user);
        srcList.add(userB);
        String srcJson = new Gson().toJson(srcList);
        List<User> desFromSrcJson = new Gson().fromJson(srcJson, new TypeToken<List<User>>(){}.getType());
        Log.e(TAG, "srcList " + srcJson);
        TextView tvCopy = mViewBinding.tvDeepCopy;
        //浅拷贝
        // 1、遍历循环复制
        TextOperateUtils.appendLineStartWithChangeLine(tvCopy,
                new String[]{"浅拷贝",
                        "1、遍历循环复制",
                        "2、使用List实现类的构造方法",
                        "3、list.addAll()",
                        "4、使用System.arraycopy()方法",
                        "深拷贝",
                        "Json"});
        List<User> destList=new ArrayList<User>(srcList.size());
        for(User p : srcList){
            destList.add(p);
        }
        Log.e(TAG, "destList " + new Gson().toJson(destList));
        //2、使用List实现类的构造方法
        List<User> destListB=new ArrayList<User>(srcList);
        Log.e(TAG, "destListB " + new Gson().toJson(destListB));

        //3、list.addAll()
        List<User> destListC=new ArrayList<User>();
        destListC.addAll(srcList);
        Log.e(TAG, "destListC " +  new Gson().toJson(destListC));

        //4、使用System.arraycopy()方法
        User[] srcUsers=srcList.toArray(new User[0]);
        User[] destUsers=new User[srcUsers.length];
        System.arraycopy(srcUsers, 0, destUsers, 0, srcUsers.length);
        Log.e(TAG, "destUsers " + new Gson().toJson(destUsers));

        srcList.get(0).setUsername("!!");
        Log.e(TAG, "srcList " + new Gson().toJson(srcList));
        Log.e(TAG, "destList " + new Gson().toJson(destList));
        Log.e(TAG, "destListB " + new Gson().toJson(destListB));
        Log.e(TAG, "destListC " + new Gson().toJson(destListC));
        Log.e(TAG, "destUsers " + new Gson().toJson(destUsers));
        Log.e(TAG, "desFromSrcJson " + new Gson().toJson(desFromSrcJson));

        sA = (short)1;
        sB = (short)1;
        sSA = 1;
        sIA = 1;
        Log.e(TAG, "onCreate: " + (1 == sA) );
        Log.e(TAG, "onCreate: " + (sA == sB));
        Log.e(TAG, "onCreate: " + (sA == sSA));
        Log.e(TAG, "onCreate: " + (sSA.equals(sIA)));
        Log.e(TAG, "onCreate: " + (sSA.equals(1)));
        Log.e(TAG, "onCreate: " + (sSA.equals((short)1)));

        ArrayList<String> l = new ArrayList<>();
        l.add("aa");
        l.add("bb");
        l.add("cc");
        for (Iterator iter = l.iterator(); iter.hasNext();) {
            String str = (String)iter.next();
            Log.e(TAG, "onCreate: " + str);
        }
        // 迭代器用于while循环，while 括号外 初始化iterator 迭代器
        Iterator iter = l.iterator();
        while(iter.hasNext()){
            String str = (String) iter.next();
            Log.e(TAG, "onCreate: " + str );
        }
        //下面这样会死循环
        /*while(l.iterator().hasNext()){
            String str = l.iterator().next();
            Log.e(TAG, "onCreate: inner iterator" + str );
        }*/

        mTvVarargs = mViewBinding.tvVarargs;
        uncertainArgs("“Varargs”是“variable number of arguments”","函数的参数表中有vararg时要放到最后");

        LearnStatic.checkTiming();

        LearnStatic learnStatic = new LearnStatic();
        LearnStatic learnStaticB = new LearnStatic();

        TextView tvRegularExpression = findViewById(R.id.tv_regular_expression);
        tvRegularExpression.append("\n[0-9]+ 表示一位或多位数字，如\"3\"或\"225\"");
        tvRegularExpression.append("\n[0-9]* 表示0位或多位数字，如\"\"或\"1\"或\"22\"");
        tvRegularExpression.append("\n[0-9]? 表示0位或一位数字，如\"\"或\"7\"");

        TextView tvPatternA = findViewById(R.id.tv_pattern_a);
        Button btCheckResult = findViewById(R.id.bt_check_result);
        EditText etPatternA = findViewById(R.id.et_pattern_a);
        EditText etPatternTarget = findViewById(R.id.et_pattern_target);

        etPatternTarget.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && !s.toString().equals("")) {
                    targetPattern = s.toString();
                }else {
                    targetPattern = "(京东|京东金融)(啊|呢|吗|呀)";
                }
                tvPatternA.setText("检查是否匹配“" + targetPattern + "”");
            }
        });

        btCheckResult.setOnClickListener(v -> {
            if (etPatternA.getEditableText().toString().matches(targetPattern)) {
                tvPatternA.append("\n" + etPatternA.getEditableText().toString() + "匹配");
            }else {
                tvPatternA.append("\n" + etPatternA.getEditableText().toString() + "不匹配");
            }
        });

        Log.e(TAG, "onCreate: 一位" + "3".matches("[0-9]+"));
        Log.e(TAG, "onCreate: 多位" + "225".matches("[0-9]+"));
        Log.e(TAG, "onCreate: 0位" + "".matches("[0-9]+"));

        Log.e(TAG, "onCreate: 一位" + "3".matches("[0-9]*"));
        Log.e(TAG, "onCreate: 多位" + "225".matches("[0-9]*"));
        Log.e(TAG, "onCreate: 0位" + "".matches("[0-9]*"));

        Log.e(TAG, "onCreate: 一位" + "3".matches("[0-9]?"));
        Log.e(TAG, "onCreate: 多位" + "225".matches("[0-9]?"));
        Log.e(TAG, "onCreate: 0位" + "".matches("[0-9]?"));

        int[] data = {4, 5, 3, 6, 2, 5, 1};
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            List<Integer> list = Arrays.stream(data).boxed().collect(Collectors.toList());
            int[] dataRestore = list.stream().mapToInt(Integer::valueOf).toArray();
        }
        TextView tvJ8Features = findViewById(R.id.tv_j8_feature);
        tvJ8Features.append("\nStream 是对集合（Collection）对象功能的增强，它专注于对集合对象进行各种非常便利、高效的聚合操作（aggregate operation），或者大批量数据操作 (bulk data operation)。");
        tvJ8Features.append("\n对于基本数值型，目前有三种对应的包装类型 Stream：IntStream、LongStream、DoubleStream。当然我们也可以用 Stream<Integer>、Stream<Long> >、Stream<Double>，但是 boxing 和 unboxing 会很耗时，所以特别为这三种基本数值型提供了对应的 Stream。");
        tvJ8Features.append("\nIntStream");
        tvJ8Features.append("\n可用于int[]和List<Integer>互转, 避免循环赋值的写法");
        tvJ8Features.append("\nList<Integer> list = Arrays.stream(data).boxed().collect(Collectors.toList());");
        tvJ8Features.append("\n// 1.使用Arrays.stream将int[]转换成IntStream。");
        tvJ8Features.append("\n// 2.使用IntStream中的boxed()装箱。将IntStream转换成Stream<Integer>。");
        tvJ8Features.append("\n// 3.使用Stream的collect()，将Stream<T>转换成List<T>，因此正是List<Integer>。");

        tvJ8Features.append("\nint[] dataRestore = list.stream().mapToInt(Integer::valueOf).toArray();");
        tvJ8Features.append("\n// 1.先转成IntStream。");
        tvJ8Features.append("\n// 2.通过mapToInt()把Stream<Integer>调用Integer::valueOf转成IntStream");
        tvJ8Features.append("\n// 3.IntStream中toArray()转成int[]");

    }

    private void uncertainArgs(String... strings){

        for (int i = 0; i < strings.length; i++) {
            mTvVarargs.append("\n");
            mTvVarargs.append(strings[i]);
        }

    }

}
