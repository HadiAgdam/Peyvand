package ir.hadiagdamapps.peyvand.profile.view.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ir.hadiagdamapps.peyvand.R

@Composable
fun EditProfileView(
    modifier: Modifier = Modifier,
    title: String,
    icon: Painter,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    Row(modifier.fillMaxWidth().clickable(
        interactionSource = interactionSource,
        indication = rememberRipple(bounded = true),
        onClick = onClick
    )) {

        Icon(
            modifier = Modifier.padding(12.dp),
            painter = icon,
            contentDescription = null
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 16.dp)
        ) {
            Text(title)
            Spacer(Modifier.height(8.dp))
            content()
        }

            Icon(
                modifier = Modifier
                    .padding(12.dp)
                    .size(30.dp)
                    .align(Alignment.CenterVertically),
                painter = painterResource(R.drawable.edit_icon),
                contentDescription = null,
                tint = colorResource(R.color.primary)
            )

    }

}