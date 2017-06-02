package com.stats.daqing.feature.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bilibili.magicasakura.widgets.TintToolbar;
import com.stats.daqing.R;
import com.stats.daqing.base.BaseActivity;

public class RegistActivity extends BaseActivity {

    private TintToolbar mToolBar;
    private EditText registerYouxiang;
    private EditText registerPassword;
    private TextView textSelectWt;
    private EditText registerWtda;
    private EditText registerSjh;
    private Button registerSubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        initView();
    }


    private void initView() {
        mToolBar = (TintToolbar) findViewById(R.id.toolbar);
        registerYouxiang = (EditText) findViewById(R.id.register_youxiang);
        registerPassword = (EditText) findViewById(R.id.register_password);
        textSelectWt = (TextView) findViewById(R.id.text_select_wt);
        registerWtda = (EditText) findViewById(R.id.register_wtda);
        registerSjh = (EditText) findViewById(R.id.register_sjh);
        registerSubmit = (Button) findViewById(R.id.register_submit);


        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle(null);
        // 设置返回按钮
        getSupportActionBar().setHomeButtonEnabled(true);
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
