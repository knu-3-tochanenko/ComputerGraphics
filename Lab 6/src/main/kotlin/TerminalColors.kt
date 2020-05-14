fun String.white(): String {
    return this.color(30)
}

fun String.grey(): String {
    return this.color(37)
}

fun String.red(): String {
    return this.color(91)
}

fun String.green(): String {
    return this.color(92)
}

fun String.yellow(): String {
    return this.color(93)
}

fun String.blue(): String {
    return this.color(94)
}

fun String.purple(): String {
    return this.color(95)
}

fun String.cyan(): String {
    return this.color(96)
}

fun String.black(): String {
    return this.color(97)
}

fun String.color(code: Int): String {
    return "\u001B[${code}m$this\u001B[0m"
}