package com.aleksey_bakhtin.coursesapp.activity

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.aleksey_bakhtin.coursesapp.databinding.ActivityMainBinding
import com.aleksey_bakhtin.coursesapp.utils.StateScreen
import com.aleksey_bakhtin.domain.Result

class MainActivity : AppCompatActivity(), StateScreen {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }

    //Установка состояния экрана в зависимости от полученного результата
    override fun setStateScreen(result: Result) = with(binding){
        when(result) {
            is Result.Success<*> -> {
                layoutLoading.visibility = View.GONE
                progressBar.visibility = View.GONE
                tvError.visibility = View.GONE
            }
            is Result.Error-> {
                layoutLoading.visibility = View.VISIBLE
                progressBar.visibility = View.INVISIBLE
                tvError.visibility = View.VISIBLE
            }
            is Result.Loading -> {
                layoutLoading.visibility = View.VISIBLE
                progressBar.visibility = View.VISIBLE
                tvError.visibility = View.GONE
            }
        }
    }


}