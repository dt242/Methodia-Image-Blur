package io;

import factory.FilterFactory;
import filters.ImageFilter;

import java.util.ArrayList;
import java.util.List;

public class CommandLineParser {

    public static List<ImageFilter> parseFilters(String[] args) {
        List<ImageFilter> pipeline = new ArrayList<>();
        int i = 1;

        while (i < args.length) {
            String filterName = args[i];
            if (!FilterFactory.isFilter(filterName)) {
                throw new IllegalArgumentException("Expected filter name, but received: " + filterName);
            }
            i++;
            List<String> currentParams = new ArrayList<>();

            while (i < args.length && !FilterFactory.isFilter(args[i])) {
                currentParams.add(args[i]);
                i++;
            }

            ImageFilter filter = FilterFactory.createFilter(filterName, currentParams);
            pipeline.add(filter);
        }

        return pipeline;
    }
}