package com.example.lindroidcode.rxjava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.lindroidcode.databinding.ActivityRxJavaBinding;
import com.example.lindroidcode.utils.TextOperateUtils;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class RxJavaActivity extends AppCompatActivity {

    private static final String TAG = RxJavaActivity.class.getSimpleName();

    private ActivityRxJavaBinding mActivityRxJavaBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityRxJavaBinding = ActivityRxJavaBinding.inflate(getLayoutInflater());
        View view = mActivityRxJavaBinding.getRoot();
        setContentView(view);

        mActivityRxJavaBinding.tvRxAbc.append("\nRxJava, 离不开观察者模式");
        mActivityRxJavaBinding.tvRxAbc.append("\nObservable 被观察者，产生事件");
        mActivityRxJavaBinding.tvRxAbc.append("\nObserver 观察者，接收事件");
        mActivityRxJavaBinding.tvRxAbc.append("\nsubscribe() 连接Observable和Observer");
        mActivityRxJavaBinding.tvRxAbc.append("\n各方法可链式调用");
        mActivityRxJavaBinding.tvRxAbc.append("\n");
        mActivityRxJavaBinding.tvRxAbc.append("\nObservableEmitter 发射器，发出事件，可以发出三种类型的事件");
        mActivityRxJavaBinding.tvRxAbc.append("\nonNext(T value)、onComplete()和onError(Throwable error)");
        TextOperateUtils.appendLineStartWithChangeLine(mActivityRxJavaBinding.tvRxAbc,
                new String[]{"发射事件的规则:","上游可以发送无限个onNext, 下游也可以接收无限个onNext",
                "上游发送了一个onComplete后, 上游onComplete之后的事件将继续发送, 而下游收到onComplete事件之后将不再继续接收事件",
                "上游发送了一个onError后, 上游onError之后的事件将继续发送, 而下游收到onError事件之后将不再继续接收事件",
                "上游可以不发送onComplete或onError",
                "onComplete和onError必须唯一并且互斥,需要自行在代码中进行控制",
                "Disposable dispose() 调用后，下游不再接收事件，上游仍然继续发送剩下的事件",
                "https://www.jianshu.com/u/c50b715ccaeb 2 - 10"});
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e(TAG,"onSubscribe: " + d.toString());
            }

            @Override
            public void onNext(Integer value) {
                Log.e(TAG,"onNext: " + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG,"onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.e(TAG,"onComplete: " + System.currentTimeMillis());
            }
        });

        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                
            }
        });
    }
}