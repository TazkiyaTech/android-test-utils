package com.tazkiyatech.utils.text

import com.tazkiyatech.utils.text.IsEqualCompressingDirectionalFormattingCharactersMatcher.Companion.equalToCompressingDirectionalFormattingCharacters
import org.junit.Assert.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class IsEqualCompressingDirectionalFormattingCharactersMatcherTest {

    @ParameterizedTest(name = "expected={0}, actual={1}")
    @MethodSource("provideArguments")
    fun equalToCompressingDirectionalFormattingCharacters(expected: String, actual: String) {
        assertThat(actual, equalToCompressingDirectionalFormattingCharacters(expected))
    }

    companion object {

        @JvmStatic
        @Suppress("unused")
        fun provideArguments(): Stream<Arguments> {
            return Stream.of(
                arguments("\u202DFoo bar baz\u202C", "Foo bar baz"),
                arguments("Foo bar baz", "\u202DFoo bar baz\u202C")
            )
        }
    }
}
