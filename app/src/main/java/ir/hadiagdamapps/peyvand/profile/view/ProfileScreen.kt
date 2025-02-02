package ir.hadiagdamapps.peyvand.profile.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.hadiagdamapps.peyvand.R
import ir.hadiagdamapps.peyvand.data.models.social_media.SocialMedia
import ir.hadiagdamapps.peyvand.profile.view.components.EditProfileView
import ir.hadiagdamapps.peyvand.profile.view.components.ProfilePictureContainer
import ir.hadiagdamapps.peyvand.profile.view.components.SocialMediaButton
import ir.hadiagdamapps.peyvand.view.components.Title

@Composable
fun ProfileScreen() {

    val image: String? = null
    val imageClick = {}

    val telegram: String? = null
    val instagram: String? = null
    val x: String? = null
    val socialMediaClick: (SocialMedia) -> Unit = {}

    val name: String = "Hadi Agdam"
    val bio: String = "This is Bio."
    val tel: String = "09012345678"

    val nameClick = {}
    val bioClick = {}
    val telClick = {}

    Surface {
        Column(
            Modifier
                .fillMaxSize()
                .padding(vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Title("Profile")

            Spacer(Modifier.height(48.dp))

            ProfilePictureContainer(imageClick, image)

            Spacer(Modifier.height(48.dp))

            Row(
                Modifier
                    .height(64.dp)
                    .fillMaxWidth()
                    .padding(4.dp)
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                SocialMediaButton(
                    modifier = Modifier.weight(1f),
                    text = telegram,
                    defaultText = stringResource(R.string.telegram),
                    icon = painterResource(R.drawable.telegram_icon),
                    color = colorResource(R.color.telegram),
                    click = { socialMediaClick(SocialMedia.TELEGRAM) }
                )

                Spacer(modifier = Modifier.width(4.dp))

                SocialMediaButton(
                    modifier = Modifier.weight(1f),
                    text = instagram,
                    defaultText = stringResource(R.string.instagram),
                    icon = painterResource(R.drawable.instagram_icon),
                    color = colorResource(R.color.instagram),
                    click = { socialMediaClick(SocialMedia.INSTAGRAM) }
                )
                Spacer(modifier = Modifier.width(4.dp))

                SocialMediaButton(
                    modifier = Modifier.weight(1f),
                    text = x,
                    defaultText = "X(Twitter)",
                    icon = painterResource(R.drawable.x_icon),
                    color = colorResource(R.color.x),
                    click = { socialMediaClick(SocialMedia.X) }
                )
            }


            Spacer(Modifier.height(48.dp))

            EditProfileView(
                title = stringResource(R.string.edit_name_title),
                icon = painterResource(R.drawable.account_icon),
                onClick = nameClick
            ) {
                Text(name, style = MaterialTheme.typography.h6, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Spacer(Modifier.height(8.dp))
                Text(stringResource(R.string.edit_name_description), style = MaterialTheme.typography.caption)
            }

            Divider(Modifier.fillMaxWidth())

            EditProfileView(
                title = stringResource(R.string.bio_title),
                icon = painterResource(R.drawable.baseline_info_outline_24),
                onClick = bioClick
            ) {
                Text(bio, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.body1)
            }

            Divider(Modifier.fillMaxWidth())

            EditProfileView(
                title = stringResource(R.string.tel_title),
                icon = painterResource(R.drawable.call_icon),
                onClick = telClick
            ) {
                Text(tel, fontSize = 20.sp, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.body1)
            }

        }
    }
}


@Preview
@Composable
fun ProfileScreenPreview() {
    ProfileScreen()
}