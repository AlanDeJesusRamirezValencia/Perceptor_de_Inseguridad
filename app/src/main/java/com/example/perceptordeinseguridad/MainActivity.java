package com.example.perceptordeinseguridad;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.PersistableBundle;

/**
 * App Main activity.
 * This Activity contains the navigation host fragment.
 */
public class MainActivity extends AppCompatActivity {

    /**A simple {@link AppCompatActivity#onCreate(Bundle, PersistableBundle)} overwritten method */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}