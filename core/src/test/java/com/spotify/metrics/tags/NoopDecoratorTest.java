package com.spotify.metrics.tags;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import org.junit.Test;

public class NoopDecoratorTest {

    @Test
    public void testWithTags() {
        final NoopDecorator decorator = new NoopDecorator();
        final Map<String, String> currentMap = ImmutableMap.of("foo", "bar","cluster", "cluster1");
        final Map<String, String> out = decorator.withTags(currentMap);
        assertThat(out, is(currentMap));
    }

    @Test
    public void testResourceTags() {
        final NoopDecorator decorator = new NoopDecorator();
        final Map<String, String> currentMap = ImmutableMap.of("foo", "bar", "cluster", "cluster1");
        final Map<String, String> out = decorator.withResources(currentMap);
        assertThat(out, is(currentMap));
    }
}