import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFalse


class SearchToolTest {

    @Test
    fun testIsValidDirectory() {
        val nonExistingDir = File("non_existing_dir")
        assertFalse(nonExistingDir.isValidDirectory())

        val fileInsteadOfDir = File("test_files")
        assertTrue(fileInsteadOfDir.isValidDirectory())
    }

    @Test
    fun testCollectFiles() {
        val validFolderPath = "test_files"
        val validFolder = File(validFolderPath)
        val validFiles = validFolder.collectFiles()
        assertEquals(validFiles.size, 4)

        val invalidFolderPath = "path/to/invalid/directory"
        val invalidFolder = File(invalidFolderPath)
        val invalidFiles = invalidFolder.collectFiles()
        assertEquals(invalidFiles.size, 0)
    }

    @Test
    fun testSearch() {
        val filePath = "test_files/c.txt"
        val file = File(filePath)

        val results1 = file.search("sdf")
        assertEquals(results1.size, 1)

        val results2 = file.search("Python")
        assertEquals(results2.size, 0)
    }
}
