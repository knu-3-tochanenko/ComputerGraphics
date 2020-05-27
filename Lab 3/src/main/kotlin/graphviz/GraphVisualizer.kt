package graphviz

import guru.nidi.graphviz.engine.Format
import guru.nidi.graphviz.engine.Graphviz
import guru.nidi.graphviz.parse.Parser
import java.io.File

class GraphVisualizer {
    companion object {
        fun visualize(fileName: String) {
            val file = File("src/main/resources/$fileName.dot")
            val mutableGraph = Parser().read(file)
            Graphviz.fromGraph(mutableGraph)
                .height(700)
                .render(Format.PNG)
                .toFile(File("src/main/resources/$fileName.png"))
        }
    }
}