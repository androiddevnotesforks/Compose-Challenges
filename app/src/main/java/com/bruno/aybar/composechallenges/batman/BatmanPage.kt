package com.bruno.aybar.composechallenges.batman

import androidx.compose.animation.core.FloatPropKey
import androidx.compose.animation.core.transitionDefinition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Stack
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.onActive
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.WithConstraints
import androidx.compose.ui.drawLayer
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.font
import androidx.compose.ui.text.font.fontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.ui.tooling.preview.Preview
import com.bruno.aybar.composechallenges.R
import com.bruno.aybar.composechallenges.common.AbsoluteAlignment
import com.bruno.aybar.composechallenges.common.AnimationSequenceStateHolder
import com.bruno.aybar.composechallenges.common.transition

private val batmanColors = darkColors(
    primary = Color(0xFFffe95e),
    surface = Color(0xFF120F0A),
)

private val batmanTypography = Typography(
    defaultFontFamily = fontFamily(font(R.font.vidaloka_regular)),
    h1 = TextStyle(fontSize = 40.sp),
    h2 = TextStyle(fontSize = 20.sp),
    subtitle1 = TextStyle(fontSize = 12.sp, color = Color.Gray)
)

@Composable
fun BatmanTheme(content: @Composable ()->Unit) {
    MaterialTheme(
        colors = batmanColors,
        typography = batmanTypography,
        content = content
    )
}


@Composable
fun BatmanPage() {
    Surface {
        WithConstraints(Modifier.fillMaxSize()) {
            Stack {
                val animationStateHolder = remember {
                    AnimationSequenceStateHolder(
                        listOf(
                            IntroStates.LogoCoveringScreen,
                            IntroStates.LogoCentered,
                            IntroStates.LogoAndHint,
                            IntroStates.Completed
                        )
                    )
                }
                val transition = transition(
                    definition = remember { introTransition(maxHeight.value) },
                    stateHolder = animationStateHolder,
                )

                onActive { animationStateHolder.start() }

                BatmanImage(maxWidth = maxWidth, batmanSizeMultiplier = transition[batmanSizeMultiplier])

                val logoHeight = Dp(transition[batmanLogoHeight]) * 2
                val logoWidth = logoHeight * 2
                BatmanLogo(Modifier
                    .size(width = logoWidth, height = logoHeight)
                    .align(AbsoluteAlignment(verticalBias = transition[batmanLogoVerticalBias]))
                )
                BatmanWelcomeHint(Modifier
                    .align(AbsoluteAlignment(verticalBias = 0.1f))
                    .drawLayer(alpha = transition[welcomeHintAlpha])
                )
                BatmanPageButtons(Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 80.dp)
                    .drawLayer(alpha = transition[buttonsAlpha])
                )
            }
        }
    }
}

private enum class IntroStates {
    LogoCoveringScreen,
    LogoCentered,
    LogoAndHint,
    Completed
}

private val batmanLogoHeight = FloatPropKey()
private val batmanLogoVerticalBias = FloatPropKey()
private val welcomeHintAlpha = FloatPropKey()
private val buttonsAlpha = FloatPropKey()
private val batmanSizeMultiplier = FloatPropKey()

private fun introTransition(maxHeight: Float) = transitionDefinition<IntroStates> {
    state(IntroStates.LogoCoveringScreen) {
        this[batmanLogoHeight] = maxHeight
        this[batmanLogoVerticalBias] = 0f
        this[welcomeHintAlpha] = 0f
        this[buttonsAlpha] = 0f
        this[batmanSizeMultiplier] = 3f
    }
    state(IntroStates.LogoCentered) {
        this[batmanLogoHeight] = 30f
        this[batmanLogoVerticalBias] = 0f
        this[welcomeHintAlpha] = 0f
        this[buttonsAlpha] = 0f
        this[batmanSizeMultiplier] = 3f
    }
    state(IntroStates.LogoAndHint) {
        this[batmanLogoHeight] = 30f
        this[batmanLogoVerticalBias] = -0.2f
        this[welcomeHintAlpha] = 1f
        this[buttonsAlpha] = 0f
        this[batmanSizeMultiplier] = 3f
    }
    state(IntroStates.Completed) {
        this[batmanLogoHeight] = 30f
        this[batmanLogoVerticalBias] = -0.2f
        this[welcomeHintAlpha] = 1f
        this[buttonsAlpha] = 1f
        this[batmanSizeMultiplier] = 1f
    }
    transition(fromState = IntroStates.LogoCoveringScreen, toState = IntroStates.LogoCentered) {
        batmanLogoHeight using tween(durationMillis = 1000, delayMillis = 300)
    }
    transition(fromState = IntroStates.LogoCentered, toState = IntroStates.LogoAndHint) {
        batmanLogoVerticalBias using tween(durationMillis = 2000, delayMillis = 300)
        welcomeHintAlpha using tween(durationMillis = 2000, delayMillis = 300)
    }
    transition(fromState = IntroStates.LogoAndHint, toState = IntroStates.Completed) {
        buttonsAlpha using tween(durationMillis = 2000, delayMillis = 300)
        batmanSizeMultiplier using tween(durationMillis = 2000, delayMillis = 300)
    }
}


@Preview
@Composable
private fun BatmanPagePreview() {
    BatmanTheme {
        BatmanPage()
    }
}

