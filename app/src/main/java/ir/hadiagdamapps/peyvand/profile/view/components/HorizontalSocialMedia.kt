package ir.hadiagdamapps.peyvand.profile.view.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.hadiagdamapps.peyvand.R

@Composable
fun HorizontalSocialMedia(
    modifier: Modifier = Modifier,
    telegram: String?,
    instagram: String?,
    x: String?,
    telegramClick: () -> Unit,
    instagramClick: () -> Unit,
    xClick: () -> Unit
) {

    Row(
        modifier
            .height(64.dp)
            .fillMaxWidth()
            .padding(4.dp)
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Button(
            onClick = telegramClick,
            modifier = Modifier
                .padding(2.dp)
                .weight(1f)
                .fillMaxHeight(),
            colors = ButtonDefaults.outlinedButtonColors(
                backgroundColor = Color.Transparent, contentColor = colorResource(R.color.telegram)
            ),
            border = BorderStroke(3.dp, colorResource(R.color.telegram)),
        ) {
            Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Text(telegram ?: "Telegram")
            }
        }


        Button(
            onClick = telegramClick,
            modifier = Modifier
                .padding(2.dp)
                .weight(1f)
                .fillMaxHeight()
        ) {
            Text(telegram ?: "Telegram")
        }

        Button(
            onClick = telegramClick,
            modifier = Modifier
                .padding(2.dp)
                .weight(1f)
                .fillMaxHeight()
        ) {
            Text(telegram ?: "Telegram")
        }

    }

}


@Preview
@Composable
fun HorizontalSocialMediaPreview() {
    Surface {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            HorizontalSocialMedia(
                telegram = "telegram",
                instagram = "instagram",
                x = null,
                telegramClick = {},
                instagramClick = {},
                xClick = {}
            )
        }
    }
}
