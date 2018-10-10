package com.tangpj.mvvmrecurve;

import android.os.Bundle;

import com.tangpj.recurve.retrofit2.LiveDataCallAdapterFactory;
import com.tangpj.recurve.apollo.LiveDataApollo;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
