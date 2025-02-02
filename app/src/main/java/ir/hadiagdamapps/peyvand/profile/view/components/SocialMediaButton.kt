package ir.hadiagdamapps.peyvand.profile.view.components

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.hadiagdamapps.peyvand.R

@Composable
fun SocialMediaButton(
    modifier: Modifier = Modifier,
    text: String?,
    defaultText: String,
    icon: Painter,
    color: Color,
    click: () -> Unit
) {

    Button(
        onClick = click ,
        modifier = modifier
            .fillMaxHeight(),
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = text?.run { color }
                ?: Color.Transparent,
            contentColor = text?.run { Color.White } ?: color
        ),
        border = BorderStroke(3.dp, color),
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            text?.let {
                Icon(
                    painter = icon,
                    contentDescription = null,
                    modifier = Modifier.weight(.3f)
                )
            }
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text ?: defaultText,
                modifier = Modifier.weight(1f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 12.sp
            )
            if (text == null) Icon(painter = painterResource(R.drawable.baseline_add_24), contentDescription = null,
                modifier = Modifier.weight(.3f))
        }
    }

}