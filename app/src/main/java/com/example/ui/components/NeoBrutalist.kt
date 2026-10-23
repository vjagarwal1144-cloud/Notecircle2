package com.example.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.AmberAccent
import com.example.ui.theme.BorderDark
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.GlassBorderLight
import com.example.ui.theme.GlassCardLight
import com.example.ui.theme.OutfitFontFamily
import com.example.ui.theme.PastelButterYellow
import com.example.ui.theme.PastelPink
import com.example.ui.theme.PrimaryInk

/**
 * Enhanced Cute Pastel + Soft Neumorphism + Playful Neo-Brutalist Card.
 * Blends frosted translucent glass surface with gentle soft shadows and clean playful borders.
 */
@Composable
fun NeoCard(
    modifier: Modifier = Modifier,
    backgroundColor: Color = GlassCardLight,
    borderColor: Color = BorderDark,
    shadowColor: Color = BorderDark,
    borderWidth: Dp = 1.8.dp,
    shadowOffset: Dp = 3.dp,
    shape: Shape = RoundedCornerShape(20.dp),
    hasTape: Boolean = false,
    tapeText: String? = null,
    tapeColor: Color = PastelButterYellow,
    isGlass: Boolean = false,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .padding(bottom = shadowOffset, end = shadowOffset)
    ) {
        // Soft Neumorphic / Playful offset shadow layer
        Box(
            modifier = Modifier
                .matchParentSize()
                .offset(x = shadowOffset, y = shadowOffset)
                .background(shadowColor.copy(alpha = 0.9f), shape)
        )

        val clickableModifier = if (onClick != null) {
            Modifier.clickable { onClick() }
        } else {
            Modifier
        }

        // Card surface (with frosted glass gradient border if isGlass)
        val borderModifier = if (isGlass) {
            Modifier.border(
                borderWidth,
                Brush.linearGradient(listOf(Color.White.copy(alpha = 0.9f), GlassBorderLight)),
                shape
            )
        } else {
            Modifier.border(borderWidth, borderColor, shape)
        }

        Box(
            modifier = Modifier
                .background(backgroundColor, shape)
                .then(borderModifier)
                .clip(shape)
                .then(clickableModifier)
        ) {
            content()
        }

        // Cute playful masking tape / sticker badge at the top
        if (hasTape) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(y = (-9).dp)
                    .rotate(-2.5f)
                    .background(tapeColor, RoundedCornerShape(4.dp))
                    .border(1.2.dp, BorderDark, RoundedCornerShape(4.dp))
                    .padding(horizontal = 14.dp, vertical = 2.5.dp)
            ) {
                Text(
                    text = tapeText ?: "★ NOTE",
                    fontSize = 9.5.sp,
                    fontFamily = OutfitFontFamily,
                    fontWeight = FontWeight.Black,
                    color = PrimaryInk,
                    letterSpacing = 0.8.sp
                )
            }
        }
    }
}

/**
 * Tactile Bouncy Button with spring press animation and rounded pastel pill design.
 */
@Composable
fun NeoButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = AmberAccent,
    textColor: Color = PrimaryInk,
    borderColor: Color = BorderDark,
    shadowColor: Color = BorderDark,
    shape: Shape = RoundedCornerShape(16.dp),
    borderWidth: Dp = 1.8.dp,
    shadowOffset: Dp = 3.dp,
    testTag: String? = null,
    enabled: Boolean = true,
    content: @Composable () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val currentOffset by animateDpAsState(
        targetValue = if (isPressed && enabled) shadowOffset else 0.dp,
        animationSpec = spring(dampingRatio = 0.6f, stiffness = 900f),
        label = "bouncy_button_offset"
    )

    val scale by animateFloatAsState(
        targetValue = if (isPressed && enabled) 0.97f else 1f,
        animationSpec = spring(dampingRatio = 0.6f, stiffness = 900f),
        label = "bouncy_button_scale"
    )

    Box(
        modifier = modifier
            .defaultMinSize(minWidth = 48.dp, minHeight = 48.dp)
            .scale(scale)
            .padding(bottom = shadowOffset, end = shadowOffset)
    ) {
        // Shadow layer
        Box(
            modifier = Modifier
                .matchParentSize()
                .offset(x = shadowOffset, y = shadowOffset)
                .background(if (enabled) shadowColor else shadowColor.copy(alpha = 0.3f), shape)
        )

        val tagModifier = if (testTag != null) Modifier.testTag(testTag) else Modifier

        Box(
            modifier = Modifier
                .offset(x = currentOffset, y = currentOffset)
                .background(if (enabled) backgroundColor else backgroundColor.copy(alpha = 0.5f), shape)
                .border(borderWidth, borderColor, shape)
                .clip(shape)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    enabled = enabled,
                    onClick = onClick
                )
                .then(tagModifier)
                .padding(horizontal = 18.dp, vertical = 11.dp),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}

/**
 * Text-based overload for NeoButton.
 */
@Composable
fun NeoButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = AmberAccent,
    textColor: Color = PrimaryInk,
    enabled: Boolean = true,
    testTag: String? = null
) {
    NeoButton(
        onClick = onClick,
        modifier = modifier,
        backgroundColor = backgroundColor,
        textColor = textColor,
        enabled = enabled,
        testTag = testTag
    ) {
        Text(
            text = text,
            fontSize = 12.sp,
            fontFamily = OutfitFontFamily,
            fontWeight = FontWeight.Black,
            color = textColor
        )
    }
}

/**
 * Cute Sticker Badge with playful pill curvature and soft pastel fill
 */
@Composable
fun NeoBadge(
    text: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = AmberAccent,
    textColor: Color = PrimaryInk,
    borderColor: Color = BorderDark,
    shape: Shape = RoundedCornerShape(12.dp),
    icon: (@Composable () -> Unit)? = null
) {
    Row(
        modifier = modifier
            .background(backgroundColor, shape)
            .border(1.2.dp, borderColor, shape)
            .padding(horizontal = 9.dp, vertical = 3.5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (icon != null) {
            icon()
            Box(modifier = Modifier.size(4.dp))
        }
        Text(
            text = text,
            color = textColor,
            fontSize = 11.sp,
            fontFamily = OutfitFontFamily,
            fontWeight = FontWeight.Black,
            letterSpacing = 0.3.sp
        )
    }
}

/**
 * Cute Rounded Avatar with sticker badge for presence & status
 */
@Composable
fun NeoAvatar(
    name: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = AmberAccent,
    statusEmoji: String? = null,
    size: Dp = 44.dp
) {
    Box(
        modifier = modifier.size(size)
    ) {
        // Drop shadow
        Box(
            modifier = Modifier
                .size(size)
                .offset(x = 2.dp, y = 2.dp)
                .background(BorderDark, CircleShape)
        )

        // Avatar Face
        Box(
            modifier = Modifier
                .size(size)
                .background(backgroundColor, CircleShape)
                .border(1.8.dp, BorderDark, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            val initial = if (name.isNotBlank()) name.take(1).uppercase() else "N"
            Text(
                text = initial,
                color = PrimaryInk,
                fontFamily = OutfitFontFamily,
                fontWeight = FontWeight.Black,
                fontSize = (size.value * 0.42f).sp
            )
        }

        // Status Emoji Sticker Badge on corner
        if (statusEmoji != null) {
            Box(
                modifier = Modifier
                    .size((size.value * 0.44f).dp)
                    .align(Alignment.BottomEnd)
                    .offset(x = 3.dp, y = 3.dp)
                    .background(Color.White, CircleShape)
                    .border(1.4.dp, BorderDark, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = statusEmoji,
                    fontSize = (size.value * 0.22f).sp
                )
            }
        }
    }
}
