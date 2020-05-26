class ArcQuery(private val p: Point?) : ArcKey() {
    override val left: Point?
        get() = p

    override val right: Point?
        get() = p
}
