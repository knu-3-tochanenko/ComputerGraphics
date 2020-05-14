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
            findDotsInRectangle(node.left, rectangle)
            findDotsInRectangle(node.right, rectangle)
        }

        private fun between(what: Double, start: Double, end: Double): Boolean {
            return (what in start..end)
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