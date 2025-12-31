package com.blogbank.blogbankback.domain.blogpost.business

import com.blogbank.blogbankback.util.Base64Utils
import org.springframework.stereotype.Component

@Component
class BlogPostExtractor {

    companion object {
        private val TABLE_HEADER_KEYWORDS = listOf("No.", "이름", "블로그")
        private val SEPARATOR_PATTERN = "^\\|[-\\s|]+\\|$".toRegex()
        private val LINK_PATTERN = "\\[(.+?)\\]\\(([^)]+)\\)".toRegex()
        private const val MIN_COLUMNS = 5
        private const val AUTHOR_COLUMN = 2
        private const val TITLE_COLUMN = 3
        private const val MEMO_COLUMN = 4
    }

    fun parseMarkdownTable(base64Content: String): List<ParsedBlogPostData> {

        // base64 디코딩
        val decodedContent = Base64Utils.decodeGitHubContent(base64Content)

        val lines = decodedContent.split("\n")
        val tableStartIndex = findTableStart(lines) ?: return emptyList()
        val tableLines = extractTableLines(lines, tableStartIndex)

        return parseTableData(tableLines)
    }

    private fun findTableStart(lines: List<String>): Int? =
        lines.indexOfFirst { line ->
            TABLE_HEADER_KEYWORDS.all { keyword -> line.contains(keyword) }
        }.takeIf { it != -1 }

    private fun extractTableLines(lines: List<String>, startIndex: Int): List<String> =
        lines.drop(startIndex + 1)
            .takeWhile { line -> line.startsWith("|") || line.isEmpty() }
            .filter { line -> line.startsWith("|") && !isSeparatorLine(line) }

    private fun isSeparatorLine(line: String): Boolean =
        line.matches(SEPARATOR_PATTERN)

    private fun parseTableData(lines: List<String>): List<ParsedBlogPostData> =
        lines.mapIndexedNotNull { index, line ->
            parseTableRow(line, index + 1)
        }

    private fun parseTableRow(line: String, sequenceNumber: Int): ParsedBlogPostData? {
        val columns = line.split("|").map { it.trim() }

        if (columns.size < MIN_COLUMNS) return null

        val authorName = columns[AUTHOR_COLUMN].takeIf { it.isNotEmpty() } ?: return null
        val title = columns[TITLE_COLUMN].takeIf { it.isNotEmpty() }
        val memo = columns[MEMO_COLUMN].takeIf { it.isNotEmpty() }
        val (blogTitle, blogLink) = extractTitleAndLink(title)

        return ParsedBlogPostData(
            sequenceNumber = sequenceNumber,
            authorName = authorName,
            title = blogTitle,
            link = blogLink,
            memo = memo
        )
    }

    private fun extractTitleAndLink(titleColumn: String?): Pair<String?, String?> {
        if (titleColumn.isNullOrEmpty()) return null to null

        return LINK_PATTERN.find(titleColumn)?.let { match ->
            match.groupValues[1] to match.groupValues[2]
        } ?: (titleColumn to null)
    }
}