package com.tazkiyatech.utils.compose

import androidx.annotation.StringRes
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.semantics.SemanticsProperties.StateDescription
import androidx.compose.ui.test.*
import androidx.test.platform.app.InstrumentationRegistry

/**
 * Asserts that the node's state description matches the expected value.
 *
 * Example usage:
 *
 * ```
 * composeTestRule.onNodeWithTag("SomeTag").assertStateDescription("Some state description")
 * ```
 */
fun SemanticsNodeInteraction.assertStateDescription(expectedValue: String) {
    assert(SemanticsMatcher.expectValue(StateDescription, expectedValue))
}

/**
 * Asserts that the node's text matches the string associated with the given resource id.
 *
 * Example usage:
 *
 * ```
 * composeTestRule.onNodeWithTag("SomeTag").assertTextEquals(R.string.some_string)
 * ```
 */
fun SemanticsNodeInteraction.assertTextEquals(@StringRes resourceId: Int) {
    val context = InstrumentationRegistry.getInstrumentation().targetContext
    assertTextEquals(context.getString(resourceId))
}

/**
 * Performs the [androidx.compose.ui.semantics.SemanticsActions.SetProgress] action with the given [value].
 *
 * Example usage:
 *
 * ```
 * composeTestRule.onNodeWithTag("SomeTag").setProgress(1f)
 * ```
 */
fun SemanticsNodeInteraction.setProgress(value: Float) {
    performSemanticsAction(SemanticsActions.SetProgress) { action -> action(value) }
}
