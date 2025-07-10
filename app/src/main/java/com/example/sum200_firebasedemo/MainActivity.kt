package com.example.sum200_firebasedemo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sum200_firebasedemo.ui.theme.SUM200FirebaseDemoTheme
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class MainActivity : ComponentActivity() {
    private var updateMovies = {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            var movies: List<Movie>? by remember { mutableStateOf(null) }

            Firebase.firestore
                .collection("movies")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        Log.d("firestore", "${document.id} => ${document.data}")
                    }
                }


            SUM200FirebaseDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainUI(
                        innerPadding,
                        onCreateClick = {
                            startCreateMovieActivity()
                        },
                        movies = movies,
                    )
                }
            }
        }
    }

    /**
     * Resume the Main Activity and update movies too display any new data
     */
    override fun onResume() {
        super.onResume()
        updateMovies()
    }

    /**
     * Start and navigate to the create movie activity
     */
    private fun startCreateMovieActivity() {
        val intent = Intent(this, CreateMovieActivity::class.java)
        startActivity(intent)
    }
}

@Composable
fun MainUI(
    innerPadding: PaddingValues,
    onCreateClick: () -> Unit,
    movies: List<Movie>?
) {
    Column(
        modifier = Modifier.padding(innerPadding)
    ) {
        Text("Firebase Demo")

        // Button to navigate to CreateMovieActivity
        Button(onClick = onCreateClick) {
            Text("Create Movie")
        }

        // List all movies in the database
        MovieList(movies)
    }
}

@Preview(showBackground = true)
@Composable
fun MainActivityUIPreview() {
    val movies = listOf(
        Movie(title = "Star Wars"),
        Movie(title = "Back to the Future"),
        Movie(title = "Jaws"),
        Movie(title = "Alien"),
        Movie(title = "Terminator"),
    )
    SUM200FirebaseDemoTheme {
        MainUI(
            innerPadding = PaddingValues(),
            onCreateClick = {},
            movies,
        )
    }
}

@Composable
fun MovieList(movies: List<Movie>?) {
    // Display loading message while reading the database
    if (movies == null) {
        Text("Loading...")
        return
    }

    // Display if the database is empty
    if (movies.isEmpty()) {
        Text("No movies available.")
        return
    }

    // Display all movies
    LazyColumn {
        items(movies) { movie ->
            MovieCard(movie)
        }
    }
}

@Composable
fun MovieCard(movie: Movie) {
    Card(
        modifier = Modifier
            .padding(all = 3.0.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = movie.title,
            modifier = Modifier.padding(all = 5.0.dp)
        )
    }
}
