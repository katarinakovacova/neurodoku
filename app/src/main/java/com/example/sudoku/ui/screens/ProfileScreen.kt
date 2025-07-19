package com.example.sudoku.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun ProfileScreen() {
    val db = FirebaseFirestore.getInstance()
    val user = FirebaseAuth.getInstance().currentUser
    var name by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }
    var message by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Your Profile", fontSize = 24.sp, modifier = Modifier.padding(bottom = 16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = bio,
            onValueChange = { bio = it },
            label = { Text("Bio") },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        )

        Button(
            onClick = {
                if (user != null) {
                    val profile = hashMapOf(
                        "name" to name,
                        "bio" to bio,
                        "email" to user.email
                    )
                    db.collection("users").document(user.uid)
                        .set(profile)
                        .addOnSuccessListener {
                            message = "Profile saved!"
                        }
                        .addOnFailureListener {
                            message = "Error: ${it.message}"
                        }
                } else {
                    message = "User not logged in."
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Save Profile")
        }

        message?.let {
            Text(it, color = Color.Blue, modifier = Modifier.padding(top = 8.dp))
        }
    }
}
