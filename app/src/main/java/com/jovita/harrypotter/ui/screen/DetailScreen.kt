@file:Suppress("NAME_SHADOWING")

package com.jovita.harrypotter.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.jovita.harrypotter.model.MovieCharacter
import com.jovita.harrypotter.ui.theme.BookMarkShape
import com.jovita.harrypotter.ui.theme.Griffindor
import com.jovita.harrypotter.ui.theme.Hufflepuff
import com.jovita.harrypotter.ui.theme.ListCardColor
import com.jovita.harrypotter.ui.theme.Ravenclaw
import com.jovita.harrypotter.ui.theme.Slytherin
import com.jovita.harrypotter.viewmodel.MainViewModel
import com.jovita.myapplication.R
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun DetailScreen(characterId: String?, viewModel: MainViewModel) {

    val character = viewModel.characters.collectAsState().value.find { it.id == characterId }
    val scrollState = rememberScrollState()

    character?.let { character: MovieCharacter ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState)
        ) {

            // Use rememberImagePainter to handle image loading with a placeholder
            val painter = rememberImagePainter(
                ImageRequest.Builder(LocalContext.current)
                    .data(character.image)
                    .placeholder(R.drawable.placeholder) // Set placeholder
                    .error(R.drawable.placeholder) // Set error image
                    .build()
            )

            Image(
                painter = painter,
                contentDescription = character.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.height(16.dp))

            Card(
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.CenterHorizontally),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                colors = CardDefaults.cardColors(
                    containerColor = ListCardColor,
                )
            ) {
                Row {
                    Column(modifier = Modifier.weight(1f).padding(20.dp)){
                        character.name?.let { Text(text = it,fontWeight = FontWeight.Bold) }
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
                    val flag = when (character.house) {
                        "Gryffindor" -> Griffindor
                        "Slytherin" -> Slytherin
                        "Ravenclaw" -> Ravenclaw
                        "Hufflepuff" -> Hufflepuff
                        else -> Color.Transparent
                    }
                    Box(
                        modifier = Modifier
                            .size(80.dp, 120.dp)
                            .padding(0.dp,0.dp,5.dp,0.dp)
                            .background(flag, shape = BookMarkShape(16.dp))
                    )

                }
            }
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

        val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        date?.let { outputFormat.format(it) }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}