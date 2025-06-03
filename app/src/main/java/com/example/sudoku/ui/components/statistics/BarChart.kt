package com.example.sudoku.ui.components.statistics
//
//import androidx.compose.foundation.Canvas
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.geometry.Offset
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
//import androidx.compose.ui.graphics.nativeCanvas
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//
//@Composable
//fun BarChart(
//    data: Map<String, Int>,
//    modifier: Modifier = Modifier,
//    barColor: Color = MaterialTheme.colorScheme.primary,
//    axisColor: Color = Color.Gray,
//    labelColor: Color = Color.Black
//) {
//    if (data.isEmpty()) {
//        Text("No data", modifier = modifier)
//        return
//    }
//
//    val maxValue = (data.values.maxOrNull() ?: 1).coerceAtLeast(1)
//
//    Box(modifier = modifier.padding(16.dp)) {
//        Canvas(modifier = Modifier.fillMaxSize()) {
//            val canvasWidth = size.width
//            val canvasHeight = size.height
//
//            val leftPadding = 60f
//            val bottomPadding = 80f
//            val topPadding = 40f
//            val rightPadding = 40f
//
//            val availableWidth = canvasWidth - leftPadding - rightPadding
//            val availableHeight = canvasHeight - topPadding - bottomPadding
//
//            val barCount = data.size
//            val barWidth = availableWidth / (barCount * 2)
//            val spacing = barWidth
//
//            // Y-axis
//            drawLine(
//                color = axisColor,
//                start = Offset(leftPadding, topPadding),
//                end = Offset(leftPadding, canvasHeight - bottomPadding),
//                strokeWidth = 2f
//            )
//
//            // X-axis
//            drawLine(
//                color = axisColor,
//                start = Offset(leftPadding, canvasHeight - bottomPadding),
//                end = Offset(canvasWidth - rightPadding, canvasHeight - bottomPadding),
//                strokeWidth = 2f
//            )
//
//            // Gridlines + Y-axis labels
//            val steps = 5
//            val stepHeight = availableHeight / steps
//            for (i in 0..steps) {
//                val y = topPadding + i * stepHeight
//                val value = (maxValue.toFloat() / steps) * (steps - i)
//                val label = String.format("%.1f", value)
//
//                // horizontal gridline
//                drawLine(
//                    color = axisColor.copy(alpha = 0.3f),
//                    start = Offset(leftPadding, y),
//                    end = Offset(canvasWidth - rightPadding, y),
//                    strokeWidth = 1f
//                )
//
//                // Y-axis label
//                drawIntoCanvas { canvas ->
//                    val paint = android.graphics.Paint().apply {
//                        textSize = 24f
//                        color = android.graphics.Color.DKGRAY
//                        textAlign = android.graphics.Paint.Align.RIGHT
//                        isAntiAlias = true
//                    }
//                    canvas.nativeCanvas.drawText(label, leftPadding - 10f, y + 8f, paint)                }
//            }
//
//            // Bars + Labels
//            data.entries.forEachIndexed { index, entry ->
//                val valueRatio = entry.value.toFloat() / maxValue
//                val barHeight = valueRatio * availableHeight
//
//                val x = leftPadding + spacing + index * (barWidth + spacing)
//                val y = (canvasHeight - bottomPadding) - barHeight
//
//                // bar
//                drawRect(
//                    color = barColor,
//                    topLeft = Offset(x, y),
//                    size = androidx.compose.ui.geometry.Size(barWidth, barHeight)
//                )
//
//                // value above bar
//                drawIntoCanvas { canvas ->
//                    val paint = android.graphics.Paint().apply {
//                        textSize = 26f
//                        color = android.graphics.Color.BLACK
//                        textAlign = android.graphics.Paint.Align.CENTER
//                        isAntiAlias = true
//                    }
//                    canvas.nativeCanvas.drawText("${entry.value}", x + barWidth / 2, y - 10f, paint)
//                }
//
//                // category label under bar
//                drawIntoCanvas { canvas ->
//                    val paint = android.graphics.Paint().apply {
//                        textSize = 24f
//                        color = android.graphics.Color.DKGRAY
//                        textAlign = android.graphics.Paint.Align.CENTER
//                        isAntiAlias = true
//                    }
//                    canvas.nativeCanvas.drawText(entry.key, x + barWidth / 2, canvasHeight - bottomPadding + 30f, paint)
//                }
//            }
//        }
//    }
//}

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp

@Composable
fun BarChart(
    data: Map<String, Int>,
    modifier: Modifier = Modifier,
    barColor: Color = MaterialTheme.colorScheme.primary,
    axisColor: Color = Color.Gray,
    labelColor: Color = Color.Black
) {
    if (data.isEmpty()) {
        Text("No data", modifier = modifier)
        return
    }

    val maxValue = (data.values.maxOrNull() ?: 1).coerceAtLeast(1)

    Box(modifier = modifier.padding(16.dp)) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasWidth = size.width
            val canvasHeight = size.height

            val leftPadding = 60f
            val bottomPadding = 80f
            val topPadding = 40f
            val rightPadding = 40f

            val availableWidth = canvasWidth - leftPadding - rightPadding
            val availableHeight = canvasHeight - topPadding - bottomPadding

            val barCount = data.size
            val barWidth = availableWidth / (barCount * 2)
            val spacing = barWidth

            // Y-axis
            drawLine(
                color = axisColor,
                start = Offset(leftPadding, topPadding),
                end = Offset(leftPadding, canvasHeight - bottomPadding),
                strokeWidth = 2f
            )

            // X-axis
            drawLine(
                color = axisColor,
                start = Offset(leftPadding, canvasHeight - bottomPadding),
                end = Offset(canvasWidth - rightPadding, canvasHeight - bottomPadding),
                strokeWidth = 2f
            )

            // Gridlines + Y-axis labels (upravené na celé čísla)
            val steps = maxValue.coerceAtLeast(1)
            val stepHeight = availableHeight / steps

            for (i in 0..steps) {
                val y = topPadding + i * stepHeight
                val value = steps - i  // 0 dole, max hore

                // horizontal gridline
                drawLine(
                    color = axisColor.copy(alpha = 0.3f),
                    start = Offset(leftPadding, y),
                    end = Offset(canvasWidth - rightPadding, y),
                    strokeWidth = 1f
                )

                // Y-axis label
                drawIntoCanvas { canvas ->
                    val paint = android.graphics.Paint().apply {
                        textSize = 24f
                        color = android.graphics.Color.DKGRAY
                        textAlign = android.graphics.Paint.Align.RIGHT
                        isAntiAlias = true
                    }
                    canvas.nativeCanvas.drawText("$value", leftPadding - 10f, y + 8f, paint)
                }
            }

            // Bars + Labels
            data.entries.forEachIndexed { index, entry ->
                val valueRatio = entry.value.toFloat() / maxValue
                val barHeight = valueRatio * availableHeight

                val x = leftPadding + spacing + index * (barWidth + spacing)
                val y = (canvasHeight - bottomPadding) - barHeight

                // bar
                drawRect(
                    color = barColor,
                    topLeft = Offset(x, y),
                    size = androidx.compose.ui.geometry.Size(barWidth, barHeight)
                )

                // value above bar
                drawIntoCanvas { canvas ->
                    val paint = android.graphics.Paint().apply {
                        textSize = 26f
                        color = android.graphics.Color.BLACK
                        textAlign = android.graphics.Paint.Align.CENTER
                        isAntiAlias = true
                    }
                    canvas.nativeCanvas.drawText("${entry.value}", x + barWidth / 2, y - 10f, paint)
                }

                // category label under bar
                drawIntoCanvas { canvas ->
                    val paint = android.graphics.Paint().apply {
                        textSize = 24f
                        color = android.graphics.Color.DKGRAY
                        textAlign = android.graphics.Paint.Align.CENTER
                        isAntiAlias = true
                    }
                    canvas.nativeCanvas.drawText(entry.key, x + barWidth / 2, canvasHeight - bottomPadding + 30f, paint)
                }
            }
        }
    }
}

