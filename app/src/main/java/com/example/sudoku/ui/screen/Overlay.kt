package com.example.sudoku.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

//@Composable
//fun Overlay(onDismiss: () -> Unit) {
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.Black.copy(alpha = 0.7f)) // Polopriesvitné pozadie
//            .clickable { onDismiss() }, // Ak klikneš na overlay, skryje sa
//        contentAlignment = Alignment.Center
//    ) {
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
//        ) {
//            Text(
//                text = "You stopped your Sudoku.",
//                fontSize = 24.sp,
//                fontWeight = FontWeight.Bold,
//                color = Color.White
//            )
//            Spacer(modifier = Modifier.height(16.dp))
//            Button(onClick = { onDismiss() }) {
//                Text("Continue")
//            }
//        }
//    }
//}
