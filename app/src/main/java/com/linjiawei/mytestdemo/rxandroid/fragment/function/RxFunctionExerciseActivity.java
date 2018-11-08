package com.linjiawei.mytestdemo.rxandroid.fragment.function;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.linjiawei.mytestdemo.R;
import com.linjiawei.mytestdemo.base.ToolbarBaseActivity;
import com.linjiawei.mytestdemo.javabean.RxFunctionBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.AsyncSubject;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;


/**
 * 更多详细RJava操作符，直接查此贴:
 *                  https://blog.csdn.net/maplejaw_/article/details/52396175
 */
public class RxFunctionExerciseActivity extends ToolbarBaseActivity {
    public static final String DATA_TAG = "functionBean";
    public static final String LOG_TAG = "rxExerciseActivity";
    private RxFunctionBean mRxFunctionBean;
    @Bind(R.id.rxOperatorTitle)
    TextView operatorText;
    @Bind(R.id.operatorDescribeText)
    TextView operatorDescribeText;
    private String type = "";

    @Override
    protected int getContentView() {
        return R.layout.activity_rx_function_exercise;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        mRxFunctionBean = (RxFunctionBean) getIntent().getExtras().get(DATA_TAG);
        if (mRxFunctionBean != null) {
            setTitle(mRxFunctionBean.getFunctionTitle() + " 操作符");
            operatorText.setText(mRxFunctionBean.getFunctionTitle());
            operatorDescribeText.setText(mRxFunctionBean.getDescribe());
            type = mRxFunctionBean.getFunctionTitle();
        }
    }

    @OnClick(R.id.describeBtn)
    public void clickCase(View view) {
        ToastUtils.showShort("执行...");
        switch (type) {
            case "create":
                create_doSomething();
                break;
            case "zip":
                zip_doSomething();
                break;
            case "map":
                map_doSomething();
                break;
            case "flatMap":
                flatMap_doSomething();
                break;
            case "switchMap":
                switchMap_doSomething();
                break;
            case "concatMap":
                concatMap_doSomething();
                break;
            case "doOnNext":
                doOnNext_doSomething();
                break;
            case "filter":
                filter_doSomething();
                break;
            case "skip":
                skip_doSomething();
                break;
            case "take":
                take_doSomething();
                break;
            case "timer":
                timer_doSomething();
                break;
            case "interval":
                interval_doSomething();
                break;
            case "just":
                just_doSomething();
                break;
            case "single":
                single_doSomething();
                break;
            case "concat":
                concat_doSomething();
                break;
            case "distinct":
                distinct_doSomething();
                break;
            case "buffer":
                buffer_doSomething();
                break;
            case "debounce":
                debounce_doSomething();
                break;
            case "defer":
                defer_doSomething();
                break;
            case "last":
                last_doSomething();
                break;
            case "merge":
                merge_doSomething();
                break;
            case "reduce":
                reduce_doSomething();
                break;
            case "scan":
                scan_doSomething();
                break;
            case "window":
                window_doSomething();
                break;
            case "PublishSubject":
                publish_doSomething();
                break;
            case "AsyncSubject":
                async_doSomething();
                break;
            case "BehaviorSubject":
                behavior_doSomething();
                break;
            case "Completable":
                completable_doSomething();
                break;
            case "Flowable":
                flowable_doSomething();
                break;

        }

    }

    /**
     * 用于创建一个被观察的对象
     */
    private void create_doSomething() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onNext(4);
                e.onComplete();
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    Disposable mDisposable;

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mDisposable = d;// 在RxJava 2.x 中，新增的Disposable可以做到切断的操作，让Observer观察者不再接收上游事件
                    }

                    @Override
                    public void onNext(@NonNull Integer integer) {
                        if (integer == 3) {
                            mDisposable.dispose();
                            ToastUtils.showShort("Disposable已切断操作");//执行dispose()后,onComplete()方法不会被执行
                        }
                        LogUtils.i(LOG_TAG, "onNext -> " + integer);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        LogUtils.i(LOG_TAG, "onError -> " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        LogUtils.i(LOG_TAG, "onComplete");
                        ToastUtils.showShort("onComplete");
                    }
                });
    }

    /**
     * 合并事件专用：
     * 分别从两个上游事件中各取出一个组合,
     * 一个事件只能被使用一次，顺序严格按照事件发送的顺序,
     * 最终下游事件收到的是和上游事件最少的数目相同（必须两两配对，多余的舍弃)
     */
    private void zip_doSomething() {
        Observable.zip(Observable.just(1, 2, 3), Observable.just("A", "B", "C", "D"), new BiFunction<Integer, String, String>() {
            @Override
            public String apply(@NonNull Integer integer, @NonNull String s) throws Exception {
                return "合并后数据：" + integer + s;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                LogUtils.i(LOG_TAG, "合并后数据：" + s);
                ToastUtils.showShort(s);
            }
        });
    }

    /**
     * 基本是RxJava 最简单的操作符了作用是对上游发送的每一个事件应用一个函数,
     * 使得每一个事件都按照指定的函数去变化
     */
    private void map_doSomething() {
        Observable.just(1, 2, 3)
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(@NonNull Integer integer) throws Exception {
                        return String.valueOf(integer);
                    }
                }).subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                LogUtils.i(LOG_TAG, "map -> " + s);
                ToastUtils.showShort("Integer to String : " + s);
            }
        });
    }

    /**
     * FlatMap将一个发送事件的上游Observable变换成多个发送事件的Observables,
     * 然后将它们发射的时间合并后放进一个单独的Observable里
     * 注意：FlatMap不保证每个新的Observable事件顺序
     */
    private void flatMap_doSomething() {
        Observable.just(1, 2, 3)
                .flatMap(new Function<Integer, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(@NonNull Integer integer) throws Exception {
                        List<String> list = new ArrayList<>();
                        if (integer == 1) {
                            list.add("one");
                            list.add("two");
                        } else if (integer == 2) {
                            list.add("A");
                            list.add("B");
                        } else if (integer == 3) {
                            list.add("1A");
                            list.add("2B");
                        }
                        return Observable.fromIterable(list).delay(500, TimeUnit.MILLISECONDS);
                    }
                }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        LogUtils.i(LOG_TAG, s);
                        ToastUtils.showShort(s);
                    }
                });
    }

    /**
     * switchMap操作符只会发射[emit]最近的Observables。
     * 当源Observable发射一个新的数据项时，如果旧数据项订阅还未完成就取消旧订阅数据和停止监视那个数据项产生的Observable,开始监视新的数据项.
     */
    private void switchMap_doSomething() {
        Observable.just(1, 2, 3, 4)
                .switchMap(new Function<Integer, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(@NonNull Integer integer) throws Exception {
                        return Observable.just(String.valueOf(integer)).subscribeOn(Schedulers.newThread());
                    }
                }).subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                LogUtils.i(LOG_TAG, "switchMap -> " + s);
                ToastUtils.showShort(s);
            }
        });
    }


    /**
     * concatMap作用和flatMap几乎一模一样，唯一的区别是它能保证事件的顺序
     */
    private void concatMap_doSomething() {
        Observable.just(1, 2, 3)
                .concatMap(new Function<Integer, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(@NonNull Integer integer) throws Exception {
                        List<String> list = new ArrayList<>();
                        if (integer == 1) {
                            list.add("one");
                            list.add("two");
                        } else if (integer == 2) {
                            list.add("A");
                            list.add("B");
                        } else if (integer == 3) {
                            list.add("1A");
                            list.add("2B");
                        }
                        return Observable.fromIterable(list).delay(500, TimeUnit.MILLISECONDS);
                    }
                }).subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                LogUtils.i(LOG_TAG, s);
                ToastUtils.showShort(s);
            }
        });
    }

    /**
     * 让订阅者在接收到数据前干点事情的操作符
     */
    private void doOnNext_doSomething() {
        final List<Integer> list = new ArrayList<>();
        list.add(100);
        list.add(200);
        list.add(300);
        Observable.fromIterable(list)
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        LogUtils.i(LOG_TAG, "接收每个数据前做的事...");
                        ToastUtils.showShort("接收每个数据前做的事...");
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                LogUtils.i(LOG_TAG, integer);
                ToastUtils.showShort("doOnNext" + integer);
            }
        });
    }

    /**
     * 过滤操作符，取正确的值
     */
    private void filter_doSomething() {
        Observable.just(1, 2, 3, 4, 5, 6, 7, 8)
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(@NonNull Integer integer) throws Exception {
                        if (integer % 2 == 0)
                            return true;
                        else
                            return false;
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                LogUtils.i(LOG_TAG, integer);
                ToastUtils.showShort("filter" + integer);
            }
        });
    }

    /**
     * 接受一个long型参数，代表跳过多少个数目的事件再开始接收
     */
    private void skip_doSomething() {
        Observable.just(1, 2, 3, 4, 5).skip(3)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        LogUtils.i(LOG_TAG, "skip-> " + integer);
                    }
                });
    }

    /**
     * 用于指定订阅者最多收到前n条数据
     */
    private void take_doSomething() {
        Observable.just("A", "B", "C", "D").take(2).subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                LogUtils.i(LOG_TAG, "take->" + s);
            }
        });
    }

    /**
     * 在Rxjava中timer 操作符既可以延迟执行一段逻辑，也可以间隔执行一段逻辑
     * 【注意】但在RxJava 2.x已经过时了，现在用interval操作符来间隔执行
     */
    private void timer_doSomething() {
        Observable.timer(2, TimeUnit.SECONDS)   //2s后开始执行
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        LogUtils.i(LOG_TAG, "timer -> " + aLong);
                    }
                });
    }

    /**
     * 间隔执行操作，默认在新线程
     * 不用时（退出时），一定要取消订阅，不然线程会一直在执行
     */
    private void interval_doSomething() {
        Observable
                .interval(2, 1, TimeUnit.SECONDS)
                .compose(this.<Long>bindToLifecycle()) //方法一：生命周期跟activity联动..当执行onDestory()时，自动解除订阅
                //.compose(this.<Long>bindUntilEvent(ActivityEvent.DESTROY))  //方法二 使用bindUntilEvent指定在哪个生命周期方法调用时取消订阅：
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        LogUtils.i(LOG_TAG, "interval:" + aLong);
                    }
                });
    }

    /**
     * just操作符，就是接受一个可变参数，依次发送
     */
    private void just_doSomething() {
        Observable.just(1, 2, 3).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                LogUtils.i(LOG_TAG, integer + "");
            }
        });
    }

    /**
     * 顾名思义，Single只会接收一个参数
     */

    private void single_doSomething() {
        Single.just(new Random().nextInt())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        LogUtils.i(LOG_TAG, "Single只会接收一个参数" + integer);
                        ToastUtils.showShort("Single只会接收一个参数" + integer);
                    }
                });
    }

    /**
     * 连接操作符，可接受Observable的可变参数，或者Observable的集合
     */
    private void concat_doSomething() {
        Observable.concat(Observable.just(1, 2), Observable.just(100, 200)).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                LogUtils.i(LOG_TAG, "concat -> " + integer);
            }
        });
    }

    /**
     * 去重操作符，其实就是简单的去重
     */
    private void distinct_doSomething() {
        Observable.just(1, 2, 4, 2, 5, 2, 1)
                .distinct().subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                LogUtils.i(LOG_TAG, integer);
            }
        });
    }

    /**
     * buffer(count, skip)` 从定义就差不多能看出作用了，将 observable 中的数据按 skip（步长）分成最长不超过 count 的 buffer，然后生成一个 observable
     */
    private void buffer_doSomething() {
        Observable.just(1, 2, 3, 4, 5, 6, 7, 8)
                .buffer(2, 3)
                .subscribe(new Consumer<List<Integer>>() {
                    @Override
                    public void accept(@NonNull List<Integer> integers) throws Exception {
                        LogUtils.i(LOG_TAG, integers);//最终得到的结果是 [1, 2] ， [4, 5] ，[7, 8] 三个集合
                    }
                });
    }

    /**
     * 过滤掉发射速率过快的数据项
     */
    private void debounce_doSomething() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                Thread.sleep(200);
                e.onNext(2);
                Thread.sleep(510);
                e.onNext(3);
                Thread.sleep(200);
                e.onNext(4);
                e.onComplete();
            }
        }).debounce(500, TimeUnit.MILLISECONDS).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                LogUtils.i(LOG_TAG, "debounce -> " + integer);
            }
        });
    }

    /**
     * 就是在每次订阅的时候就会创建一个新的 Observable
     * （只有在发生注册订阅的时候，才会去调用call()方法）
     */
    private void defer_doSomething() {
        Observable.defer(new Callable<ObservableSource<Integer>>() {
            @Override
            public ObservableSource<Integer> call() throws Exception {
                return Observable.just(100);
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                LogUtils.i(LOG_TAG, "defer -> " + integer);
            }
        });
    }

    /**
     * 取出最后一个值，参数是没有值的时候的默认值
     */
    private void last_doSomething() {
        Observable.just(1, 2, 3, 4)
                .last(100)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        LogUtils.i(LOG_TAG, "last -> " + integer);
                    }
                });
    }

    /**
     * 将多个Observable合起来，接受可变参数，也支持使用迭代器集合
     */
    private void merge_doSomething() {
        Observable.merge(Observable.just(1), Observable.just(100))
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        LogUtils.i(LOG_TAG, "merge" + integer);
                    }
                });
    }

    /**
     * 就是一次用一个方法处理一个值，可以有一个seed(种子)作为初始值
     */
    private void reduce_doSomething() {
        Observable.just(1, 2, 3, 4, 5)
                .reduce(10, new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(@NonNull Integer integer, @NonNull Integer integer2) throws Exception {
                        return integer + integer2;  //10作为基数开始进行累加... 10+1+2+3+4+5=25
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                LogUtils.i(LOG_TAG, "reduce" + integer);
            }
        });
    }

    /**
     * 和上面的reduce差不多，区别在于reduce()只输出结果，而scan()会将过程中每一个结果输出
     */
    private void scan_doSomething() {
        Observable.just(1, 2, 3, 4, 5)
                .scan(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(@NonNull Integer integer, @NonNull Integer integer2) throws Exception {
                        return integer + integer2;
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                LogUtils.i(LOG_TAG, "reduce" + integer);
            }
        });
    }

    /**
     * 按照时间划分窗口，将数据发送给不同的Observable
     */
    private void window_doSomething() {
        Observable.interval(1, TimeUnit.SECONDS) // 间隔一秒发一次
                .take(15) // 最多接收15个
                .window(3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Observable<Long>>() {
                    @Override
                    public void accept(@NonNull Observable<Long> longObservable) throws Exception {
                        LogUtils.i(LOG_TAG, "Sub Divide begin...\n");
                        longObservable.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<Long>() {
                                    @Override
                                    public void accept(@NonNull Long aLong) throws Exception {
                                        LogUtils.i(LOG_TAG, "Next:" + aLong + "\n");
                                    }
                                });
                    }
                });
    }


    /**
     * PublishSubject
     * onNext() 会通知每个观察者，仅此而已
     */
    private void publish_doSomething() {
        PublishSubject<Integer> publishSubject = PublishSubject.create();

        publishSubject.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                LogUtils.i(LOG_TAG, "First onSubscribe :" + d.isDisposed() + "\n");
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                LogUtils.i(LOG_TAG, "First onNext value :" + integer + "\n");
            }

            @Override
            public void onError(@NonNull Throwable e) {
                LogUtils.i(LOG_TAG, "First onError:" + e.getMessage() + "\n");
            }

            @Override
            public void onComplete() {
                LogUtils.i(LOG_TAG, "First onComplete!\n");
            }
        });

        publishSubject.onNext(1);
        publishSubject.onNext(2);
        publishSubject.onNext(3);

        publishSubject.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                LogUtils.i(LOG_TAG, "Second onSubscribe :" + d.isDisposed() + "\n");
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                LogUtils.i(LOG_TAG, "Second onNext value :" + integer + "\n");
            }

            @Override
            public void onError(@NonNull Throwable e) {
                LogUtils.i(LOG_TAG, "Second onError:" + e.getMessage() + "\n");
            }

            @Override
            public void onComplete() {
                LogUtils.i(LOG_TAG, "Second onComplete!\n");
            }
        });
        publishSubject.onNext(4);
        publishSubject.onNext(5);
        publishSubject.onComplete();
    }

    /**
     * 在调用 onComplete() 之前，除了 subscribe() 其它的操作都会被缓存，在调用 onComplete() 之后只有最后一个 onNext() 会生效
     */
    private void async_doSomething() {
        AsyncSubject<Integer> asyncSubject = AsyncSubject.create();

        asyncSubject.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                LogUtils.i(LOG_TAG, "First onSubscribe :" + d.isDisposed() + "\n");
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                LogUtils.i(LOG_TAG, "First onNext value :" + integer + "\n");
            }

            @Override
            public void onError(@NonNull Throwable e) {
                LogUtils.i(LOG_TAG, "First onError:" + e.getMessage() + "\n");
            }

            @Override
            public void onComplete() {
                LogUtils.i(LOG_TAG, "First onComplete!\n");
            }
        });

        asyncSubject.onNext(1);
        asyncSubject.onNext(2);
        asyncSubject.onNext(3);

        asyncSubject.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                LogUtils.i(LOG_TAG, "Second onSubscribe :" + d.isDisposed() + "\n");
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                LogUtils.i(LOG_TAG, "Second onNext value :" + integer + "\n");
            }

            @Override
            public void onError(@NonNull Throwable e) {
                LogUtils.i(LOG_TAG, "Second onError:" + e.getMessage() + "\n");
            }

            @Override
            public void onComplete() {
                LogUtils.i(LOG_TAG, "Second onComplete!\n");
            }
        });

        asyncSubject.onNext(4);
        asyncSubject.onNext(5);
        asyncSubject.onComplete();
    }


    /**
     * BehaviorSubject 的最后一次 onNext() 操作会被缓存，然后在 subscribe() 后立刻推给新注册的 Observer
     */
    private void behavior_doSomething() {
        BehaviorSubject<Integer> behaviorSubject = BehaviorSubject.create();

        behaviorSubject.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                LogUtils.i(LOG_TAG, "First onSubscribe :" + d.isDisposed() + "\n");
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                LogUtils.i(LOG_TAG, "First onNext value :" + integer + "\n");
            }

            @Override
            public void onError(@NonNull Throwable e) {
                LogUtils.i(LOG_TAG, "First onError:" + e.getMessage() + "\n");
            }

            @Override
            public void onComplete() {
                LogUtils.i(LOG_TAG, "First onComplete!\n");
            }
        });

        behaviorSubject.onNext(1);
        behaviorSubject.onNext(2);
        behaviorSubject.onNext(3);

        behaviorSubject.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                LogUtils.i(LOG_TAG, "Second onSubscribe :" + d.isDisposed() + "\n");
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                LogUtils.i(LOG_TAG, "Second onNext value :" + integer + "\n");
            }

            @Override
            public void onError(@NonNull Throwable e) {
                LogUtils.i(LOG_TAG, "Second onError:" + e.getMessage() + "\n");
            }

            @Override
            public void onComplete() {
                LogUtils.i(LOG_TAG, "Second onComplete!\n");
            }
        });

        behaviorSubject.onNext(4);
        behaviorSubject.onNext(5);
        behaviorSubject.onComplete();
    }


    /**
     * 只关心结果，也就是说 Completable 是没有 onNext 的，要么成功要么出错，不关心过程，在 subscribe 后的某个时间点返回结果
     */
    private void completable_doSomething() {
        Completable.timer(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        LogUtils.i(LOG_TAG, "onSubscribe : d :" + d.isDisposed() + "\n");
                    }

                    @Override
                    public void onComplete() {
                        LogUtils.i(LOG_TAG, "onComplete\n");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        LogUtils.i(LOG_TAG, "onError :" + e.getMessage() + "\n");
                    }
                });
    }


    /**
     * 专用于解决背压问题
     */
    private void flowable_doSomething() {
        Flowable.just(1, 2, 3, 4)
                .reduce(100, new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(@NonNull Integer integer, @NonNull Integer integer2) throws Exception {
                        return integer + integer2;
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                LogUtils.i(LOG_TAG, "Flowable :" + integer + "\n");
            }
        });
    }
}
