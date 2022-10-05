package com.virtualtag.app

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.virtualtag.app.data.ScanningViewModel
import com.virtualtag.app.ui.screens.CardScreen
import com.virtualtag.app.ui.screens.EditScreen
import com.virtualtag.app.ui.screens.HomeScreen
import com.virtualtag.app.ui.screens.ScanScreen
import com.virtualtag.app.ui.theme.VirtualTagTheme
import com.virtualtag.app.viewmodels.CardViewModel

class MainActivity : ComponentActivity() {
    companion object {
        private lateinit var scanningViewModel: ScanningViewModel
        private lateinit var cardViewModel: CardViewModel
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
            val scanCard: () -> Unit = { navController.navigate("scan") }
            val viewCard: (id: Int) -> Unit = { navController.navigate("card/$it") }
            val editCard: (id: Int) -> Unit = { navController.navigate("edit/$it") }
            // Render content
            VirtualTagTheme {
                NavHost(navController = navController, startDestination = "home") {
                    composable("home") {
                        HomeScreen(model = cardViewModel, viewCard = viewCard, scanCard = scanCard)
                    }
                    composable("scan") {
                        ScanScreen(
                            scanningViewModel = scanningViewModel,
                            viewCard = viewCard,
                            goBack = goBack
                        )
                    }
                    composable("card/{id}") {
                        CardScreen(
                            model = cardViewModel,
                            id = it.arguments?.getInt("id") ?: 0,
                            editCard = editCard,
                            goBack = goBack
                        )
                    }
                    composable("edit/{id}") {
                        EditScreen(
                            model = cardViewModel,
                            id = it.arguments?.getInt("id") ?: 0,
                            goBack = goBack
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
}
