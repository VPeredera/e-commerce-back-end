package ecommerce.reviews.domain;

import java.util.HashMap;
import java.util.Map;

public enum Rate {
    _1(1), _2(2), _3(3), _4(4), _5(5);

    private final int value;
    private static final Map<Integer, Rate> map = new HashMap<>();

    Rate(int value) {
        this.value = value;
    }

    static {
        for (Rate rate : Rate.values())
            map.put(rate.value, rate);
    }

    public static Rate valueOf(int rate) {
        return map.get(rate);
    }

    public int getRate() {
        return value;
    }
}
