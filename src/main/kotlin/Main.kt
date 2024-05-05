import java.io.File

fun main(args: Array<String>) {

    var folder = File(if (args.size == 1) args[0] else "")

    while (!folder.isValidDirectory()) {
        print("Current directory is incorrect. Please try again (q to quit): ")
        val folderPath = readLine()?.trim() ?: ""
        if (folderPath == "q") return
        folder = File(folderPath)
    }

    val files = folder.collectFiles()

    if (files.isEmpty()) {
        println("No text files found in the '${folder.absolutePath}' folder.")
        return
    }

    println("Indexing completed. Ready for search.")

    while (true) {
        print("Enter search query (q to quit): ")
        val query = readLine()?.trim() ?: ""
        if (query == "q") break

        val results = files
            .map { filePath -> File(filePath).search(query) }
            .flatten()
        if (results.isEmpty()) {
            println("No matches found for '$query'.")
        } else {
            println("Matches found for '$query':")
            results.forEach { println(it) }
        }
    }
}

fun File.isValidDirectory() = exists() && isDirectory

fun File.collectFiles(): List<String> = this
    .takeIf(File::isValidDirectory)
    ?.walkTopDown()
    ?.map { file ->
        when {
            file.isFile && file.extension == "txt" -> file.absolutePath
            else -> null
        }
    }
    ?.filterNotNull()
    ?.toList()
    ?: emptyList<String>()
        .also { println("Error: Folder does not exist or is not a directory.") }

fun File.search(query: String) = try {
    this
        .readLines()
        .mapIndexed { lineNumber, line ->
            when {
                line.contains(query, ignoreCase = true) -> "[${this.absolutePath}:${lineNumber + 1}] $line"
                else -> null
            }
        }
        .filterNotNull()
        .toList()
} catch (e: Exception) {
    println("Error while reading file '${this.absolutePath}': ${e.message}")
    emptyList()
}
