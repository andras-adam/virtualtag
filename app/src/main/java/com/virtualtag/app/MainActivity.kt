package com.virtualtag.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.virtualtag.app.ui.screens.CardScreen
import com.virtualtag.app.ui.screens.EditScreen
import com.virtualtag.app.ui.screens.HomeScreen
import com.virtualtag.app.ui.screens.ScanScreen
import com.virtualtag.app.ui.theme.VirtualTagTheme
import com.virtualtag.app.viewmodels.CardViewModel

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      // Navigation controller and functions
      val navController = rememberNavController()
      val goBack: () -> Unit = { navController.navigateUp() }
      val viewCard: (id: Int) -> Unit = { navController.navigate("card/$it") }
      // View models
      val model: CardViewModel by viewModels()
      // Render content
      VirtualTagTheme {
        NavHost(navController = navController, startDestination = "home") {
          composable("home") {
            HomeScreen(model = model, viewCard = viewCard)
          }
          composable("scan") {
            ScanScreen(model = model, viewCard = viewCard, goBack = goBack)
          }
          composable("card/{id}") {
            CardScreen(model = model, id = it.arguments?.getInt("id") ?: 0, goBack = goBack)
          }
          composable("edit/{id}") {
            EditScreen(model = model, id = it.arguments?.getInt("id") ?: 0, goBack = goBack)
          }
        }
      }
    }
  }
}
