package com.example.sudoku.data.blog.repository

import com.example.sudoku.data.blog.local.BlogPost

object BlogRepository {

    val blogPosts = listOf(
        BlogPost(
            id = 1,
            title = "The surprising benefits of Sudoku for your health",
            fileName = "blog-posts/blog1.html"
        ),
        BlogPost(
            id = 2,
            title = "Sudoku history",
            fileName = "blog-posts/blog2.html"
        ),
        BlogPost(
            id = 3,
            title = "Sudoku as a prevention against dementia",
            fileName = "blog-posts/blog3.html"
        ),
        BlogPost(
            id = 4,
            title = "How to solve Sudoku effectively",
            fileName = "blog-posts/blog4.html"
        ),
        BlogPost(
            id = 5,
            title = "Why did I create my sudoku project?",
            fileName = "blog-posts/blog5.html"
        ),
    )

    fun getPostById(id: Int): BlogPost? {
        return blogPosts.find { it.id == id }
    }
}
