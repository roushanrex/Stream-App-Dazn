package com.streaming.dazn.presentation

import VideoPlayerScreen
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.streaming.dazn.presentation.features.HomeScreen
import com.streaming.dazn.ui.theme.ComposeNewsApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeNewsApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = "home") {
                        composable("home") {
                            HomeScreen(navController)
                        }
                        composable(
                            "videoPlayer/{videoUri}",
                            arguments = listOf(navArgument("videoUri") {
                                type = NavType.StringType
                            })
                        ) { backStackEntry ->
                            val videoUri =
                                Uri.decode(backStackEntry.arguments?.getString("videoUri") ?: "")
                            VideoPlayerScreen(navController, videoUri)
                        }
                    }
                }
            }
        }
    }
}