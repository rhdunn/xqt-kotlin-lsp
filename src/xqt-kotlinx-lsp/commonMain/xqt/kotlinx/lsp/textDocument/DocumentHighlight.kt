// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.textDocument

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import xqt.kotlinx.lsp.types.Range
import xqt.kotlinx.rpc.json.serialization.*
import xqt.kotlinx.rpc.json.serialization.types.JsonInt
import kotlin.jvm.JvmInline

/**
 * A document highlight is a range inside a text document which deserves
 * special attention.
 *
 * Usually a document highlight is visualized by changing the background
 * color of its range.
 *
 * @since 1.0.0
 */
data class DocumentHighlight(
    /**
     * The range this highlight applies to.
     */
    val range: Range,

    /**
     * The highlight kind.
     *
     * The default is `DocumentHighlightKind.Text`.
     */
    val kind: DocumentHighlightKind? = null
) {
    companion object : JsonSerialization<DocumentHighlight> {
        override fun serializeToJson(value: DocumentHighlight): JsonObject = buildJsonObject {
            put("range", value.range, Range)
            putOptional("kind", value.kind, DocumentHighlightKind)
        }

        override fun deserialize(json: JsonElement): DocumentHighlight = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> DocumentHighlight(
                range = json.get("range", Range),
                kind = json.getOptional("kind", DocumentHighlightKind)
            )
        }
    }
}

/**
 * A document highlight kind.
 *
 * @param kind the document highlight kind.
 *
 * @since 1.0.0
 */
@JvmInline
value class DocumentHighlightKind(val kind: Int) {
    companion object : JsonSerialization<DocumentHighlightKind> {
        override fun serializeToJson(value: DocumentHighlightKind): JsonPrimitive = JsonPrimitive(value.kind)

        override fun deserialize(json: JsonElement): DocumentHighlightKind {
            return DocumentHighlightKind(JsonInt.deserialize(json))
        }

        /**
         * A textual occurrence.
         */
        val Text: DocumentHighlightKind = DocumentHighlightKind(1)

        /**
         * Read-access of a symbol, like reading a variable.
         */
        val Read: DocumentHighlightKind = DocumentHighlightKind(2)

        /**
         * Write-access of a symbol, like writing to a variable.
         */
        val Write: DocumentHighlightKind = DocumentHighlightKind(3)
    }
}
