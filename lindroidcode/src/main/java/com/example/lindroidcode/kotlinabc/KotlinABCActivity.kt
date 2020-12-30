package com.example.lindroidcode.kotlinabc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.lindroidcode.R
import com.example.lindroidcode.databinding.ActivityKotlinABCBinding
import kotlinx.android.synthetic.main.activity_kotlin_a_b_c.*

class KotlinABCActivity : AppCompatActivity() {

    private lateinit var mViewBinding: ActivityKotlinABCBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewBinding = ActivityKotlinABCBinding.inflate(layoutInflater);
        val view: View = mViewBinding.root
        setContentView(view)

        tv_kt_string

        et_kotlin.setText("et_kotlin.text = string trigger Type mismatch: inferred type is String but Editable! was expected")
    }
}