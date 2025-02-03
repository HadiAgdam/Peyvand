package ir.hadiagdamapps.peyvand.profile.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ir.hadiagdamapps.peyvand.R
import ir.hadiagdamapps.peyvand.tools.Picture

@Composable
fun ProfilePictureContainer(
    click: () -> Unit,
    picture: Picture?
) {
    Box(
        Modifier
            .size(128.dp)
            .clickable { click() }
    ) {

        Box(
            Modifier
                .fillMaxSize()
                .clip(CircleShape.copy(CornerSize(128.dp)))) {
            picture?.let {
                picture.toBitmap()?.let {
                    Image(
                        bitmap = it.asImageBitmap(), contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                } ?: AsyncImage(
                    model = picture.urlString,
                    contentDescription = "profile image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } ?: run {
                Image(
                    painter = painterResource(R.drawable.picture_placeholder),
                    contentDescription = "profile placeholder",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        Icon(
            painterResource(R.drawable.camera_white_icon), contentDescription = null,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .clip(CircleShape.copy(CornerSize(24.dp)))
                .background(colorResource(R.color.primary))
                .padding(5.dp),
            tint = Color.White
        )

    }

}