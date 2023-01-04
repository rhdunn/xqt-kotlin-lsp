// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.base

import xqt.kotlinx.rpc.json.protocol.ErrorCode
import xqt.kotlinx.rpc.json.protocol.ErrorObject

/**
 * A number that indicates the error type that occurred.
 *
 * @since 1.0.0
 */
object ErrorCodes {
    /**
     * Invalid JSON was received by the server.
     *
     * An error occurred on the server while parsing the JSON text.
     */
    val ParseError: ErrorCode = ErrorCode(-32700)

    /**
     * Invalid method parameter(s).
     */
    val InternalError: ErrorCode = ErrorCode(-32603)

    /**
     * Invalid method parameter(s).
     */
    val InvalidParams: ErrorCode = ErrorCode(-32602)

    /**
     * The method does not exist / is not available.
     */
    val MethodNotFound: ErrorCode = ErrorCode(-32601)

    /**
     * The JSON sent is not a valid Request object.
     */
    val InvalidRequest: ErrorCode = ErrorCode(-32600)

    /**
     * Reserved for implementation-defined server-errors.
     */
    val ServerErrorStart: ErrorCode = ErrorCode(-32099)

    /**
     * Reserved for implementation-defined server-errors.
     */
    val ServerErrorEnd: ErrorCode = ErrorCode(-32000)
}

/**
 * An error processing an RPC call.
 *
 * @since 1.0.0
 */
typealias ResponseError = ErrorObject