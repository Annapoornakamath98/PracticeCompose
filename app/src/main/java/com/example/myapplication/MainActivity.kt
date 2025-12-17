package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fontFamily = FontFamily(
            Font(R.font.lexend_bold, FontWeight.Bold),
            Font(R.font.lexend_light, FontWeight.Light),
            Font(R.font.lexend_black, FontWeight.Black),
            Font(R.font.lexend_semibold, FontWeight.SemiBold)
        )
        setContent {
            Column(
                modifier = Modifier.verticalScroll(
                    enabled = true,
                    state = rememberScrollState()
                )
            ) {
                AnnotatedText(fontFamily)
                ImageCard(
                    painter = painterResource(R.drawable.bird),
                    contentDesc = "Kingfisher",
                    title = "This is a Kingfisher bird.",
                    fontFamily = fontFamily
                )
                ColorBox(modifier = Modifier)
            }
           // SnackBarExample()
        }
    }
}

@Composable
fun ImageCard(
    painter: Painter,
    contentDesc: String,
    title: String,
    modifier: Modifier = Modifier,
    fontFamily: FontFamily
) {
    Box(
        modifier = Modifier
            .fillMaxSize(0.5f)
            .padding(12.dp)
    ) {
        Card(
            modifier = modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            )
        ) {
            Box(modifier = Modifier.height(200.dp)) {
                Image(
                    painter = painter,
                    contentDescription = contentDesc,
                    contentScale = ContentScale.Crop
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black
                                ),
                                startY = 300f
                            )
                        )
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Text(
                        text = title,
                        color = Color.White,
                        fontSize = 16.sp,
                        style = MaterialTheme.typography.bodyLarge,
                        fontFamily = fontFamily
                    )
                }
            }

        }
    }
}

@Composable
fun AnnotatedText(fontFamily: FontFamily) {
    Text(
        text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = Color.Blue,
                    fontSize = 50.sp
                )
            ) {
                append("Bird")
            }
            append(" app")
        },
        color = Color.Black,
        fontSize = 50.sp,
        style = MaterialTheme.typography.bodyLarge,
        fontFamily = fontFamily
    )
}

@Composable
fun ColorBox(modifier: Modifier = Modifier) {
    var color by remember { mutableStateOf(Color.Yellow) }

    Box(modifier = modifier
        .padding(40.dp)
        .background(color = color)
        .clickable {
            color = Color(
                Random.nextFloat(),
                Random.nextFloat(),
                Random.nextFloat(),
                1f
            )
        }) {
        Text(text = "Click to change color", modifier = modifier.padding(16.dp))
    }
}

@Composable
fun SnackBarExample() {
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var textEntered by remember { mutableStateOf("") }
    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
            ) {
            TextField(
                value = textEntered,
                label = {
                    Text("Enter your name")
                },
                onValueChange = {
                    textEntered = it
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    scope.launch {
                        snackBarHostState.showSnackbar(
                            message = "Hello $textEntered",
                            duration = SnackbarDuration.Long
                        )
                    }
                }
            ) {
                Text("Click!")
            }
        }
    }
}