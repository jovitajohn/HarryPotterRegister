package com.jovita.harrypotter.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.jovita.harrypotter.model.MovieCharacter
import com.jovita.harrypotter.ui.theme.BookMarkShape
import com.jovita.harrypotter.ui.theme.Griffindor
import com.jovita.harrypotter.ui.theme.Hufflepuff
import com.jovita.harrypotter.ui.theme.ListCardColor
import com.jovita.harrypotter.ui.theme.Ravenclaw
import com.jovita.harrypotter.ui.theme.Slytherin
import com.jovita.harrypotter.viewmodel.MainViewModel

@Composable
fun ListingScreen(navController: NavHostController, viewModel: MainViewModel) {

    val characters by viewModel.filteredCharacters.collectAsState()
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }


    Column {
        SearchBar(
            searchQuery = searchQuery,
            onSearchQueryChanged = {
                searchQuery = it
                viewModel.filterCharacters(it.text)
            }
        )
        CharacterList(
            characters = characters,
            onCharacterClick = { character ->
                navController.navigate("detail/${character.id}")
            }
        )
    }
}


@Composable
fun SearchBar(searchQuery: TextFieldValue, onSearchQueryChanged: (TextFieldValue) -> Unit) {
    BasicTextField(
        value = searchQuery,
        onValueChange = onSearchQueryChanged,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.LightGray)
            .padding(8.dp),
        decorationBox = { innerTextField ->
            if (searchQuery.text.isEmpty()) {
                Text("Search by name or actor", color = Color.Gray)
            }
            innerTextField()
        }
    )
}

@Composable
fun CharacterList(characters: List<MovieCharacter>, onCharacterClick: (MovieCharacter) -> Unit) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(characters) { character ->
            CharacterRow(character = character, onClick = { onCharacterClick(character) })
        }
    }
}

@Composable
fun CharacterRow(character: MovieCharacter, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier.padding(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        colors = CardDefaults.cardColors(
            containerColor = ListCardColor,
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.weight(1f).padding(15.dp,10.dp,0.dp,0.dp)) {

                character.name?.takeIf { it.isNotEmpty() }?.let { Text(text = it) }
                //character.name?.let { Text(text = it) }
                character.actor?.takeIf { it.isNotEmpty() }?.let { Text(text = it) }
                character.species?.takeIf { it.isNotEmpty() }?.let { Text(text = it) }

            }
            val flag = when (character.house) {
                "Gryffindor" -> Griffindor
                "Slytherin" -> Slytherin
                "Ravenclaw" -> Ravenclaw
                "Hufflepuff" -> Hufflepuff
                else -> Color.Transparent
            }
            //Adding flag to identify house
            Box {
                Box(
                    modifier = Modifier
                        .size(80.dp, 120.dp)
                        .background(flag, shape = BookMarkShape(16.dp))
                )
                Box(
                    modifier = Modifier
                        .size(70.dp)
                        .align(Alignment.Center)
                       /* .offset(x = -30.dp, y = 10.dp) // Adjusted for centering*/
                ){
                    Image(
                        painter = rememberImagePainter(character.image),
                        contentDescription = character.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(350.dp),
                        contentScale = ContentScale.Fit
                    )
                }
            }
        }
    }
}