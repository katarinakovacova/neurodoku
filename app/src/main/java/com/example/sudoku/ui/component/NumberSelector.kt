package com.example.sudoku.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NumberSelector() {
    val selectedNumber = remember { mutableStateOf<Int?>(null) }

    Row(horizontalArrangement = Arrangement.Center) {
        for (number in 1..9) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        if (selectedNumber.value == number) Color.Blue else Color.Gray,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable { selectedNumber.value = number },
                contentAlignment = Alignment.Center
            ) {
                Text(text = number.toString(), fontSize = 20.sp, color = Color.White)
            }
            Spacer(modifier = Modifier.width(4.dp))
        }
    }
}