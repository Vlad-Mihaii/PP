import org.jsoup.Jsoup
import org.jsoup.parser.Parser

data class RSSItem(
    val title: String,
    val link: String,
    val description: String,
    val pubDate: String?
)

data class RSSFeed(
    val title: String,
    val link: String,
    val description: String,
    val items: List<RSSItem>
)

fun main() {
    val url = "http://rss.cnn.com/rss/edition.rss"

    try {
        // 1. Conectare și parsare XML
        val doc = Jsoup.connect(url)
            .parser(Parser.xmlParser()) // Important pentru XML, creare obiect document
            .get()

        // 2. Extragere atribute generale ale canalului
        val channel = doc.selectFirst("channel")

        if (channel != null) {
            val feedTitle = channel.selectFirst("title")?.text() ?: "N/A"
            val feedLink = channel.selectFirst("link")?.text() ?: "N/A"
            val feedDesc = channel.selectFirst("description")?.text() ?: "N/A"

            // 3. Extragere item-uri și mapare către lista de ADT-uri(Abstract type data)
            val items = channel.select("item").map { element ->
                RSSItem(
                    title = element.selectFirst("title")?.text() ?: "Fără titlu",
                    link = element.selectFirst("link")?.text() ?: "Fără link",
                    description = element.selectFirst("description")?.text() ?: "",
                    pubDate = element.selectFirst("pubDate")?.text()
                )
            }

            // 4. ADT principal
            val rssFeed = RSSFeed(feedTitle, feedLink, feedDesc, items)

            // 5. Afișarea rezultatelor
            println("=== Feed: ${rssFeed.title} ===")
            println("Descriere: ${rssFeed.description}\n")

            rssFeed.items.forEachIndexed { index, item ->
                println("${index + 1}. Titlu: ${item.title}")
                println("   Link:  ${item.link}")
                println("-".repeat(50))
            }
        }
    } catch (e: Exception) {
        println("Eroare la procesarea feed-ului RSS: ${e.message}")
    }
}