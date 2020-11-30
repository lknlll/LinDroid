package com.example.lindroidcode.spannable;

import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.BulletSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.LeadingMarginSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.ScaleXSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TextAppearanceSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

import com.example.lindroidcode.R;
import com.example.lindroidcode.utils.DensityUtils;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class SpannableTextActivity extends AppCompatActivity {

    private TextView mTvAppend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spannable_text);
        mTvAppend = findViewById(R.id.tv_append);

        final SpannableStringBuilder bWord = new SpannableStringBuilder("SpannableStringBuilder,可以append():");

        findViewById(R.id.bt_append).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bWord.append("onClick");
                mTvAppend.setText(bWord);
            }
        });

        TextView tvSupport = findViewById(R.id.tv_support);
        tvSupport.append("\nsetSpan (Object what, int start, int end, int flags)");
        tvSupport.append("\nwhat: Span ");
        tvSupport.append("\nend: Span结束的位置,不包含这个位置");
        tvSupport.append(
                "\nflags: Spannable.SPAN_EXCLUSIVE_EXCLUSIVE：前后都不包括，即在指定范围的前面和后面插入新字符都不会应用新样式 \n" +
                        "Spannable.SPAN_EXCLUSIVE_INCLUSIVE ：前面不包括，后面包括。即仅在范围字符的后面插入新字符时会应用新样式\n" +
                        "Spannable.SPAN_INCLUSIVE_EXCLUSIVE ：前面包括，后面不包括。\n" +
                        "Spannable.SPAN_INCLUSIVE_INCLUSIVE ：前后都包括。\n");

        TextView tvSpans = findViewById(R.id.tv_spans);
        //创建一个 SpannableString对象
        SpannableString msp = new SpannableString("字体测试字体大小一半两倍前景色背景色正常粗体斜体粗斜体下划线删除线x8x3电话邮件网站短信彩信地图X轴综合/bot");

        //设置字体(default,default-bold,monospace,serif,sans-serif)
        msp.setSpan(new TypefaceSpan("monospace"), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        msp.setSpan(new TypefaceSpan("serif"), 2, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置字体大小（绝对值,单位：像素）
        msp.setSpan(new AbsoluteSizeSpan(20), 4, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        msp.setSpan(new AbsoluteSizeSpan(20, true), 6, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //第二个参数boolean dip，如果为true，表示前面的字体大小单位为dip，否则为像素，同上。
        //设置字体大小（相对值,单位：像素） 参数表示为默认字体大小的多少倍
        msp.setSpan(new RelativeSizeSpan(0.5f), 8, 10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //0.5f表示默认字体大小的一半
        msp.setSpan(new RelativeSizeSpan(2.0f), 10, 12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //2.0f表示默认字体大小的两倍

        //设置字体前景色
        msp.setSpan(new ForegroundColorSpan(Color.MAGENTA), 12, 15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //设置前景色为洋红色
        //设置字体背景色
        msp.setSpan(new BackgroundColorSpan(Color.CYAN), 15, 18, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //设置背景色为青色

        //设置字体样式正常，粗体，斜体，粗斜体
        msp.setSpan(new StyleSpan(android.graphics.Typeface.NORMAL), 18, 20, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //正常
        msp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 20, 22, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //粗体
        msp.setSpan(new StyleSpan(android.graphics.Typeface.ITALIC), 22, 24, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //斜体
        msp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD_ITALIC), 24, 27, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //粗斜体

        //设置下划线
        msp.setSpan(new UnderlineSpan(), 27, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        //设置删除线
        msp.setSpan(new StrikethroughSpan(), 30, 33, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        //设置上下标
        msp.setSpan(new SubscriptSpan(), 34, 35, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //下标
        msp.setSpan(new SuperscriptSpan(), 36, 37, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //上标

        //超级链接（需要添加setMovementMethod方法附加响应）
        msp.setSpan(new URLSpan("tel:10086"), 37, 39, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //电话
        msp.setSpan(new URLSpan("123456789@qq.com"), 39, 41, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //邮件
        msp.setSpan(new URLSpan("http://www.baidu.com "), 41, 43, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //网络
        msp.setSpan(new URLSpan("sms:10086"), 43, 45, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //短信 使用sms:或者smsto:
        msp.setSpan(new URLSpan("mms:10086"), 45, 47, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //彩信 使用mms:或者mmsto:
        msp.setSpan(new URLSpan("geo:38.899533,-77.036476"), 47, 49, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //地图
        //设置字体大小（相对值,单位：像素） 参数表示为默认字体宽度的多少倍
        msp.setSpan(new ScaleXSpan(2.0f), 49, 51, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //2.0f表示默认字体宽度的两倍，即X轴方向放大为默认字体的两倍，而高度不变
        //设置字体（依次包括字体名称，字体大小，字体样式，字体颜色，链接颜色）
        ColorStateList csllink = null;
        ColorStateList csl = null;
        XmlResourceParser xppcolor = getResources().getXml(R.xml.text_appearance);
        try {
            csl = ColorStateList.createFromXml(getResources(), xppcolor);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        XmlResourceParser xpplinkcolor = getResources().getXml(R.xml.text_appearance);
        try {
            csllink = ColorStateList.createFromXml(getResources(), xpplinkcolor);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        msp.setSpan(new TextAppearanceSpan("monospace", android.graphics.Typeface.BOLD_ITALIC, 30, csl, csllink), 51,
                53, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置项目符号
        msp.setSpan(new BulletSpan(android.text.style.BulletSpan.STANDARD_GAP_WIDTH, Color.GREEN),
                0, msp.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);//第一个参数表示项目符号占用的宽度，第二个参数为项目符号的颜色
        tvSpans.setText(msp);
        tvSpans.setMovementMethod(LinkMovementMethod.getInstance());

        TextView tvSpanLean = findViewById(R.id.tv_span_leaning);
        StringBuilder sb = new StringBuilder();
        sb.append("\n 首行缩进");
        sb.append("\n 1.先创建SpannableString对象");
        sb.append("\n 2.设置文本缩进的样式，参数arg0，首行缩进的像素，arg1，剩余行缩进的像素,这里我将像素px转换成了手机独立像素dp");
        sb.append("\n 3.进行样式的设置了,其中参数what是具体样式的实现对象,start则是该样式开始的位置，end对应的是样式结束的位置，参数flags，定义在Spannable中的常量");
        //1.先创建SpannableString对象
        SpannableString spannableString = new SpannableString(sb);
        //2.设置文本缩进的样式，参数arg0，首行缩进的像素，arg1，剩余行缩进的像素,这里我将像素px转换成了手机独立像素dp
        LeadingMarginSpan.Standard what = new LeadingMarginSpan.Standard(DensityUtils.dp2px(16, this), 0);
        //3.进行样式的设置了,其中参数what是具体样式的实现对象,start则是该样式开始的位置，end对应的是样式结束的位置，参数flags，定义在Spannable中的常量
        spannableString.setSpan(what, 0, spannableString.length(), SpannableString.SPAN_INCLUSIVE_INCLUSIVE);
        tvSpanLean.setText(spannableString);
    }
}
