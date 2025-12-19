package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.myapplication.viewmodel.LaunchedEffectExampleVM
import com.example.myapplication.viewmodel.ScreenEvents
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filter
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
                //ConstraintLayoutExample()
                AnimationExample()
            }
            // SnackBarExample()
            // LazyColumnExample()
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
fun LazyColumnExample() {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        val randomList = List(1000) { Random.nextInt(0, 10000) }
        itemsIndexed(
            randomList
        ) { index, item ->
            Text(
                text = item.toString()
            )
        }
        items(5000) {

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

@Composable
fun ConstraintLayoutExample() {
    val constraints = ConstraintSet {
        val greenBox = createRefFor("greenBox")
        val redBox = createRefFor("redBox")
        val guideline = createGuidelineFromTop(0.5f)
        constrain(greenBox) {
            top.linkTo(guideline)
            // linking start of green box to parent start
            start.linkTo(parent.start)
            width = Dimension.value(100.dp)
            height = Dimension.value(100.dp)
        }

        constrain(redBox) {
            top.linkTo(greenBox.top)
            start.linkTo(greenBox.end)
            end.linkTo(parent.end)
            width = Dimension.fillToConstraints
            height = Dimension.value(100.dp)
        }
    }

    ConstraintLayout(constraintSet = constraints, modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .background(Color.Green)
                .layoutId("greenBox")
        )
        Box(
            modifier = Modifier
                .background(Color.Red)
                .layoutId("redBox")
        )
    }
}

@Composable
fun AnimationExample() {
    var sizeState by remember { mutableStateOf(200.dp) }
    val size by animateDpAsState(
        targetValue = sizeState,
        label = "",
        animationSpec = tween(
            durationMillis = 1000,
            delayMillis = 100,
            easing = LinearOutSlowInEasing
        )
    )
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val color by infiniteTransition.animateColor(
        initialValue = Color.Cyan,
        targetValue = Color.Yellow,
        animationSpec = InfiniteRepeatableSpec(
            tween(durationMillis = 2000),
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )
    Box(
        modifier = Modifier
            .size(size)
            .background(color),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = {
                sizeState += 20.dp
            }
        ) {
            Text("Increase size")
        }
    }
}

@Composable
fun EffectHandlersExample() {
    // This tells Compose: Watch the variable text.
    // Every time text changes, cancel whatever this block was doing and restart it from the beginning.
    // delay(1000L) This pauses the code for 1,000 milliseconds (1 second)
    val text by remember { mutableStateOf("") }
    LaunchedEffect(key1 = text) {
        delay(1000L)
    }
}

@Composable
fun LaunchedEffectDemo(viewModel: LaunchedEffectExampleVM) {
    LaunchedEffect(
        key1 = true
    ) {
        viewModel.sharedFlow.collect { event ->
            when (event) {
                is ScreenEvents.ShowSnackBar -> {

                }

                is ScreenEvents.Navigate -> {

                }
            }

        }
    }

}

@Composable
fun RememberUpdatedStateExample(onTimeout: () -> Unit) {
    // This code ensures that a long-running timer calls the latest version of a function,
    // without restarting the timer every time that function changes.
    val updatedOnTimeout by rememberUpdatedState(newValue = onTimeout)
    LaunchedEffect(key1 = true) {
        delay(3000L)
        updatedOnTimeout()
    }
}


/*
* This code handles cleanup. It ensures that when your Composable screen is destroyed
* (user navigates away), you stop listening to lifecycle events to prevent memory leaks.

The Use Case: A Video Player
Imagine you have a screen that plays a video.

Behavior: When the user minimizes the app (puts it in the background), the video must pause.

Problem: If you don't clean up your listener, the app might try to pause a video player that no
longer exists after the user closes the screen, causing a crash or memory leak.

How this code handles it:
Start Listening (DisposableEffect): When the Composable enters the screen, it attaches an observer
to the Lifecycle. It says, "Tell me whenever the app pauses or resumes."

React to Events (observer): Inside the observer, it checks if (event == Lifecycle.Event.ON_PAUSE).
In a real app, this is where you would call videoPlayer.pause().

Stop Listening (onDispose): This is the critical part. When the user hits the "Back" button and the
Composable is removed from the screen, onDispose triggers. It removes the observer.
This ensures the component is clean and doesn't leave any "zombie" listeners behind.
* */
@Composable
fun DisposableEffectExample() {
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_PAUSE) {
                println("Paused")
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}


/*
* Called when composable is successfully recomposed
*
* This code allows you to run a block of code after every successful recomposition, ensuring that
* the "outside world" (non-Compose code) stays in sync with your Compose UI.

What it does
Waits for Success: It only runs if the UI was successfully built and displayed.
If the composition fails or is cancelled mid-way, this block is skipped.

Runs Every Time: Unlike LaunchedEffect (which runs only once or when keys change),
SideEffect runs every single time the parent Composable recomposes.

The Real-World Use Case: Updating System Status Bars
The most common use case for SideEffect is updating properties that belong to the standard Android
system (not Compose), such as the Status Bar Color.

Imagine you have a Theme switch (Light Mode / Dark Mode).

Compose: handles changing the background color of your app easily.

System: The little bar at the top with the battery icon is not part of Compose.
It is an "external" Android window property.

You use SideEffect to tell the Android System to change the status bar color whenever your
Compose Theme changes.

The Code Example
Kotlin

@Composable
fun ThemeAwareScreen(isDarkTheme: Boolean) {
    // 1. Determine the color based on Compose state
    val statusBarColor = if (isDarkTheme) Color.Black else Color.White

    // 2. Get the standard Android System UI controller
    val systemUiController = rememberSystemUiController()

    // 3. Sync Compose state with the Android System
    SideEffect {
        // This runs AFTER every frame is drawn.
        // It ensures that if the user toggles the theme, the status bar
        // INSTANTLY updates to match the new color.
        systemUiController.setSystemBarsColor(statusBarColor)
    }

    // ... Rest of your UI ...
}
Why not use LaunchedEffect?
LaunchedEffect is asynchronous: It launches a coroutine. There might be a tiny delay (1 frame)
where your app turns Dark Mode but the status bar is still Light Mode.

SideEffect is synchronous: It runs immediately after the frame is committed,
ensuring no visual glitch or "flicker" between the app UI and the system UI.
* */
@Composable
fun SideEffectExample(nonComposeCounter: Int) {
    SideEffect {
        println("Called when composable is successfully recomposed")
    }
}

/*
* Think of produceState as a wrapper that combines remember { mutableStateOf(...) } and LaunchedEffect into one clean package.

Launch: It creates a coroutine automatically when the Composable enters the screen.

The value property: Inside the block, you have access to a special variable called value.
Whenever you assign something to value (value++), it updates the State.

Cleanup: If this Composable leaves the screen, the coroutine (the while loop) is automatically cancelled.

Use produceState when you need to convert non-Compose state into Compose State.

Example: Listening to a specific Firebase callback or a WebSocket and updating the UI every time a message arrives.

Note: If you are just collecting a standard Kotlin Flow, use the simpler flow.collectAsStateWithLifecycle() instead.
* */
@Composable
fun ProduceStateExample(countUpTo: Int): State<Int> {
    return produceState(initialValue = 0) {
        while (value < countUpTo) {
            delay(1000L)
            value += 1
        }
    }
}


/*
* You should use derivedStateOf when you have High Frequency Inputs (changing constantly) but Low Frequency Outputs (changing rarely).
* It acts as a Buffer or a Filter. It stops the UI from recomposing too often.
*
* The Perfect Use Case: "Scroll to Top" Button
* Imagine a list with 1000 items. You want to show a "Jump to Top" button only when the user has scrolled past the first item.
* Without derivedStateOf (Bad):
Kotlin
val listState = rememberLazyListState()
// BAD: This line runs every single pixel the user scrolls!
// If the user scrolls 100 pixels, your whole screen recomposes 100 times.
val showButton = listState.firstVisibleItemIndex > 0
*
*
With derivedStateOf (Good):
Kotlin
val listState = rememberLazyListState()
// GOOD: The logic runs every pixel, BUT...
// 'showButton' only updates when the RESULT changes from false to true.
val showButton by remember {
    derivedStateOf {
        listState.firstVisibleItemIndex > 0
    }
}
*
* */
@Composable
fun DerivedStateExample() {
    // Compose re-executed the function, causing the string counterText to be re-calculated with the new number.
//    var counter by remember { mutableStateOf(0) }
//    val counterText = "The counter is $counter"
//    Button(
//        onClick = {
//            counter++
//        }
//    ) {
//        Text(text = counterText)
//    }

    // Better way for the above code
    var counter by remember { mutableStateOf(0) }
    val counterText by remember { derivedStateOf { "The counter is $counter" } }
    Button(
        onClick = {
            counter++
        }
    ) {
        Text(text = counterText)
    }
}


/*
*
* snapshotFlow is a bridge that converts Compose State objects (like mutableStateOf or derivedStateOf)
* into a standard Kotlin Flow.

This is incredibly useful when you want to use Flow operators (like .filter, .debounce, or .distinctUntilChanged)
* on your Compose state variables.
*
*
*
* Use Case: Analytics on Scroll
* Imagine you want to log an analytics event ("User passed item #10") or load more data, but
* you don't want to spam the server every single pixel the user scrolls.
* You use snapshotFlow to observe the listState and only act when the index changes.
*
* @Composable
fun SnapshotFlowExample() {
    val listState = rememberLazyListState()

    // We want to detect when the user scrolls past index 10
    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .map { index -> index > 10 }
            .distinctUntilChanged() // Only emit when the boolean FLIPS (false -> true)
            .filter { it == true }  // Only care when it becomes TRUE
            .collect {
                println("Analytics: User has scrolled past item 10!")
                // valid use case: viewModel.loadNextPage()
            }
    }

    LazyColumn(state = listState) {
        items(100) { index ->
            Text(
                text = "Item #$index",
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
*
* snapshotFlow { ... }: This block runs whenever the state read inside it (listState.firstVisibleItemIndex) changes.
* It converts that changing integer into a Flow stream.

distinctUntilChanged(): This is the power move. Even if the user scrolls 50 pixels,
* if the index is still 5, this operator prevents downstream events. It filters out noise.

collect: This runs in the coroutine scope (from LaunchedEffect). It's where you perform your side
* effect (API call, Analytics, Toast, etc.).
*
* When to use it?
Form Validation: Validate a field only after the user stops typing for 500ms (.debounce(500)).

Scroll & Pagination: Trigger "Load More" when the user reaches the end of a list.

System Changes: Reacting to permission changes or window size classes efficiently.
* */
@Composable
fun EasySnapshotFlowExample() {
    // 1. Standard Compose State
    var count by remember { mutableIntStateOf(0) }

    // 2. The Bridge (snapshotFlow)
    LaunchedEffect(Unit) {
        // "Watch 'count'. Every time it changes, emit the new number."
        snapshotFlow { count }
            .filter { it == 10 } // Only let the number 10 pass through
            .collect {
                // This code runs ONLY when count becomes 10
                Log.d("TAG", "Bingo! You hit the target.")
            }
    }

    // 3. Simple UI to change the number
    Button(onClick = { count++ }) {
        Text("Count is $count")
    }
}