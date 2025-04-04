package com.example.sudoku.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

//@Composable
//fun ActionBar(
//    onErase: () -> Unit,
//    onHint: () -> Unit,
//    onGetNewSudoku: () -> Unit,
//    onRestart: () -> Unit,
//) {
//    Row(
//        horizontalArrangement = Arrangement.Center,
//        modifier = Modifier.fillMaxWidth()
//    ) {
//        ActionButton(text = "Erase", onClick = onErase)
//        Spacer(modifier = Modifier.width(8.dp))
//        ActionButton(text = "Hint", onClick = onHint)
//        Spacer(modifier = Modifier.width(8.dp))
//        ActionButton(text = "Restart", onClick = onRestart)
//        Spacer(modifier = Modifier.width(8.dp))
//        ActionButton(text = "New", onClick = onGetNewSudoku)
//    }
//}
//
//@Composable
//fun ActionButton(text: String, onClick: () -> Unit) {
//    Box(
//        modifier = Modifier
//            .size(80.dp, 40.dp)
//            .background(Color.DarkGray, shape = RoundedCornerShape(8.dp))
//            .clickable { onClick() },
//        contentAlignment = Alignment.Center
//    ) {
//        Text(text = text, fontSize = 16.sp, color = Color.White)
//    }
//}