package com.example.sudoku.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sudoku.data.blog.local.BlogPost
import com.example.sudoku.data.blog.repository.BlogRepository

@Composable
fun BlogScreen(
    innerPadding: PaddingValues,
    onPostClick: (BlogPost) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Blog",
            fontSize = 30.sp,
            color = MaterialTheme.colorScheme.onBackground
        )

        BlogRepository.blogPosts.forEach { post ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onPostClick(post) }
            ) {
                Text(
                    text = post.title,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}
