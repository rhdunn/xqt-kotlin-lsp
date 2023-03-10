// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.textDocument

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import xqt.kotlinx.lsp.types.Position
import xqt.kotlinx.lsp.types.TextDocumentIdentifier
import xqt.kotlinx.rpc.json.serialization.*
import xqt.kotlinx.rpc.json.serialization.types.JsonString

/**
 * Parameters for `textDocument/rename` request.
 *
 * @since 1.0.0
 */
data class RenameParams(
    /**
     * The document containing the symbol to rename.
     */
    val textDocument: TextDocumentIdentifier,

    /**
     * The position at which this request was send.
     */
    val position: Position,

    /**
     * The new name of the symbol.
     *
     * If the given name is not valid the request must return a `ResponseError`
     * with an appropriate message set.
     */
    val newName: String
) {
    companion object : JsonSerialization<RenameParams> {
        override fun serializeToJson(value: RenameParams): JsonObject = buildJsonObject {
            put("textDocument", value.textDocument, TextDocumentIdentifier)
            put("position", value.position, Position)
            put("newName", value.newName, JsonString)
        }

        override fun deserialize(json: JsonElement): RenameParams = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> RenameParams(
                textDocument = json.get("textDocument", TextDocumentIdentifier),
                position = json.get("position", Position),
                newName = json.get("newName", JsonString)
            )
        }
    }
}
