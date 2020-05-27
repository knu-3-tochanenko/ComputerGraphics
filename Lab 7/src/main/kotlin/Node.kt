class Node<T> {
    var prev: Node<T>? = null
    var next: Node<T>? = null
    var data: T

    constructor(data: T) {
        this.data = data
    }

    constructor(data: T, prev: Node<T>?, next: Node<T>?) {
        this.prev = prev
        this.next = next
        this.data = data
    }
}