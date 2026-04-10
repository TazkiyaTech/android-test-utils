package com.tazkiyatech.utils.compose

import androidx.annotation.StringRes
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.SemanticsProperties.StateDescription
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.test.*
import androidx.test.platform.app.InstrumentationRegistry

/**
 * Asserts that the node has a `"CollectionInfo"` semantic property with the expected `rowCount` value.
 *
 * Example usage:
 *
 * ```
 * composeTestRule.onNodeWithTag("SomeCollectionView").assertRowCount(100)
 * ```
 *
 * @param expected The expected row count.
 * @return The [SemanticsNodeInteraction] that is the receiver of this function call.
 * @throws AssertionError If the node does not have a `"CollectionInfo"` semantic property with the expected `rowCount` value.
 */
fun SemanticsNodeInteraction.assertRowCount(expected: Int): SemanticsNodeInteraction {
    return assert(SemanticsMatcher("CollectionInfo.rowCount == $expected") { node ->
        node.config.getOrNull(SemanticsProperties.CollectionInfo)?.rowCount == expected
    })
}

/**
 * Asserts that the node's state description matches the expected value.
 *
 * Example usage:
 *
 * ```
 * composeTestRule.onNodeWithTag("SomeView").assertStateDescription("Some state description")
 * ```
 *
 * @param expectedValue The expected state description.
 * @return The [SemanticsNodeInteraction] that is the receiver of this function call.
 * @throws AssertionError If the node does not have the expected state description.
 */
fun SemanticsNodeInteraction.assertStateDescription(expectedValue: String): SemanticsNodeInteraction {
    return assert(SemanticsMatcher.expectValue(StateDescription, expectedValue))
}

/**
 * Asserts that the node's text matches the string associated with the given resource id.
 *
 * Example usage:
 *
 * ```
 * composeTestRule.onNodeWithTag("SomeView").assertTextEquals(R.string.some_string)
 * ```
 *
 * @param resourceId The resource id of the expected String.
 * @return The [SemanticsNodeInteraction] that is the receiver of this function call.
 * @throws AssertionError If the node does not have the expected text.
 */
fun SemanticsNodeInteraction.assertTextEquals(@StringRes resourceId: Int): SemanticsNodeInteraction {
    val context = InstrumentationRegistry.getInstrumentation().targetContext
    return assertTextEquals(context.getString(resourceId))
}

/**
 * Performs the [SemanticsActions.SetProgress] action with the given [value] on this node.
 *
 * Example usage:
 *
 * ```
 * composeTestRule.onNodeWithTag("SomeView").setProgress(1f)
 * ```
 *
 * @param value The progress value to pass into the [SemanticsActions.SetProgress] action.
 * @return The [SemanticsNodeInteraction] that is the receiver of this function call.
 * @throws AssertionError If the [SemanticsActions.SetProgress] action is not defined on this node.
 */
fun SemanticsNodeInteraction.setProgress(value: Float): SemanticsNodeInteraction {
    return performSemanticsAction(SemanticsActions.SetProgress) { action -> action(value) }
}
