import android.webkit.WebView
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.example.sudoku.data.blog.repository.BlogRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlogDetailScreen(
    postId: Int,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val post = remember { BlogRepository.getPostById(postId) }

    var htmlContent by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(postId) {
        post?.let {
            try {
                val inputStream = context.assets.open(it.fileName)
                val content = inputStream.bufferedReader().use { reader -> reader.readText() }
                htmlContent = content
            } catch (e: Exception) {
                htmlContent = "<html><body><h2>Error loading article.</h2></body></html>"
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(post?.title ?: "Article") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            if (htmlContent == null) {
                CircularProgressIndicator()
            } else {
                AndroidView(
                    factory = { context ->
                        WebView(context).apply {
                            loadDataWithBaseURL(null, htmlContent!!, "text/html", "utf-8", null)
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
