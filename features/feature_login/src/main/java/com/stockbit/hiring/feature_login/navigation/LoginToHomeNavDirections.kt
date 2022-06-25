package com.stockbit.hiring.feature_login.navigation

import android.os.Bundle
import androidx.navigation.NavDirections
import com.stockbit.hiring.feature_login.R

class LoginToHomeNavDirections : NavDirections {
    override fun getActionId(): Int = R.id.action_loginFragment_to_homeFragment
    override fun getArguments(): Bundle = Bundle()
}
