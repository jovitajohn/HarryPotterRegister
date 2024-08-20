package com.jovita.harrypotter.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.jovita.harrypotter.model.MovieCharacter
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
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            character.name?.let { Text(text = it) }
            character.actor?.let { Text(text = it) }
        }
        Box(
            modifier = Modifier
                .size(16.dp)
                .background(
                    when (character.house) {
                        "Gryffindor" -> Color(0xFF740001)
                        "Slytherin" -> Color(0xFF1a472a)
                        "Ravenclaw" -> Color(0xFF0c1a40)
                        "Hufflepuff" -> Color(0xFFeeb939)
                        else -> Color.Transparent
                    }
                )
        )
    }
}