package com.syntext.error.gitissue.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.syntext.error.gitissue.ui.navigation.AppNavGraph
import com.syntext.error.gitissue.ui.screen.RepoSearchScreen
import com.syntext.error.gitissue.ui.theme.GitIssueTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            GitIssueTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavGraph()
                }
            }
        }
    }
}
