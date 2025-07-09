package com.example.sum200_firebasedemo

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.sum200_firebasedemo.ui.theme.SUM200FirebaseDemoTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateMovieActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SUM200FirebaseDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CreateMovieUI(
                        innerPadding,
                        onSave = { movie ->
                            save(movie)
                        }
                    )
                }
            }
        }
    }

    /**
     * Save a movie to the database
     */
    private fun save(movie: Movie) {
        // Launch a coroutine
        lifecycleScope.launch {
            // Write to database on the background thread
            AppDatabase.getDatabase(application)
                .movieDao()
                .insertAll(movie)

            // Switch back to main thread to display a toast on the UI
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    this@CreateMovieActivity,
                    "${movie.title} saved!",
                    Toast.LENGTH_SHORT
                ).show()

                // Finish movie creation and resume the main activity
                finish()
            }
        }
    }
}

@Composable
fun CreateMovieUI(innerPadding: PaddingValues, onSave: (movie: Movie) -> Unit) {
    /**
     * Variable to hold a movie title
     */
    var title by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(innerPadding)
    ) {
        Text("Add a movie")

        // User input for movie title
        OutlinedTextField(
            value = title,
            onValueChange =  { value -> title = value },
            label = { Text("Title") }
        )

        // Save button
        Button(onClick = {
            // Create a movie object to save
            val movie = Movie(
                title = title
            )

            // Invoke callback to save the movie
            onSave(movie)

            // Reset title input field to be empty
            title = ""
        }) {
            Text("Save")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateMovieUIPreview() {
    SUM200FirebaseDemoTheme {
        CreateMovieUI (
            innerPadding = PaddingValues(),
            onSave = {}
        )
    }
}