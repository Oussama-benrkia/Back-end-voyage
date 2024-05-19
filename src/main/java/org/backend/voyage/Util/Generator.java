package org.backend.voyage.Util;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;
@Component
public class Generator {
    public String genrator(Long user,Long voye) {
        UUID uuid = UUID.randomUUID();
        LocalDateTime dateTime = LocalDateTime.now();
        long secondsSinceEpoch = dateTime.toEpochSecond(ZoneOffset.UTC);
        String input = String.valueOf(secondsSinceEpoch);
        int midpoint = input.length() / 2;
        String firstPart = input.substring(0, midpoint);
        String secondPart = input.substring(midpoint);
        String uniqueString =firstPart+user+ uuid.toString()+voye+ secondPart;
        return uniqueString;
    }
}
