class CircularList<T> {
    private var root: Node<T>? = null
    private var tail: Node<T>? = null
    private var size = 0

    fun size(): Int {
        return size
    }

    fun insertToEnd(data: T): Node<T> {
        size++
        val node: Node<T> = Node(data)
        if (root == null) {
            root = node
            root?.next = root
            root?.prev = root
            tail = root
            return node
        }
        tail?.next = node
        node.prev = tail
        node.next = root
        tail = node
        root?.prev = tail
        return node
    }

    fun getRoot(): Node<T>? {
        return root
    }

    fun insertAfter(node: Node<T>, data: T): Node<T> {
        val newNode: Node<T> = Node(data)
        newNode.next = node.next
        newNode.prev = node
        node.next = newNode
        newNode.next?.prev = newNode
        if (node === tail) {
            tail = newNode
            root?.prev = tail
        }
        return newNode
    }

    fun splitNext(nodeA: Node<T>, nodeB: Node<T>) {
        nodeA.next = nodeB
        nodeB.prev = nodeA
        root = nodeA
        tail = nodeA.prev
    }
}