package org.wso2.extension.siddhi.execution.esbanalytics.util;

public class ESBAnalyticsDecompressConstants {

    public class TestData {

        // Sample input analytics message from the EI
        public static final boolean META_COMPRESSED = true;
        public static final int META_TENANT_ID = -1234;
        public static final String MESSAGE_ID = "urn_uuid_0542abda-fa1e-4e95-9543-b63c690f465f199208030396291";
//        public static final String FLOW_DATA =
//                "H4sIAAAAAAAAAI2WT4jcVBzHN8lSmlJFRYsUkSoiHszs+5vk5aTorl1odXEvgofh7eTtTjCbjJlkyyKCiGfx" +
//                        "IJ48FE9FRLa1tHry2vaiXjxVqLB762nbQy8WXzZ5M5PkzerA8Jskv/e+n9+f95sYi5axLcZjviVW4v" +
//                        "TS6r5lFFnSL4oo7ANKEN8IubPJoXCIYNRhlGBnw8UDl4FN4tJNyBgCPsAAMxexq5Yx4rtxysMH9wxj" +
//                        "wTKG6fihZUDQgwD0vB6E0kPsiCQvnz8nv09bEuDNtduWcV7wOB8OeCbkpbnQvLH6BggaDvaVX3/7+5" +
//                        "8P7Mt3jsyjUwvyYxkfwr+sJ0pBFzLku+CXcqPp1VRw9dz7YpwW2eDAMpaGk52XBjwXW2kWifHSJ/Xv" +
//                        "3U+XMjEW2c6BabapWKC2sX9oAD1UQEgD5EAKPR/7gNxQROvi40IkJY3ctr/67vryH+ZiWw2CYPJ0nh7" +
//                        "WJYC6LiTQ9W4quYsijHieHlrGWpaORJbv1ney4Hw6HkU5v2+e6MjDYK63fbfCOazMmZqG6KKnBBDq+/" +
//                        "BqTfOShkJqhv2k2N4Qh+bJDgjqgswsUBC1WahZqC4zxPcwhmBvivI2z/lFPpK7qySZpzoAOOi6Kb0/b" +
//                        "x2Z/ZO1rqvtACjPi0t8dyYJ65eifDCcqJ7uqJKg6WJ/d2u2Bx4btaKnUUSUyDPL0E9TvQvp1kTsyY4Y" +
//                        "DWae21cqpe8rY9ZCvkYIAyKrS8nPx1S3yKLeDs96Q9VrT3Xk3W6J26sUTZ3w12sopoFiEFMXed5Mtt/" +
//                        "icTwJ/5mOvhfMOiipu7ePzDWVaQi0xUUEedhD7Lo6b8tJOEqjRM7CdzKehO/xj5Z/N5+duVqTA276yP" +
//                        "6xkrvXltONN4dBz5PV9f5PxmXHSo58W47gfrRvnmnHjbz5eW+utfcruMeVOasQdQMPUgp89h9NEaYDa" +
//                        "fqhyHkUPzCf76D5XbTmGsXy2Z1Gl0LtTPQZIYgy77gpNOJ5VEarmM52mFiXqbVI0XxRmUUFpRuNSP7l" +
//                        "Ao9CdGNOn77QBsCg2adzxLSzD0AXA0aAGgkXpNiQJ4mIo2RrRQjZoi+2b5V92nKyP6/EvqzMaaWpn3u" +
//                        "YMQoxZHvHpJ0P8oLH/U1xYJ7rBKyZ/VN/BfFVqwN0E9FBAGHiYwRmW6B6fVnhZV/tTvL+cgcDB3pXJf" +
//                        "1NZU4oAt2odCBB0MOM4r059X6lo0ua9f66oTaJVzcDHfkORhmigN2cFnxd5HksZCjlqZYFf7V9qyx4y" +
//                        "0mJflsZ9T+HdLMQMgiI62N6fRqhfGUapUk4CfK1TpBu0PJRWpcbfZ0U8X3dySZSFEPv2r95HRx/3QoAAA==";
        public static final String FLOW_DATA = "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" +
        "6BiTCPe/3vVsiGdD5aaL+4B/czklVFSUAAA==";
    }
}
