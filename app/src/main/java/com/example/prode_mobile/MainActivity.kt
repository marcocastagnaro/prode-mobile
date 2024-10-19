package com.example.prode_mobile

import android.annotation.SuppressLint
import android.hardware.biometrics.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.prode_mobile.navigation.BottomBar
import com.example.prode_mobile.navigation.NavHostComposable
import com.example.prode_mobile.security.BiometricAuthManager
import com.example.prode_mobile.ui.theme.ProdemobileTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : FragmentActivity(
) {
    @Inject
    lateinit var manager: BiometricAuthManager

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        var auth = false
        setContent {
            MainScreen(manager)
        }
    }
}

@Composable
fun MainScreen (biometricAuthManager: BiometricAuthManager) {
    var isAuthenticated by remember { mutableStateOf(false) }

    if (isAuthenticated) {
        // Show main content if authenticated
        val navController = rememberNavController()
        ProdemobileTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Scaffold(
                    bottomBar = {
                        BottomBar { navController.navigate(it) }
                    },
                ) { innerPadding ->
                    NavHostComposable(innerPadding, navController)
                }
            }
        }
    } else {
        // Attempt biometric authentication
        BiometricLogin(
            biometricAuthManager = biometricAuthManager,
            onAuthSuccess = { isAuthenticated = true },
            onAuthError = { isAuthenticated = false }
        )
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun BiometricLogin(
    biometricAuthManager: BiometricAuthManager,
    onAuthSuccess: () -> Unit,
    onAuthError: () -> Unit
) {    val context = LocalContext.current
    var _isAuthenticated = MutableStateFlow(false)
    val isAuthenticated = _isAuthenticated.asStateFlow()
    val biometricManager = remember { BiometricManager.from(context) }
    val isBiometricAvailable = remember {
        biometricManager.canAuthenticate(BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
    }
    when (isBiometricAvailable) {
        BiometricManager.BIOMETRIC_SUCCESS -> {
            // Biometric features are available
            if(isAuthenticated.value) {
                onAuthSuccess()
            } else {
                biometricAuthManager.authenticate(
                    context,
                    onError = {
                        _isAuthenticated.value = false
                        Toast.makeText(context, "There was an error in the authentication", Toast.LENGTH_SHORT).show()
                    },
                    onSuccess = {
                        _isAuthenticated.value = true
                        onAuthSuccess()
                    },
                    onFail = {
                        _isAuthenticated.value = false
                        Toast.makeText(context, "The authentication failed, try again", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
        BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
            Text(text = "This phone is not prepared for biometric features")
        }
        BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
            // Biometric features are currently unavailable.
            Text(text = "Biometric auth is unavailable")
        }
        BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED -> {
            // Biometric features available but a security vulnerability has been discovered
            Text(text = "You can't use biometric auth until you have updated your security details")
        }
        BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED -> {
            // Biometric features are currently unavailable because the specified options are incompatible with the current Android version..
            Text(text = "You can't use biometric auth with this Android version")
        }
        BiometricManager.BIOMETRIC_STATUS_UNKNOWN -> {
            // Unable to determine whether the user can authenticate using biometrics
            Text(text = "You can't use biometric auth")
        }
        BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
            // The user can't authenticate because no biometric or device credential is enrolled.
            Text(text = "You can't use biometric auth")
        }
    }
}
