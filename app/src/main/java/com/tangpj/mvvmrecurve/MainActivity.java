package com.tangpj.mvvmrecurve;

import android.os.Bundle;
import android.widget.Button;

import com.tangpj.recurve.databinding.ActivityRecurveBinding;
import com.tangpj.recurve.databinding.ToolbarCollapsingRecurveBinding;
import com.tangpj.recurve.retrofit2.LiveDataCallAdapterFactory;
import com.tangpj.recurve.apollo.LiveDataApollo;
import com.tangpj.recurve.ui.activity.RecurveActivity;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends RecurveActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityRecurveBinding binding = initContentBinding(R.layout.activity_recurve);
    }
}
