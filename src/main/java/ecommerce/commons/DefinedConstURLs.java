package ecommerce.commons;

public class DefinedConstURLs {
    private static final String BASE_URL = "/ecommerce";

    public static final String[] OPTIONS = {
            "/**"
    };

    public static final String[] ALL = {
            BASE_URL + "/auth/**",
            BASE_URL + "/auth",
    };

    public static final String[] GET = {
            BASE_URL + "/goods/**",
            BASE_URL + "/review/**",
    };

    public static final String[] POST = {
            BASE_URL + "/goods/**",
            BASE_URL + "/review/**",
    };

    public static final String[] PUT = {
            BASE_URL + "/goods/**",
            BASE_URL + "/review/**",
    };

    public static final String[] DELETE = {
            BASE_URL + "/goods/**",
            BASE_URL + "/review/**",
    };
}
