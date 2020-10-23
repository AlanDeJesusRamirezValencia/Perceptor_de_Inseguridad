package com.example.perceptordeinseguridad.UI;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.PersistableBundle;

import com.example.perceptordeinseguridad.R;

/**
 * App Main activity.
 * This Activity contains the navigation host fragment.
 */
public class DashboardActivity extends AppCompatActivity {

    /**A simple {@link AppCompatActivity#onCreate(Bundle, PersistableBundle)} overwritten method */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    }
}