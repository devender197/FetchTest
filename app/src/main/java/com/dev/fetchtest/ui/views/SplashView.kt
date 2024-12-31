package com.dev.fetchtest.ui.views

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dev.fetchtest.R

private const val SCALE_INITIAL_VALUE = 0.9f
private const val SCALE_TARGET_VALUE = 1f
private const val TRANSITION_DURATION_MS = 1600

@Composable
fun SplashView() {
    val infiniteTransition = rememberInfiniteTransition(label = "infinite")

    val scale = infiniteTransition.animateFloat(
        initialValue = SCALE_INITIAL_VALUE,
        targetValue = SCALE_TARGET_VALUE,
        animationSpec = infiniteRepeatable(tween(TRANSITION_DURATION_MS), RepeatMode.Reverse),
        label = "scale"
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier
                .width(180.dp)
                .height(180.dp)
                .graphicsLayer {
                    scaleX = scale.value
                    scaleY = scale.value
                    transformOrigin = TransformOrigin.Center
                },
            painter = painterResource(id = R.drawable.fetch_logo),
            contentDescription = "Fetch logo"
        )
    }
}

@Preview
@Composable
fun Preview_SplashView() {
    SplashView()
}