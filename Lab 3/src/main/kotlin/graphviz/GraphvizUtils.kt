package graphviz

import graph.Graph
import graph.Node
import guru.nidi.graphviz.engine.Format
import guru.nidi.graphviz.engine.Graphviz
import guru.nidi.graphviz.parse.Parser
import java.io.File
import java.io.FileWriter
import java.io.IOException

fun createPNG(fileName: String) {
    val file = File("src/main/resources/$fileName.dot")
    val mutableGraph = Parser().read(file)
    Graphviz.fromGraph(mutableGraph)
        .height(700)
        .render(Format.PNG)
        .toFile(File("src/main/resources/$fileName.png"))
}

fun writeFile(fileName: String, graph: Graph) {
    val file = File("src/main/resources/$fileName.dot")
    val newFile = file.createNewFile()
    try {
        FileWriter(file).use { writer ->
            writer.write("digraph G {\n")
            writer.flush()
        }
    } catch (ex: IOException) {
        println(ex.message)
    }
    printNodeShapeToFile(graph.root(), file)
    printTreeToFile(graph.root(), file)
    try {
        FileWriter(file, true).use { writer ->
            writer.write("}")
            writer.flush()
        }
    } catch (ex: IOException) {
        println(ex.message)
    }
}

private fun printNodeShapeToFile(root: Node?, file: File?) {
    if (root != null) {
        printNodeShapeToFile(root.left, file)
        try {
            FileWriter(file, true).use { writer ->
                writer.write(root.shape())
                writer.flush()
            }
        } catch (ex: IOException) {
            println(ex.message)
        }
        printNodeShapeToFile(root.right, file)
    }
}

private fun printTreeToFile(root: Node?, file: File?) {
    if (root != null) {
        printTreeToFile(root.left, file)
        if (root.left != null) {
            try {
                FileWriter(file, true).use { writer ->
                    writer.write(
                        """
                            "$root"->"${root.left}";
                            
                            """.trimIndent()
                    )
                    writer.flush()
                }
            } catch (ex: IOException) {
                println(ex.message)
            }
        }
        if (root.right != null) {
            try {
                FileWriter(file, true).use { writer ->
                    writer.write(
                        """
                            "$root"->"${root.right}";
                            
                            """.trimIndent()
                    )
                    writer.flush()
                }
            } catch (ex: IOException) {
                println(ex.message)
            }
        }
        printTreeToFile(root.right, file)
    }
}