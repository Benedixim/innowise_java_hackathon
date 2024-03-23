package com.ua.money24.helper;

import jakarta.validation.ValidationException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CurrencyCallbackData {
    public static final String SEPARATOR = ":";
    public static final String ID = "id";
    public static final String ACTION = "action";
    private static final String PREFIX = "currency";
    private static final String[] names = new String[]{ID, ACTION};

    private final String[] parts;


    public CurrencyCallbackData(String... parts) {
        var list = new ArrayList<>(Arrays.asList(parts));
        list.add(0, PREFIX);
        this.parts = list.toArray(String[]::new);
    }

    public static Map<String, String> parse(String callbackData) {
        var parts = new LinkedList<>(Arrays.asList(callbackData.split(SEPARATOR)));
        var dataPrefix = parts.poll();
        if (!PREFIX.equals(dataPrefix)) {
            throw new ValidationException(String.format("Bad prefix %s != %s", dataPrefix, PREFIX));
        }
        if (names.length != parts.size()) {
            throw new ValidationException(String.format(
                    "Callback data takes %d arguments but %d were given",
                    names.length,
                    parts.size()
            ));
        }
        return IntStream.range(0, names.length)
                .boxed()
                .collect(Collectors.toMap(i -> names[i], parts::get));
    }

    public static boolean filter(String callbackData) {
        try {
            parse(callbackData);
            return true;
        } catch (ValidationException exception) {
            return false;
        }
    }

    @Override
    public String toString() {
        return String.join(SEPARATOR, parts);
    }
}
