package com.linjiawei.mytestdemo.nicedialog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.linjiawei.mytestdemo.R;
import com.linjiawei.mytestdemo.base.ToolbarBaseActivity;
import com.linjiawei.mytestdemo.tools.AppTools;
import com.othershe.nicedialog.BaseNiceDialog;
import com.othershe.nicedialog.NiceDialog;
import com.othershe.nicedialog.ViewConvertListener;
import com.othershe.nicedialog.ViewHolder;

/**
 *
 * 郭霖大神 NiceDialog弹框框架，是基于DialogFragment实现.
 * 接入及使用流程：https://github.com/Othershe/NiceDialog
 */

public class NiceDialogActivity extends ToolbarBaseActivity {

    @Override
    protected int getContentView() {
        return R.layout.activity_nice_dialog;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        setTitle(getIntent().getExtras().getString(ACTIVITY_TITLE_NAME));
    }

    public void showDialog0(View view) {
        NiceDialog.init().setLayoutId(R.layout.nice_dialog_share_layout)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    public void convertView(ViewHolder holder, final BaseNiceDialog dialog) {
                        holder.setOnClickListener(R.id.wechat, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(NiceDialogActivity.this, "分享成功", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .setDimAmount(0.3f)
                .setShowBottom(true)
                .show(getSupportFragmentManager());
    }

    public void showDialog1(View view) {
        NiceDialog.init()
                .setLayoutId(R.layout.nice_dialog_friend_set_layout)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    public void convertView(ViewHolder holder, final BaseNiceDialog dialog) {

                    }
                })
                .setShowBottom(true)
                .setHeight(310)
                .show(getSupportFragmentManager());
    }

    public void showDialog2(View view) {
        NiceDialog.init()
                .setLayoutId(R.layout.nice_dialog_commit_layout)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    public void convertView(ViewHolder holder, final BaseNiceDialog dialog) {
                        final EditText editText = holder.getView(R.id.edit_input);
                        editText.post(new Runnable() {
                            @Override
                            public void run() {
                                //自己获取输入框的焦点（弹出键盘）
                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.showSoftInput(editText, 0);
                            }
                        });
                        holder.setOnClickListener(R.id.submitCommentBtn, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ToastUtils.showShort(editText.getText().toString());
                            }
                        });
                    }
                })
                .setShowBottom(true)
                .show(getSupportFragmentManager());
    }

    public void showDialog3(View view) {
        NiceDialog.init()
                .setLayoutId(R.layout.nice_dialog_ad_layout)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    public void convertView(ViewHolder holder, final BaseNiceDialog dialog) {
                        holder.setOnClickListener(R.id.close, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                    }
                })
                .setWidth(210)
                .setOutCancel(false)
                .setAnimStyle(R.style.EnterExitAnimation)
                .show(getSupportFragmentManager());
    }


    public void showDialog4(View view) {
        NiceDialog.init()
                .setLayoutId(R.layout.nice_dialog_loading_layout)
                .setWidth(100)
                .setHeight(100)
                .setDimAmount(0)
                //.setOutCancel(false)
                .show(getSupportFragmentManager());
    }

    public void showDialog5(View view) {
        ConfirmDialog.newInstance("1")
                .setMargin(60)
                .setOutCancel(false)
                .show(getSupportFragmentManager());
    }

    public void showDialog6(View view) {
        ConfirmDialog.newInstance("2")
                .setMargin(60)
                .setOutCancel(true)
                .show(getSupportFragmentManager());
    }

    public void openAppClick(View view){
        //通过已知包名的方式启动
    //    AppTools.startOtherApp(NiceDialogActivity.this, "com.clio.selfservice.uat");

        //通过注册scheme的方式向外暴露接口的方式启动
        //详细配置可查看：https://www.cnblogs.com/zhang-cb/p/7093769.html
        if (AppTools.hasInstallApp(this,"sino://escan:8088/news?")) {
            Intent action = new Intent(Intent.ACTION_VIEW,Uri.parse("sino://escan:8088/news?system=android&id=000000"));
            action.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//这种启动模式如果不添加你会发现有时候返回顺序是混乱的，并且不是采用独立启动APP的方式
            startActivity(action);
        } else {
            ToastUtils.showShort("本机没有安装eScan");
        }
    }

    public static class ConfirmDialog extends BaseNiceDialog {
        private String type;

        public static ConfirmDialog newInstance(String type) {
            Bundle bundle = new Bundle();
            bundle.putString("type", type);
            ConfirmDialog dialog = new ConfirmDialog();
            dialog.setArguments(bundle);
            return dialog;
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Bundle bundle = getArguments();
            if (bundle == null) {
                return;
            }
            type = bundle.getString("type");
        }

        @Override
        public int intLayoutId() {
            return R.layout.nice_dialog_confirm_layout;
        }

        @Override
        public void convertView(ViewHolder holder, final BaseNiceDialog dialog) {
            if ("1".equals(type)) {
                holder.setText(R.id.title, "提示");
                holder.setText(R.id.message, "您已支付成功！");
            } else if ("2".equals(type)) {
                holder.setText(R.id.title, "警告");
                holder.setText(R.id.message, "您的账号已被冻结！");
            }
            holder.setOnClickListener(R.id.cancel, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            holder.setOnClickListener(R.id.ok, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }
    }

}
