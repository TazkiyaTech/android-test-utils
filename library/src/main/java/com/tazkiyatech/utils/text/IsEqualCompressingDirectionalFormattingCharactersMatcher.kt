package com.tazkiyatech.utils.text

import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

/**
 * Tests if a string is equal to another string, compressing any changes in directional formatting characters.
 */
class IsEqualCompressingDirectionalFormattingCharactersMatcher
private constructor(private val string: String) : TypeSafeMatcher<String>() {

    override fun describeTo(description: Description) {
        description
            .appendText("a string equal to ")
            .appendText(string)
            .appendText(" compressing directional formatting characters")
    }

    override fun matchesSafely(item: String): Boolean {
        return stripDirectionalFormattingCharacters(string) == stripDirectionalFormattingCharacters(item)
    }

    private fun stripDirectionalFormattingCharacters(toBeStripped: String): String {
        // The directional formatting characters below are taken from: https://unicode.org/reports/tr9/#Directional_Formatting_Characters
        val directionalFormattingCharacters = setOf(
            '\u202A', '\u202B', // Explicit Directional Embeddings
            '\u202D', '\u202E', // Explicit Directional Overrides
            '\u202C', // Terminating Explicit Directional Embeddings and Overrides
            '\u2066', '\u2067', '\u2068', // Explicit Directional Isolates
            '\u2069', // Terminating Explicit Directional Isolates
            '\u200E', '\u200F', '\u061C' // Implicit Directional Marks
        )

        return toBeStripped.filterNot {
            directionalFormattingCharacters.contains(it)
        }
    }

    companion object {

        /**
         * Creates a matcher of `String` that matches when the examined string is equal to `expectedString`,
         * when directional formatting character differences are ignored.
         *
         * Usage example:
         * ```
         * assertThat("\u202DFoo bar baz\u202C", equalToCompressingDirectionalFormattingCharacters("Foo bar baz"))
         * ```
         *
         * @param expectedString The expected value of matched strings.
         */
        fun equalToCompressingDirectionalFormattingCharacters(expectedString: String): Matcher<String> {
            return IsEqualCompressingDirectionalFormattingCharactersMatcher(expectedString)
        }
    }
}
