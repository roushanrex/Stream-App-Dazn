package com.streaming.dazn.screens

import android.net.Uri
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import com.streaming.dazn.data.model.VideoItemDTO
import com.streaming.dazn.presentation.features.HomeScreen
import com.streaming.dazn.presentation.viewmodel.HomeViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever


@RunWith(MockitoJUnitRunner::class)
class HomeScreenTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val composeTestRule = createComposeRule()

    @Mock
    private lateinit var homeViewModel: HomeViewModel

    private lateinit var navController: NavController

    @Before
    fun setup() {
        navController = mock(NavController::class.java)
    }

    @Test
    fun homeScreen_loadingState() {

        val videoItems = emptyList<VideoItemDTO>()
        whenever(homeViewModel.videoItems).thenReturn(videoItems)

        // Act
        composeTestRule.setContent {
            HomeScreen(navController = navController, homeViewModel = homeViewModel)
        }

        composeTestRule.onNodeWithContentDescription("progressBar").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("videoList").assertDoesNotExist()
    }

    @Test
    fun homeScreen_loadedState() {

        val videoItems = listOf(
            VideoItemDTO(
                "video1",
                "https://storage.googleapis.com/wvmedia/clear/h264/tears/tears.mpd"
            ),
            VideoItemDTO(
                "video2",
                "https://storage.googleapis.com/wvmedia/clear/h264/tears/tears_uhd.mpd"
            )
        )
        whenever(homeViewModel.videoItems).thenReturn(videoItems)

        composeTestRule.setContent {
            HomeScreen(navController = navController, homeViewModel = homeViewModel)
        }

        // Assert
        composeTestRule.onNodeWithContentDescription("progressBar").assertDoesNotExist()
        composeTestRule.onNodeWithContentDescription("videoList").assertIsDisplayed()
    }

    @Test
    fun videoItemClick_navigatesToVideoPlayerScreen() {

        val videoItem = VideoItemDTO("video1", "http://example.com/video1")
        val videoItems = listOf(videoItem)
        whenever(homeViewModel.videoItems).thenReturn(videoItems)

        composeTestRule.setContent {
            HomeScreen(navController = navController, homeViewModel = homeViewModel)
        }
        composeTestRule.onNodeWithText(videoItem.name).performClick()

        verify(navController).navigate("videoPlayer/${Uri.encode(videoItem.uri)}")
    }
}
