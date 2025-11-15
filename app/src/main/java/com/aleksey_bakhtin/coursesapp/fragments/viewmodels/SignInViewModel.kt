package com.aleksey_bakhtin.coursesapp.fragments.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update

class SignInViewModel(): ViewModel() {

    private var _isEnableButton = MutableStateFlow(false)
    var isEnableButton = _isEnableButton.asStateFlow()

    private var email = MutableStateFlow("")
    private var password = MutableStateFlow("")

    val combineFlow = combine(email, password) { email, password ->
        return@combine Pair(email, password)
    }

    fun setEmail(newEmail: String){
        email.update { newEmail }
    }

    fun setPassword(newPassword: String){
        password.update { newPassword }
    }

    fun setEnable(email: String, password: String){
        val emailPattern = Regex(pattern = """^[a-zA-Z0-9]*@[a-zA-Z0-9]+\.[a-zA-Z0-9]+$""")

        val isEnable =
            //emai соответствует маске
            email.matches(emailPattern)
                    //и password не пустой и не содержит пробелов
                    && !(password.isBlank() || password.contains(' '))

        _isEnableButton.update { isEnable }
    }

}