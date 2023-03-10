// Copyright (C) 2022 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.test.types

import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonPrimitive
import xqt.kotlinx.lsp.types.Position
import xqt.kotlinx.lsp.types.Range
import xqt.kotlinx.lsp.types.TextEdit
import xqt.kotlinx.rpc.json.serialization.UnsupportedKindTypeException
import xqt.kotlinx.rpc.json.serialization.jsonArrayOf
import xqt.kotlinx.rpc.json.serialization.jsonObjectOf
import xqt.kotlinx.test.DisplayName
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

@DisplayName("The TextEdit type")
class TheTextEditType {
    @Test
    @DisplayName("supports the non-optional properties")
    fun supports_the_non_optional_properties() {
        val json = jsonObjectOf(
            "range" to jsonObjectOf(
                "start" to jsonObjectOf(
                    "line" to JsonPrimitive(5),
                    "character" to JsonPrimitive(23)
                ),
                "end" to jsonObjectOf(
                    "line" to JsonPrimitive(5),
                    "character" to JsonPrimitive(23)
                )
            ),
            "newText" to JsonPrimitive("lorem")
        )

        val te = TextEdit.deserialize(json)
        assertEquals(Position(5u, 23u), te.range.start)
        assertEquals(Position(5u, 23u), te.range.end)
        assertEquals("lorem", te.newText)

        assertEquals(json, TextEdit.serializeToJson(te))
    }

    @Test
    @DisplayName("supports insert operations")
    fun supports_insert_operations() {
        val expected = TextEdit(
            Range(Position(5u, 23u), Position(5u, 23u)),
            "lorem"
        )

        val insert = TextEdit.insert(Position(5u, 23u), "lorem")
        assertEquals(expected, insert)
    }

    @Test
    @DisplayName("supports edit operations")
    fun supports_edit_operations() {
        val expected = TextEdit(
            Range(Position(5u, 23u), Position(5u, 34u)),
            "lorem"
        )

        val edit = TextEdit.edit(
            Range(Position(5u, 23u), Position(5u, 34u)),
            "lorem"
        )
        assertEquals(expected, edit)
    }

    @Test
    @DisplayName("supports delete operations")
    fun supports_delete_operations() {
        val expected = TextEdit(
            Range(Position(5u, 23u), Position(5u, 34u)),
            ""
        )

        val delete = TextEdit.delete(Range(Position(5u, 23u), Position(5u, 34u)))
        assertEquals(expected, delete)
    }

    @Test
    @DisplayName("throws an error if the kind type is not supported")
    fun throws_an_error_if_the_kind_type_is_not_supported() {
        val e1 = assertFails { TextEdit.deserialize(jsonArrayOf()) }
        assertEquals(UnsupportedKindTypeException::class, e1::class)
        assertEquals("Unsupported kind type 'array'", e1.message)

        val e2 = assertFails { TextEdit.deserialize(JsonNull) }
        assertEquals(UnsupportedKindTypeException::class, e2::class)
        assertEquals("Unsupported kind type 'null'", e2.message)

        val e3 = assertFails { TextEdit.deserialize(JsonPrimitive("test")) }
        assertEquals(UnsupportedKindTypeException::class, e3::class)
        assertEquals("Unsupported kind type 'string'", e3.message)

        val e4 = assertFails { TextEdit.deserialize(JsonPrimitive(true)) }
        assertEquals(UnsupportedKindTypeException::class, e4::class)
        assertEquals("Unsupported kind type 'boolean'", e4.message)

        val e5 = assertFails { TextEdit.deserialize(JsonPrimitive(1)) }
        assertEquals(UnsupportedKindTypeException::class, e5::class)
        assertEquals("Unsupported kind type 'integer'", e5.message)

        val e6 = assertFails { TextEdit.deserialize(JsonPrimitive(1.2)) }
        assertEquals(UnsupportedKindTypeException::class, e6::class)
        assertEquals("Unsupported kind type 'decimal'", e6.message)
    }
}
