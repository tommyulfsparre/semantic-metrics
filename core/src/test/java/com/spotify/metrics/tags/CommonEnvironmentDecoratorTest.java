/*
 * Copyright (c) 2020 Spotify AB.
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.spotify.metrics.tags;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class CommonEnvironmentDecoratorTest {

    private Map<String, String> tags;
    private Map<String, String> resources;

    @Before
    public void setUp() {
        tags = ImmutableMap.of("FFWD_TAG_foo", "bar", "FFWD_RESOURCES_bar", "baz",
            "PATH", "ignored:ignored", "FFWD_TAG_oof", "rab");
        resources = ImmutableMap.of("FFWD_TAG_foo", "bar", "FFWD_RESOURCES_bar", "baz",
            "PATH", "ignored:ignored", "FFWD_TAG_oof", "rab");
    }

    @Test
    public void testWithTags() {
        final Supplier<Map<String, String>> environmentSupplier =
            Suppliers.ofInstance(tags);
        final CommonEnvironmentDecorator decorator =
            new CommonEnvironmentDecorator(environmentSupplier::get);
        final Map<String, String> currentMap = ImmutableMap.of("cluster", "cluster1");
        final Map<String, String> out = decorator.withTags(currentMap);

        assertThat(out, is(ImmutableMap.of("foo", "bar", "oof", "rab", "cluster", "cluster1")));
    }

    @Test
    public void testEnvironmentTagsTakesPrecedence() {
        final Supplier<Map<String, String>> environmentSupplier =
            Suppliers.ofInstance(tags);
        final CommonEnvironmentDecorator decorator =
            new CommonEnvironmentDecorator(environmentSupplier::get);
        final Map<String, String> currentMap = ImmutableMap.of("cluster", "cluster1", "foo", "should_replace");
        final Map<String, String> out = decorator.withTags(currentMap);

        assertThat(out, is(ImmutableMap.of("foo", "bar", "oof", "rab", "cluster", "cluster1")));
    }


    @Test
    public void testWithResources() {
        final Supplier<Map<String, String>> environmentSupplier =
            Suppliers.ofInstance(resources);
        final CommonEnvironmentDecorator decorator =
            new CommonEnvironmentDecorator(environmentSupplier::get);
        final Map<String, String> currentMap = ImmutableMap.of("cluster", "cluster1");
        final Map<String, String> out = decorator.withResources(currentMap);

        assertThat(out, is(ImmutableMap.of("bar", "baz", "cluster", "cluster1")));
    }

    @Test
    public void testEnvironmentResourcesTakesPrecedence() {
        final Supplier<Map<String, String>> environmentSupplier =
            Suppliers.ofInstance(tags);
        final CommonEnvironmentDecorator decorator =
            new CommonEnvironmentDecorator(environmentSupplier::get);
        final Map<String, String> currentMap = ImmutableMap.of("cluster", "cluster1", "bar", "should_replace");
        final Map<String, String> out = decorator.withResources(currentMap);

        assertThat(out, is(ImmutableMap.of("bar", "baz", "cluster", "cluster1")));
    }
}