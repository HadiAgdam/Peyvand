package ir.hadiagdamapps.peyvand.view.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight

@Composable
fun Title(text: String) {
    Text(text, style = MaterialTheme.typography.h5, fontWeight = FontWeight.Bold)
}