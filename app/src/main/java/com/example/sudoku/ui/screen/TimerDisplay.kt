package com.example.sudoku.ui.screen
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//
//@Composable
//fun TimerDisplay(minutes: String, seconds: String, onStop: () -> Unit, onStart: () -> Unit) {
//    Box(
//        modifier = Modifier
//            .clickable {
//                if (minutes == "00" && seconds == "00") {
//                    onStart() // Ak ešte nebeží, spustí sa
//                } else {
//                    onStop() // Ak už beží, zastaví sa
//                }
//            } // Ak klikneš, zastaví sa alebo sa spustí
//            .padding(16.dp)
//            .background(Color.Gray, shape = RoundedCornerShape(8.dp)) // Sivý pozadie
//            .padding(horizontal = 24.dp, vertical = 8.dp) // Väčší padding na stranách
//    ) {
//        Text(
//            text = "$minutes:$seconds", // Formátovaný čas
//            fontSize = 20.sp,
//            fontWeight = FontWeight.Bold,
//            color = Color.White
//        )
//    }
//}