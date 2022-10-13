package com.virtualtag.app

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.rememberImagePainter
import com.virtualtag.app.data.ScanningViewModel
import com.virtualtag.app.ui.screens.*
import com.virtualtag.app.ui.theme.VirtualTagTheme
import com.virtualtag.app.viewmodels.CameraView
import com.virtualtag.app.viewmodels.CardViewModel
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : ComponentActivity() {
    companion object {
        private lateinit var cardViewModel: CardViewModel
        private lateinit var outputDirectory: File
    }
    private lateinit var scanningViewModel: ScanningViewModel
    private lateinit var cameraExecutor: ExecutorService

    private var shouldShowCamera: MutableState<Boolean> = mutableStateOf(false)

    private lateinit var photoUri: Uri
    private var shouldShowPhoto: MutableState<Boolean> = mutableStateOf(false)

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Log.i("err", "Permission granted")
            shouldShowCamera.value = true
        } else {
            Log.i("err", "Permission denied")
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize ViewModels
        scanningViewModel = ScanningViewModel()
        cardViewModel = CardViewModel(application = application)
        setContent {
            // Navigation controller and functions
            val navController = rememberNavController()
            val goBack: () -> Unit = { navController.navigateUp() }
            val goHome: () -> Unit = {
                navController.navigate("home") {
                    popUpTo(0)
                }
            }
            val scanCard: () -> Unit = { navController.navigate("scan") }
            val addCard: () -> Unit = { navController.navigate("add") }
            val takePhoto: () -> Unit = { navController.navigate("photo") }
            val viewCard: (id: String) -> Unit = { id -> navController.navigate("card/$id") }
            val editCard: (id: String) -> Unit = { id -> navController.navigate("edit/$id") }
            // Render content
            VirtualTagTheme {
                NavHost(navController = navController, startDestination = "home") {
                    composable("home") {
                        HomeScreen(model = cardViewModel, viewCard = viewCard, scanCard = scanCard, takePhoto = takePhoto,)
                    }
                    composable("scan") {
                        ScanScreen(
                            scanningViewModel = scanningViewModel,
                            goBack = goBack,
                            addCard = addCard
                        )
                    }
                    composable("add") {
                        AddScreen(
                            model = cardViewModel,
                            scanningViewModel = scanningViewModel,
                            goHome = goHome,
                            goBack = goBack
                        )
                    }
                    composable("photo") {
                            if (shouldShowCamera.value) {
                                CameraView(
                                    outputDirectory = outputDirectory,
                                    executor = cameraExecutor,
                                    onImageCaptured = ::handleImageCapture,
                                    onError = { Log.e("err", "View error:", it) }
                                )
                            }

                            if (shouldShowPhoto.value) {
                                Image(
                                    painter = rememberImagePainter(photoUri),
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }

                        requestCameraPermission()

                        outputDirectory = getOutputDirectory()
                        cameraExecutor = Executors.newSingleThreadExecutor()

                    }
                    composable(
                        "card/{id}",
                        arguments = listOf(navArgument("id") {
                            type = NavType.StringType
                        }),
                    ) {
                        CardScreen(
                            model = cardViewModel,
                            id = it.arguments?.getString("id") ?: "0",
                            editCard = editCard,
                            goBack = goBack,
                            goHome = goHome
                        )
                    }
                    composable("edit/{id}",
                        arguments = listOf(navArgument("id") {
                            type = NavType.StringType
                        })
                    ) {
                        EditScreen(
                            model = cardViewModel,
                            id = it.arguments?.getString("id") ?: "0",
                            goBack = goBack,
                            goHome = goHome
                        )
                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        scanningViewModel.onActivityNewIntent(intent)
    }

    private fun requestCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                Log.i("err", "Permission previously granted")
                shouldShowCamera.value = true
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.CAMERA
            ) -> Log.i("err", "Show camera permissions dialog")

            else -> requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    private fun handleImageCapture(uri: Uri) {
        Log.i("err", "Image captured: $uri")
        shouldShowCamera.value = false

        photoUri = uri
        shouldShowPhoto.value = true
    }

    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }

        return if (mediaDir != null && mediaDir.exists()) mediaDir else filesDir
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }
}
