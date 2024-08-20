@file:Suppress("NAME_SHADOWING")

package com.jovita.harrypotter.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.jovita.harrypotter.model.MovieCharacter
import com.jovita.harrypotter.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun DetailScreen(characterId: String?, viewModel: MainViewModel) {

    val character = viewModel.characters.collectAsState().value.find { it.id == characterId }

    character?.let { character: MovieCharacter ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(character.image),
                contentDescription = character.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.height(16.dp))
            character.name?.let { Text(text = it) }
            Text(text = "Actor: ${character.actor}")
            Text(text = "Species: ${character.species}")
            Text(text = "House: ${character.house ?: "Unknown"}")

            character.dateOfBirth?.let {
                val formattedDate = formatDateString(it)
                Text(text = "Date of Birth: $formattedDate")
            } ?: Text(text = "Date of Birth: Unknown")

            Text(
                text = if (character.alive == true) "Status: Alive" else "Status: Deceased",
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    } ?: run {
        Text(
            text = "Character not found",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        )
    }
}

fun formatDateString(dateString: String): String? {
    return try {
        val inputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val date = inputFormat.parse(dateString)

        val outputFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
        date?.let { outputFormat.format(it) }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}