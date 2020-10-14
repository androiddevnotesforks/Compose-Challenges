package com.bruno.aybar.composechallenges.batman

import androidx.compose.foundation.BaseTextField
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.drawLayer
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.bruno.aybar.composechallenges.R
import com.bruno.aybar.composechallenges.common.AbsoluteAlignment

@Composable
fun BatmanLogo(modifier: Modifier) {
    Image(asset = imageResource(id = R.drawable.batman_logo), modifier = modifier)
}

@Composable
fun BatmanWelcomeHint(modifier: Modifier) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
        Text(
            text = stringResource(id = R.string.batman_welcome_to),
            style = MaterialTheme.typography.h2
        )
        Text(
            text = stringResource(id = R.string.batman_gotham_city),
            style = MaterialTheme.typography.h1
        )
        Text(
            text = stringResource(id = R.string.batman_need_access),
            style = MaterialTheme.typography.subtitle1
        )
    }
}

enum class BatmanButtonIconPosition { Left, Right }

@Composable
fun BatmanPageButtons(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        BatmanButton(onClick = { }, label = "LOGIN", iconPosition = BatmanButtonIconPosition.Right)
        Spacer(modifier = Modifier.height(16.dp))
        BatmanButton(onClick = { }, label = "SIGNUP", iconPosition = BatmanButtonIconPosition.Left)
    }
}

@Composable
fun BatmanButton(onClick: ()->Unit, label: String, iconPosition: BatmanButtonIconPosition) {
    Button(
        onClick = onClick,
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier.size(width = 300.dp, height = 48.dp)
    ) {
        Stack(Modifier.fillMaxSize()) {
            Text(text = label, modifier = Modifier.align(Alignment.Center))
            Image(
                asset = imageResource(id = R.drawable.batman_logo),
                colorFilter = ColorFilter.tint(Color.Gray.copy(alpha = 0.5f)),
                modifier = Modifier
                    .size(width = 60.dp, height = 30.dp)
                    .align(AbsoluteAlignment(
                        verticalBias = 1.05f,
                        horizontalBias = when(iconPosition) {
                            BatmanButtonIconPosition.Left -> -1.05f
                            BatmanButtonIconPosition.Right -> 1.05f
                        }
                    ))
                    .drawLayer(rotationZ = when(iconPosition) {
                        BatmanButtonIconPosition.Left -> 30f
                        BatmanButtonIconPosition.Right -> -30f
                    }),
            )
        }
    }
}

@Composable
fun BatmanImage(maxWidth: Dp, batmanSizeMultiplier: Float) {
    ConstraintLayout(Modifier.size(maxWidth)) {
        val (backgroundRef, batmanRef) = createRefs()

        Image(
            asset = imageResource(id = R.drawable.batman_background),
            modifier = Modifier
                .size(width = maxWidth, height = maxWidth)
                .constrainAs(backgroundRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            contentScale = ContentScale.FillHeight
        )
        Image(
            asset = imageResource(id = R.drawable.batman_alone),
            modifier = Modifier
                .size(width = maxWidth * batmanSizeMultiplier, height = maxWidth * 1.11f)
                .constrainAs(batmanRef) {
                    bottom.linkTo(backgroundRef.bottom)
                    centerHorizontallyTo(parent)
                }
            ,
            contentScale = ContentScale.FillWidth
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BatmanInput(label: String, displayIcon: Boolean = false) {
//    OutlinedTextField(
//        value = TextFieldValue(""),
//        onValueChange = {},
//        label = { Text(text = label, style = MaterialTheme.typography.caption) },
//        activeColor = MaterialTheme.colors.onSurface,
//        trailingIcon = {
//            if (displayIcon) {
//                Image(
//                    asset = imageResource(R.drawable.batman_logo),
//                    modifier = Modifier.size(width = 40.dp, height = 20.dp)
//                )
//            }
//        }
//    )
    BaseTextField(
        value = TextFieldValue(),
        onValueChange = {  }
    )
}

@Composable
fun BatmanSignUpInputs(modifier: Modifier = Modifier) {
    Column(modifier.preferredWidth(300.dp)) {
        BatmanInput("FULL NAME")
        BatmanInput("EMAIL ID")
        BatmanInput("PASSWORD", displayIcon = true)
    }
}

@Preview
@Composable
private fun ComponentsPreview() {
    BatmanTheme {
        Surface {
            Column(Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                BatmanSignUpInputs()
                Spacer(modifier = Modifier.height(8.dp))
                BatmanPageButtons()
            }
        }
    }
}