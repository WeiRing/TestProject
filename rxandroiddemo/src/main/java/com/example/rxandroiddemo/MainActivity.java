package com.example.rxandroiddemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.widget.Button;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxCompoundButton;
import com.trello.rxlifecycle.ActivityEvent;
import com.trello.rxlifecycle.ActivityLifecycleProvider;
import com.trello.rxlifecycle.RxLifecycle;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.subjects.BehaviorSubject;

public class MainActivity extends AppCompatActivity implements ActivityLifecycleProvider {
    private int count = 0;
    private Button loopBtn;
    private Subscription mSubscription;//订阅轮询的对象
    private AppCompatCheckBox mCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifecycleSubject.onNext(ActivityEvent.CREATE);
        setContentView(R.layout.activity_main);
        initViewEvent();
    }

    private void initViewEvent() {
        //longEventBtn
        RxView.longClicks(findViewById(R.id.longEventBtn)).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                Toast.makeText(MainActivity.this, "长按事件", Toast.LENGTH_SHORT).show();
            }
        });

        //Buffer操作符
        RxView.clicks(findViewById(R.id.bufferBtn))
                .buffer(3)//必须点击满3次才能执行事件...
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Void>>() {
                    @Override
                    public void call(List<Void> voids) {
                        Toast.makeText(MainActivity.this, "累积点击3次才后事件才生效", Toast.LENGTH_SHORT).show();
                    }
                });

        //防抖动点击（3s内只能点击一次）
        RxView.clicks(findViewById(R.id.btnOne)).throttleFirst(3000, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                Toast.makeText(MainActivity.this, "不能连续点击", Toast.LENGTH_SHORT).show();
            }
        });

        //延迟2秒后执行
        RxView.clicks(findViewById(R.id.btnTwo)).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                Observable.timer(2, TimeUnit.SECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Long>() {
                            @Override
                            public void call(Long aLong) {
                                Toast.makeText(MainActivity.this, "2s秒后再显示", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        //轮询执行
        loopBtn = (Button) findViewById(R.id.btnThree);
        RxView.clicks(loopBtn).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                if (mSubscription == null || mSubscription.isUnsubscribed()) {
                    mSubscription = Observable.interval(1000, 2000, TimeUnit.MILLISECONDS)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Action1<Long>() {
                                @Override
                                public void call(Long aLong) {
                                    count++;
                                    Toast.makeText(MainActivity.this, "轮询显示" + count + "次", Toast.LENGTH_SHORT).show();
                                    loopBtn.setText("停止轮询" + count);
                                }
                            });
                } else if (mSubscription != null && !mSubscription.isUnsubscribed()) {
                    mSubscription.unsubscribe();
                    loopBtn.setText("开始轮询Toast");
                    count = 0;
                }
            }
        });

        mCheckBox = (AppCompatCheckBox) findViewById(R.id.mCheckBox);
        RxCompoundButton.checkedChanges(mCheckBox)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean)
                            mCheckBox.setText("状态：已选中");
                        else
                            mCheckBox.setText("状态：未选中");
                    }
                });

    }


    private final BehaviorSubject<ActivityEvent> lifecycleSubject = BehaviorSubject.create();

    //使用RxLifecycle框架，实现ActivityLifecycleProvider重写的三个方法，防止使用Rx框架时内存溢出
    @Override
    public final Observable<ActivityEvent> lifecycle() {
        return lifecycleSubject.asObservable();
    }

    @Override
    public final <T> Observable.Transformer<T, T> bindUntilEvent(ActivityEvent event) {
        return RxLifecycle.bindUntilActivityEvent(lifecycleSubject, event);
    }

    @Override
    public final <T> Observable.Transformer<T, T> bindToLifecycle() {
        return RxLifecycle.bindActivity(lifecycleSubject);
    }

    //联动生命周期，防止使用Rx框架时内存溢出
    @Override
    protected void onStart() {
        lifecycleSubject.onNext(ActivityEvent.START);
        super.onStart();
    }

    @Override
    protected void onResume() {
        lifecycleSubject.onNext(ActivityEvent.RESUME);
        super.onResume();
    }

    @Override
    protected void onPause() {
        lifecycleSubject.onNext(ActivityEvent.PAUSE);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        lifecycleSubject.onNext(ActivityEvent.DESTROY);
        super.onDestroy();
    }
}
