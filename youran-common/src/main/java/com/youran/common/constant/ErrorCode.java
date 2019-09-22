package com.youran.common.constant;


import java.util.HashMap;
import java.util.Map;

/**
 * <p>Title:错误代码枚举</p>
 * <p>Description: 标准http错误编码，4xx客户端错误，5xx服务端错误</p>
 * @author: cbb
 * @date: 2017/10/13
 */
public enum ErrorCode {

    /**
     * {@code 400 Bad Request}.
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.5.1">HTTP/1.1: Semantics and Content, section 6.5.1</a>
     */
    BAD_REQUEST(400, "Bad Request"),
    /**
     * {@code 401 Unauthorized}.
     * @see <a href="http://tools.ietf.org/html/rfc7235#section-3.1">HTTP/1.1: Authentication, section 3.1</a>
     */
    UNAUTHORIZED(401, "Unauthorized"),
    /**
     * {@code 402 Payment Required}.
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.5.2">HTTP/1.1: Semantics and Content, section 6.5.2</a>
     */
    PAYMENT_REQUIRED(402, "Payment Required"),
    /**
     * {@code 403 Forbidden}.
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.5.3">HTTP/1.1: Semantics and Content, section 6.5.3</a>
     */
    FORBIDDEN(403, "Forbidden"),
    /**
     * {@code 404 Not Found}.
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.5.4">HTTP/1.1: Semantics and Content, section 6.5.4</a>
     */
    NOT_FOUND(404, "Not Found"),
    /**
     * {@code 405 Method Not Allowed}.
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.5.5">HTTP/1.1: Semantics and Content, section 6.5.5</a>
     */
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
    /**
     * {@code 406 Not Acceptable}.
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.5.6">HTTP/1.1: Semantics and Content, section 6.5.6</a>
     */
    NOT_ACCEPTABLE(406, "Not Acceptable"),
    /**
     * {@code 407 Proxy Authentication Required}.
     * @see <a href="http://tools.ietf.org/html/rfc7235#section-3.2">HTTP/1.1: Authentication, section 3.2</a>
     */
    PROXY_AUTHENTICATION_REQUIRED(407, "Proxy Authentication Required"),
    /**
     * {@code 408 Request Timeout}.
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.5.7">HTTP/1.1: Semantics and Content, section 6.5.7</a>
     */
    REQUEST_TIMEOUT(408, "Request Timeout"),
    /**
     * {@code 409 Conflict}.
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.5.8">HTTP/1.1: Semantics and Content, section 6.5.8</a>
     */
    CONFLICT(409, "Conflict"),
    /**
     * {@code 410 Gone}.
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.5.9">
     *     HTTP/1.1: Semantics and Content, section 6.5.9</a>
     */
    GONE(410, "Gone"),
    /**
     * {@code 411 Length Required}.
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.5.10">
     *     HTTP/1.1: Semantics and Content, section 6.5.10</a>
     */
    LENGTH_REQUIRED(411, "Length Required"),
    /**
     * {@code 412 Precondition failed}.
     * @see <a href="http://tools.ietf.org/html/rfc7232#section-4.2">
     *     HTTP/1.1: Conditional Requests, section 4.2</a>
     */
    PRECONDITION_FAILED(412, "Precondition Failed"),
    /**
     * {@code 413 Payload Too Large}.
     * @since 4.1
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.5.11">
     *     HTTP/1.1: Semantics and Content, section 6.5.11</a>
     */
    PAYLOAD_TOO_LARGE(413, "Payload Too Large"),
    /**
     * {@code 413 Request Entity Too Large}.
     * @see <a href="http://tools.ietf.org/html/rfc2616#section-10.4.14">HTTP/1.1, section 10.4.14</a>
     * @deprecated in favor of {@link #PAYLOAD_TOO_LARGE} which will be
     * returned from {@code HttpStatus.valueOf(413)}
     */
    @Deprecated
    REQUEST_ENTITY_TOO_LARGE(413, "Request Entity Too Large"),
    /**
     * {@code 414 URI Too Long}.
     * @since 4.1
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.5.12">
     *     HTTP/1.1: Semantics and Content, section 6.5.12</a>
     */
    URI_TOO_LONG(414, "URI Too Long"),
    /**
     * {@code 414 Request-URI Too Long}.
     * @see <a href="http://tools.ietf.org/html/rfc2616#section-10.4.15">HTTP/1.1, section 10.4.15</a>
     * @deprecated in favor of {@link #URI_TOO_LONG} which will be returned from {@code HttpStatus.valueOf(414)}
     */
    @Deprecated
    REQUEST_URI_TOO_LONG(414, "Request-URI Too Long"),
    /**
     * {@code 415 Unsupported Media Type}.
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.5.13">
     *     HTTP/1.1: Semantics and Content, section 6.5.13</a>
     */
    UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type"),
    /**
     * {@code 416 Requested Range Not Satisfiable}.
     * @see <a href="http://tools.ietf.org/html/rfc7233#section-4.4">HTTP/1.1: Range Requests, section 4.4</a>
     */
    REQUESTED_RANGE_NOT_SATISFIABLE(416, "Requested range not satisfiable"),
    /**
     * {@code 417 Expectation Failed}.
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.5.14">
     *     HTTP/1.1: Semantics and Content, section 6.5.14</a>
     */
    EXPECTATION_FAILED(417, "Expectation Failed"),
    /**
     * {@code 418 I'm a teapot}.
     * @see <a href="http://tools.ietf.org/html/rfc2324#section-2.3.2">HTCPCP/1.0</a>
     */
    I_AM_A_TEAPOT(418, "I'm a teapot"),
    /**
     * @deprecated See
     * <a href="http://tools.ietf.org/rfcdiff?difftype=--hwdiff&url2=draft-ietf-webdav-protocol-06.txt">
     *     WebDAV Draft Changes</a>
     */
    @Deprecated
    INSUFFICIENT_SPACE_ON_RESOURCE(419, "Insufficient Space On Resource"),
    /**
     * @deprecated See
     * <a href="http://tools.ietf.org/rfcdiff?difftype=--hwdiff&url2=draft-ietf-webdav-protocol-06.txt">
     *     WebDAV Draft Changes</a>
     */
    @Deprecated
    METHOD_FAILURE(420, "Method Failure"),
    /**
     * @deprecated
     * See <a href="http://tools.ietf.org/rfcdiff?difftype=--hwdiff&url2=draft-ietf-webdav-protocol-06.txt">
     *     WebDAV Draft Changes</a>
     */
    @Deprecated
    DESTINATION_LOCKED(421, "Destination Locked"),
    /**
     * {@code 422 Unprocessable Entity}.
     * @see <a href="http://tools.ietf.org/html/rfc4918#section-11.2">WebDAV</a>
     */
    UNPROCESSABLE_ENTITY(422, "Unprocessable Entity"),
    /**
     * {@code 423 Locked}.
     * @see <a href="http://tools.ietf.org/html/rfc4918#section-11.3">WebDAV</a>
     */
    LOCKED(423, "Locked"),
    /**
     * {@code 424 Failed Dependency}.
     * @see <a href="http://tools.ietf.org/html/rfc4918#section-11.4">WebDAV</a>
     */
    FAILED_DEPENDENCY(424, "Failed Dependency"),
    /**
     * {@code 426 Upgrade Required}.
     * @see <a href="http://tools.ietf.org/html/rfc2817#section-6">Upgrading to TLS Within HTTP/1.1</a>
     */
    UPGRADE_REQUIRED(426, "Upgrade Required"),
    /**
     * {@code 428 Precondition Required}.
     * @see <a href="http://tools.ietf.org/html/rfc6585#section-3">Additional HTTP Status Codes</a>
     */
    PRECONDITION_REQUIRED(428, "Precondition Required"),
    /**
     * {@code 429 Too Many Requests}.
     * @see <a href="http://tools.ietf.org/html/rfc6585#section-4">Additional HTTP Status Codes</a>
     */
    TOO_MANY_REQUESTS(429, "Too Many Requests"),
    /**
     * {@code 431 Request Header Fields Too Large}.
     * @see <a href="http://tools.ietf.org/html/rfc6585#section-5">Additional HTTP Status Codes</a>
     */
    REQUEST_HEADER_FIELDS_TOO_LARGE(431, "Request Header Fields Too Large"),
    /**
     * {@code 451 Unavailable For Legal Reasons}.
     * @see <a href="https://tools.ietf.org/html/draft-ietf-httpbis-legally-restricted-status-04">
     * An HTTP Status Code to Report Legal Obstacles</a>
     * @since 4.3
     */
    UNAVAILABLE_FOR_LEGAL_REASONS(451, "Unavailable For Legal Reasons"),

    /**
     * 参数错误
     */
    BAD_PARAMETER(460,"参数错误"),

    /**
     * {@code 500 Internal Server Error}.
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.6.1">HTTP/1.1: Semantics and Content, section 6.6.1</a>
     */
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    /**
     * {@code 501 Not Implemented}.
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.6.2">HTTP/1.1: Semantics and Content, section 6.6.2</a>
     */
    NOT_IMPLEMENTED(501, "Not Implemented"),
    /**
     * {@code 502 Bad Gateway}.
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.6.3">HTTP/1.1: Semantics and Content, section 6.6.3</a>
     */
    BAD_GATEWAY(502, "Bad Gateway"),
    /**
     * {@code 503 Service Unavailable}.
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.6.4">HTTP/1.1: Semantics and Content, section 6.6.4</a>
     */
    SERVICE_UNAVAILABLE(503, "Service Unavailable"),
    /**
     * {@code 504 Gateway Timeout}.
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.6.5">HTTP/1.1: Semantics and Content, section 6.6.5</a>
     */
    GATEWAY_TIMEOUT(504, "Gateway Timeout"),
    /**
     * {@code 505 HTTP Version Not Supported}.
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.6.6">HTTP/1.1: Semantics and Content, section 6.6.6</a>
     */
    HTTP_VERSION_NOT_SUPPORTED(505, "HTTP Version not supported"),
    /**
     * {@code 506 Variant Also Negotiates}
     * @see <a href="http://tools.ietf.org/html/rfc2295#section-8.1">Transparent Content Negotiation</a>
     */
    VARIANT_ALSO_NEGOTIATES(506, "Variant Also Negotiates"),
    /**
     * {@code 507 Insufficient Storage}
     * @see <a href="http://tools.ietf.org/html/rfc4918#section-11.5">WebDAV</a>
     */
    INSUFFICIENT_STORAGE(507, "Insufficient Storage"),
    /**
     * {@code 508 Loop Detected}
     * @see <a href="http://tools.ietf.org/html/rfc5842#section-7.2">WebDAV Binding Extensions</a>
     */
    LOOP_DETECTED(508, "Loop Detected"),
    /**
     * {@code 509 Bandwidth Limit Exceeded}
     */
    BANDWIDTH_LIMIT_EXCEEDED(509, "Bandwidth Limit Exceeded"),
    /**
     * {@code 510 Not Extended}
     * @see <a href="http://tools.ietf.org/html/rfc2774#section-7">HTTP Extension Framework</a>
     */
    NOT_EXTENDED(510, "Not Extended"),
    /**
     * {@code 511 Network Authentication Required}.
     * @see <a href="http://tools.ietf.org/html/rfc6585#section-6">Additional HTTP Status Codes</a>
     */
    NETWORK_AUTHENTICATION_REQUIRED(511, "Network Authentication Required"),
    /**
     * 唯一键重复
     */
    DUPLICATE_KEY(520,"唯一键重复"),
    /**
     * 记录未找到
     */
    RECORD_NOT_FIND(522, "记录未找到"),
    /**
     * 参数为空
     */
    PARAM_IS_NULL(523, "参数为空"),
    /**
     * 内部数据校验失败
     */
    INNER_DATA_ERROR(560,"内部数据校验失败"),
    ;


    private final Integer value;
    private final String desc;

    private static final Map<Integer, ErrorCode> LOOKUP = new HashMap<>();

    static {
        for (ErrorCode e : ErrorCode.values()) {
            LOOKUP.put(e.value, e);
        }
    }


    ErrorCode(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static ErrorCode find(Integer value) {
        return LOOKUP.get(value);
    }

    public Integer getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }


}
