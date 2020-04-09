package com.spotify.metrics.tags;

import java.util.Map;

public class NoopDecorator implements Decorator {

    @Override
    public Map<String, String> withTags(final Map<String, String> tags) {
        return tags;
    }

    @Override
    public Map<String, String> withResources(final Map<String, String> resources) {
        return resources;
    }
}
