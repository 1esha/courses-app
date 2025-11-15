package com.aleksey_bakhtin.coursesapp.fragments

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.aleksey_bakhtin.coursesapp.R
import com.aleksey_bakhtin.coursesapp.databinding.FragmentSignInBinding
import com.aleksey_bakhtin.coursesapp.fragments.viewmodels.SignInViewModel
import com.aleksey_bakhtin.coursesapp.utils.EmailMask
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    private val viewModel by  viewModels<SignInViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.isEnableButton.collect { isEnable ->

                    binding.bSignIn.isEnabled = isEnable

                    val green = ContextCompat.getColor(requireContext(),R.color.primary)
                    val gray = ContextCompat.getColor(requireContext(),R.color.background_accent)

                    binding.bSignIn.setBackgroundColor(
                        if (isEnable) green
                        else gray
                    )

                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.combineFlow.collectLatest {
                    val email = it.first
                    val password = it.second

                    viewModel.setEnable(
                        email = email,
                        password = password
                    )
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding){

        etEmail.filters += EmailMask() { text ->
            viewModel.setEmail(newEmail = text)
        }

        etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.setPassword(newPassword = text.toString())
            }

            override fun afterTextChanged(editable: Editable?) {}
        })

        bSignIn.setOnClickListener {
        }

       bVk.setOnClickListener {
           try {
               val intent = Intent(Intent.ACTION_VIEW, URL_VK.toUri())
               startActivity(intent)
           }
           catch (e: Exception){
               Log.e("TAG",e.stackTraceToString())
           }
       }

        bOk.setOnClickListener {
            try {
                val intent = Intent(Intent.ACTION_VIEW, URL_OK.toUri())
                startActivity(intent)
            }
            catch (e: Exception){
                Log.e("TAG",e.stackTraceToString())
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val URL_VK = "https://vk.com/"
        private const val URL_OK = "https://ok.ru/"
    }

}