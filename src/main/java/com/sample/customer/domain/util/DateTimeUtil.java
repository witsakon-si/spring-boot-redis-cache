package com.sample.customer.domain.util;

import java.time.Instant;
import java.util.Date;

public class DateTimeUtil {

    public static Date now() {
        return Date.from(Instant.now());
    }

}


