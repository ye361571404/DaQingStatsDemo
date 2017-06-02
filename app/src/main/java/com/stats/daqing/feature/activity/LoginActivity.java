package com.stats.daqing.feature.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bilibili.magicasakura.widgets.TintToolbar;
import com.stats.daqing.R;
import com.stats.daqing.base.BaseActivity;
import com.youth.banner.Banner;

public class LoginActivity extends BaseActivity implements View.OnClickListener {


    private TintToolbar mToolBar;
    private LinearLayout logLl;
    private EditText logUserName;
    private EditText logUserPassword;
    private TextView tvRegist;
    private TextView textWangjiPasswrod;
    private Button logSubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        setListener();
    }

    private void setListener() {
        tvRegist.setOnClickListener(this);
        textWangjiPasswrod.setOnClickListener(this);
    }


    private void initView() {
        mToolBar = (TintToolbar) findViewById(R.id.toolbar);
        logLl = (LinearLayout) findViewById(R.id.log_ll);
        logUserName = (EditText) findViewById(R.id.log_user_name);
        logUserPassword = (EditText) findViewById(R.id.log_user_password);
        tvRegist = (TextView) findViewById(R.id.tv_regist);
        textWangjiPasswrod = (TextView) findViewById(R.id.text_wangji_passwrod);
        logSubmit = (Button) findViewById(R.id.log_submit);

        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle(null);
        // 设置返回按钮
        getSupportActionBar().setHomeButtonEnabled(true);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_regist:
                // 注册
                Intent intent = new Intent(LoginActivity.this, RegistActivity.class);
                startActivity(intent);
                break;

            case R.id.text_wangji_passwrod:
                // 忘记密码

                break;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
