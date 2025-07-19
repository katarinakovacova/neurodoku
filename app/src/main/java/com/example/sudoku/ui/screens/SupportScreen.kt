package com.example.sudoku.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun SupportScreen() {
    val db = FirebaseFirestore.getInstance()
    val user = FirebaseAuth.getInstance().currentUser
    var subject by remember { mutableStateOf("") }
    var messageText by remember { mutableStateOf("") }
    var status by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Support Request", fontSize = 24.sp, modifier = Modifier.padding(bottom = 16.dp))

        OutlinedTextField(
            value = subject,
            onValueChange = { subject = it },
            label = { Text("Subject") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = messageText,
            onValueChange = { messageText = it },
            label = { Text("Message") },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        )

        Button(
            onClick = {
                if (user != null) {
                    val supportMessage = hashMapOf(
                        "subject" to subject,
                        "message" to messageText,
                        "userEmail" to user.email,
                        "userId" to user.uid,
                        "timestamp" to FieldValue.serverTimestamp()
                    )
                    db.collection("support_requests")
                        .add(supportMessage)
                        .addOnSuccessListener {
                            status = "Support request sent!"
                            subject = ""
                            messageText = ""
                        }
                        .addOnFailureListener {
                            status = "Error: ${it.message}"
                        }
                } else {
                    status = "User not logged in."
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Send Request")
        }

        status?.let {
            Text(it, color = Color.Blue, modifier = Modifier.padding(top = 8.dp))
        }
    }
}
