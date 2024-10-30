package com.example.prode_mobile

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    @Inject
    lateinit var manager: BiometricAuthManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen(manager)
        }
    }
}

@Composable
fun MainScreen(biometricAuthManager: BiometricAuthManager) {
    var isAuthenticated by remember { mutableStateOf(false) }
    var authAttempted by remember { mutableStateOf(false) }
    val context = LocalContext.current

    // Manejar la autenticación biométrica
    if (!isAuthenticated && !authAttempted) {
        LaunchedEffect(Unit) {
            biometricAuthManager.authenticate(
                context = context,
                onSuccess = {
                    isAuthenticated = true
                    authAttempted = false
                },
                onError = {
                    Toast.makeText(
                        context,
                        "Authentication error. Please try again.".toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                    authAttempted = false
                },
                onFail = {
                    Toast.makeText(
                        context,
                        "Authentication error. Please try again.".toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                    authAttempted = false
                }
            )
            authAttempted = true
        }
    }

    if (isAuthenticated) {
        val navController = rememberNavController()
        ProdemobileTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Scaffold(
                    bottomBar = {
                        BottomBar { navController.navigate(it) }
                    }
                ) { innerPadding ->
                    NavHostComposable(innerPadding, navController)
                }
            }
        }
    } else {
        BiometricLogin(
            biometricAuthManager = biometricAuthManager,
            onAuthSuccess = { isAuthenticated = true },
            onAuthError = { authAttempted = false }
        )
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun BiometricLogin(
    biometricAuthManager: BiometricAuthManager,
    onAuthSuccess: () -> Unit,
    onAuthError: () -> Unit
) {
    val context = LocalContext.current
    val biometricManager = remember { BiometricManager.from(context) }
    val isBiometricAvailable = remember {
        biometricManager.canAuthenticate(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
    }

    when (isBiometricAvailable) {
        BiometricManager.BIOMETRIC_SUCCESS -> {
            biometricAuthManager.authenticate(
                context,
                onSuccess = {
                    onAuthSuccess()
                },
                onError = {
                    Toast.makeText(context, "Authentication error. Please try again.", Toast.LENGTH_SHORT).show()
                    onAuthError()
                },
                onFail = {
                    Toast.makeText(context, "Authentication failed. Try again.", Toast.LENGTH_SHORT).show()
                    onAuthError()
                }
            )
        }
        BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
            Text(text = "This device doesn't support biometric authentication.")
        }
        BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
            Text(text = "No biometric credentials are enrolled.")
        }
        else -> {
            Text(text = "Biometric authentication is not available.")
        }
    }
}
