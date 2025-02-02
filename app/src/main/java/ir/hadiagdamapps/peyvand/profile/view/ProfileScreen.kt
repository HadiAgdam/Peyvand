package ir.hadiagdamapps.peyvand.profile.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ir.hadiagdamapps.peyvand.R
import ir.hadiagdamapps.peyvand.profile.view.components.ProfilePictureContainer
import ir.hadiagdamapps.peyvand.view.components.Title

@Composable
fun ProfileScreen() {

    val image: String? = null
    val imageClick = {}

    val telegram: String? = null
    val instagram: String? = null
    val x: String? = null

    Surface {
        Column(
            Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Title("Profile")

            Spacer(Modifier.height(48.dp))

            ProfilePictureContainer(imageClick, image)

            Spacer(Modifier.height(48.dp))

            Row (Modifier.fillMaxWidth()) {
                
            }

        }
    }
}


@Preview
@Composable
fun TestFuckingPreview() {
    ProfileScreen()
}