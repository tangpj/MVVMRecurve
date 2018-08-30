package tang.com.recurve.resource

interface NextPageStrategy{
    fun nextPageRule(links: Map<String, String>): Int
}