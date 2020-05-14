import kotlin.math.pow

class Tree2D {
    companion object {
        private var result = mutableListOf<Dot>()

        fun calculate(dots: List<Dot>, rectangle: Rectangle): List<Dot> {
            val root = buildTree(dots)
            findDotsInRectangle(root, rectangle)
            return result
        }

        private fun buildTree(dots: List<Dot>, split: Split = Split.VERTICAL): TreeNode? {
            if (dots.size == 1)
                return TreeNode(dots[0])
            else if (dots.isEmpty())
                return null

            val median = findMedian(dots, split)
            val dot = dots[median]
            val x: Double?
            val y: Double?
            val nextSplit: Split
            if (split == Split.VERTICAL) {
                nextSplit = Split.HORIZONTAL
                x = dot.x
                y = null
            } else {
                nextSplit = Split.VERTICAL
                x = null
                y = dot.y
            }

            val node = TreeNode(dot, x = x, y = y)
            node.left = buildTree(dots.subList(0, median), nextSplit)
            node.right = buildTree(dots.subList(median, dots.size), nextSplit)
            return node
        }

        private fun findDotsInRectangle(node: TreeNode?, rectangle: Rectangle) {
            if (node == null)
                return
            if (isInRectangle(node.dot, rectangle))
                result.add(node.dot)
            if (isOutside(node, rectangle)) {
                println("${node.dot.x} --- ${node.dot.y} for left")
                findDotsInRectangle(node.left, rectangle)
            }
            if (isOutside(node, rectangle)) {
                println("${node.dot.x} --- ${node.dot.y} for right")
                findDotsInRectangle(node.right, rectangle)
            }
            if (!isOutside(node, rectangle) && !isOutside(node, rectangle))
                println("${node.dot.x} --- ${node.dot.y} ${"SKIPPED".red()}")
        }

        private fun equalDelta(a: Double, b: Double, delta: Double = .1.pow(8)): Boolean = kotlin.math.abs(a - b) < delta

        private fun isOutside(node: TreeNode, rectangle: Rectangle): Boolean {
            if (node.x == null && node.y != null) {
                return if (rectangle.topRight.y < node.y!! || equalDelta(rectangle.topRight.y, node.y!!)) {
                    true
                } else if (rectangle.bottomLeft.y < node.y!! || equalDelta(rectangle.bottomLeft.y, node.y!!)) {
                    true
                } else {
                    true
                }
            } else if (node.x != null && node.y == null) {
                return if (rectangle.topRight.x <= node.x!! || equalDelta(rectangle.topRight.x, node.x!!)) {
                    true
                } else if (rectangle.bottomLeft.x < node.x!! || equalDelta(rectangle.bottomLeft.x, node.x!!)) {
                    true
                } else {
                    true
                }
            }

            return false
        }

        private fun findMedian(dots: List<Dot>, type: Split): Int {
            if (type == Split.VERTICAL)
                dots.sortedBy { dot -> dot.x }
            if (type == Split.HORIZONTAL)
                dots.sortedBy { dot -> dot.y }
            return dots.size / 2
        }

        private fun isInRectangle(dot: Dot, rectangle: Rectangle): Boolean {
            return (dot.x >= rectangle.bottomLeft.x && dot.x <= rectangle.topRight.x)
                    && (dot.y >= rectangle.bottomLeft.y && dot.y <= rectangle.topRight.y)
        }
    }
}