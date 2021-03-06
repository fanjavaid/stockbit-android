package com.stockbit.hiring.feature_login.ui

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.text.method.TransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.stockbit.common.base.BaseFragment
import com.stockbit.common.base.BaseViewModel
import com.stockbit.hiring.feature_login.R
import com.stockbit.hiring.feature_login.databinding.FragmentLoginBinding
import com.stockbit.model.User
import org.koin.android.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment() {

    private lateinit var binding: FragmentLoginBinding
    private val loginViewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun getViewModel(): BaseViewModel = loginViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            toolbar.inflateMenu(R.menu.main_menu)
            passwordVisibilityIcon.setOnClickListener {
                val isPasswordVisible = passwordEditText.transformationMethod.isPasswordVisible()
                passwordEditText.transformationMethod = getTransformationMethod(!isPasswordVisible)
                passwordEditText.setSelection(passwordEditText.text?.length ?: 0)
                passwordVisibilityIcon.setImageResource(
                    if (isPasswordVisible) {
                        R.drawable.ic_visibility_off
                    } else R.drawable.ic_visibility
                )
            }
            loginButton.setOnClickListener {
                loginViewModel.login(
                    User(
                        emailAddress = usernameEmailEditText.text.toString(),
                        password = passwordEditText.text.toString()
                    )
                )
            }
        }
    }

    private fun TransformationMethod.isPasswordVisible(): Boolean {
        return this == HideReturnsTransformationMethod.getInstance()
    }

    private fun getTransformationMethod(isShow: Boolean): TransformationMethod? {
        return if (isShow) {
            HideReturnsTransformationMethod.getInstance()
        } else PasswordTransformationMethod.getInstance()
    }

    companion object {
        private const val TAG = "LoginFragment"
    }
}
