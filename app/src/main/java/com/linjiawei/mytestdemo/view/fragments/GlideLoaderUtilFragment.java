package com.linjiawei.mytestdemo.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.linjiawei.mytestdemo.R;
import com.linjiawei.mytestdemo.base.RxFragmentV4;
import com.linjiawei.mytestdemo.interfacebase.OnFragmentInteractionListener;


public class GlideLoaderUtilFragment extends RxFragmentV4 {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private OnFragmentInteractionListener mListener;

    private String mParam1;
    private String mParam2;

    String URL1 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490944508&di=671845045c66356487c1a539c4ed0717&imgtype=jpg&er=1&src=http%3A%2F%2Fattach.bbs.letv.com%2Fforum%2F201606%2F27%2F185306g84m4gsxztvzxjt5.jpg";
    String URL2 = "http://s1.dwstatic.com/group1/M00/86/4A/81beb00a44bc52b4fdd46285de8f8f00.png";
    String URL3 = "https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2796659031,1466769776&fm=80&w=179&h=119&img.JPEG";
    String URL4 = "https://isparta.github.io/compare-webp/image/gif_webp/gif/1.gif";
    String URL5 = "https://p.upyun.com/docs/cloud/demo.jpg!/format/webp";

    String IMG_NAME = "SHARE_IMG2.PNG";
    String IMG_NAME_C = "jpeg_test.jpeg";

    private ImageView iv_test1;
    private ImageView iv_test2;
    private ImageView iv_test3;
    private ImageView iv_test4;
    private ImageView iv_test5;
    private ImageView iv_test6;
    private ImageView iv_test7;
    private ImageView iv_test8;
    private ImageView iv_test9;
    private ImageView iv_test10;
    private ImageView iv_test11;
    private ImageView iv_test12;
    private ImageView iv_test13;
    private ImageView iv_test14;
    private ImageView iv_test15;


    public static GlideLoaderUtilFragment newInstance(String param1, String param2) {
        GlideLoaderUtilFragment fragment = new GlideLoaderUtilFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflateView = inflater.inflate(R.layout.fragment_glide_loader_util, container, false);
    //    initView(inflateView);
        return inflateView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    //    load();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(int type) {
        if (mListener != null) {
            mListener.onFragmentInteraction(type);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }




/*



    private void initView(View view) {
        iv_test1 = (ImageView) view.findViewById(R.id.iv_test1);
        iv_test2 = (ImageView) view.findViewById(R.id.iv_test2);
        iv_test3 = (ImageView) view.findViewById(R.id.iv_test3);
        iv_test4 = (ImageView) view.findViewById(R.id.iv_test4);
        iv_test5 = (ImageView) view.findViewById(R.id.iv_test5);
        iv_test6 = (ImageView) view.findViewById(R.id.iv_test6);
        iv_test7 = (ImageView) view.findViewById(R.id.iv_test7);
        iv_test8 = (ImageView) view.findViewById(R.id.iv_test8);
        iv_test9 = (ImageView) view.findViewById(R.id.iv_test9);
        iv_test10 = (ImageView) view.findViewById(R.id.iv_test10);
        iv_test11 = (ImageView) view.findViewById(R.id.iv_test11);
        iv_test12 = (ImageView) view.findViewById(R.id.iv_test12);
        iv_test13 = (ImageView) view.findViewById(R.id.iv_test13);
        iv_test14 = (ImageView) view.findViewById(R.id.iv_test14);
        iv_test15 = (ImageView) view.findViewById(R.id.iv_test15);
    }

    private void load() {
        ViewPropertyAnimation.Animator animationObject = new ViewPropertyAnimation.Animator() {
            @Override
            public void animate(View view) {
                view.setAlpha( 0f );

                ObjectAnimator fadeAnim = ObjectAnimator.ofFloat( view, "alpha", 0f, 1f );
                fadeAnim.setDuration( 2500 );
                fadeAnim.start();
            }
        };

        ImageLoader.with(getContext())
                .url(URL1)
                .animate(animationObject)
                .placeHolder(R.mipmap.ic_launcher)
                .scale(ScaleMode.CENTER_CROP)
                .into(iv_test1);

        ImageLoader.with(getContext())
                .url(URL1)
                .placeHolder(R.mipmap.ic_launcher)
                .scale(ScaleMode.FIT_CENTER)
                .into(iv_test2);

        //        ImageLoader.with(this)
        //                .url(URL2)
        //                .placeHolder(R.mipmap.ic_launcher)
        //                .scale(ScaleMode.FIT_CENTER)
        //                .into(iv_test3);

        Glide.with(this).load(URL4).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(iv_test3);

        ImageLoader.with(getActivity())
                .url(URL4)
                .placeHolder(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .scale(ScaleMode.FIT_CENTER)
                .into(iv_test4);

        ImageLoader.with(getContext())
                .url(URL3)
                .placeHolder(R.mipmap.ic_launcher)
                .scale(ScaleMode.FIT_CENTER)
                .into(iv_test5);

        ImageLoader.with(getContext())
                .url(URL5)
                .placeHolder(R.mipmap.ic_launcher)
                .scale(ScaleMode.FIT_CENTER)
                .into(iv_test6);

        ImageLoader.with(getContext())
//                .res(R.drawable.gif_test)
                .res(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeHolder(R.mipmap.ic_launcher)
                .scale(ScaleMode.FIT_CENTER)
                .into(iv_test7);

        ImageLoader.with(getContext())
//                .res(R.drawable.jpeg_test)
                .res(R.mipmap.ic_launcher)
                .placeHolder(R.mipmap.ic_launcher)
                .scale(ScaleMode.FIT_CENTER)
                .into(iv_test8);

        ImageLoader.with(getContext())
//                .res(R.drawable.b000)
                .res(R.mipmap.ic_launcher)
                .vignetteFilter()
                .priority(PriorityMode.PRIORITY_NORMAL)
                .placeHolder(R.mipmap.ic_launcher)
                .scale(ScaleMode.FIT_CENTER)
                //                .ignoreCertificateVerify()
                .into(iv_test9);

        ImageLoader.with(getContext())
//                .res(R.drawable.b000)
                .res(R.mipmap.ic_launcher)
                .sketchFilter()
                .placeHolder(R.mipmap.ic_launcher)
                .scale(ScaleMode.FIT_CENTER)
                .into(iv_test10);

        //        ImageLoader.with(this)
        //                .content("content://media/external/images/media/"+getContentId())
        //                .placeHolder(R.mipmap.ic_launcher)
        //                .scale(ScaleMode.FIT_CENTER)
        //                .into(iv_test10);

        ImageLoader.with(getContext())
                .file("file://"+ Environment.getExternalStorageDirectory().getPath()+FOREWARD_SLASH + IMG_NAME)
                .placeHolder(R.mipmap.ic_launcher)
                .scale(ScaleMode.FIT_CENTER)
                .into(iv_test11);


        ImageLoader.with(getContext())
                .file(new File(getActivity().getFilesDir(), IMG_NAME_C))
                .placeHolder(R.mipmap.ic_launcher)
                .scale(ScaleMode.FIT_CENTER)
                .into(iv_test12);

        ImageLoader.with(getContext())
                .asserts(ASSERTS_PATH+IMG_NAME_C)
                .placeHolder(R.mipmap.ic_launcher)
                .scale(ScaleMode.FIT_CENTER)
                .rectRoundCorner(50)
                .into(iv_test13);

        */
/*ImageLoader.with(getContext())
                .raw(ANDROID_RESOURCE + getActivity().getPackageName() + RAW + R.raw.jpeg_test)
                .placeHolder(R.mipmap.ic_launcher)
                .scale(ScaleMode.FIT_CENTER)
                .asCircle()
                .into(iv_test14);*//*


        */
/*ImageLoader.with(getContext())
                .raw(ANDROID_RESOURCE+getActivity().getPackageName()+ RAW + R.raw.jpeg_test)
                .placeHolder(R.mipmap.ic_launcher)
                .scale(ScaleMode.FIT_CENTER)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .asSquare()
                .into(iv_test15);*//*


        */
/*ImageLoader.saveImageIntoGallery(new DownLoadImageService(SimpleActivity.this, URL3, true, "lala", new ImageDownLoadCallBack() {

            @Override
            public void onDownLoadSuccess(Bitmap bitmap) {
                Log.e(TAG, "下载图片成功 bitmap");
            }

            @Override
            public void onDownLoadFailed() {
                Log.e(TAG, "下载图片失败");
            }

        }));*//*

    }

    public long getContentId(){
        Cursor cursor = getContext().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        long aLong = 0;
        if (cursor != null && cursor.moveToFirst()) {
            aLong = cursor.getLong(cursor.getColumnIndex(ContactsContract.Contacts._ID));
        }
        return aLong;
    }

*/


}
