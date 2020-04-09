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

import static com.spotify.metrics.tags.EnvironmentTagExtractor.DEFAULT_FFWD_TAG_PREFIX;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Suppliers;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Extracts tags and resources identifiers from the systems environment variables for.
 * <p>
 * Environment variables matching the prefix
 * {@value EnvironmentTagExtractor#DEFAULT_FFWD_TAG_PREFIX}
 * is extracted and will be returned by {@link #withTags(java.util.Map)}. Environment variables
 * matching the prefix {@value DEFAULT_FFWD_RESOURCE_PREFIX} is extracted and will be returned by
 * {@link #withResources(java.util.Map)}. The prefix will be stripped from the final tag and
 * resource identifier key.
 */
public class CommonEnvironmentDecorator implements Decorator {
    /**
     * Environment variable name prefix for extracting additional resource identifiers.
     */
    public static final String DEFAULT_FFWD_RESOURCE_PREFIX = "FFWD_RESOURCES_";

    private final TagExtractor commonTags;
    private final TagExtractor commonResources;


    CommonEnvironmentDecorator() {
        this(Suppliers.ofInstance(System.getenv())::get);
    }

    @VisibleForTesting
    public CommonEnvironmentDecorator(final Supplier<Map<String, String>> environmentSupplier) {
        this.commonTags = new EnvironmentTagExtractor(DEFAULT_FFWD_TAG_PREFIX, environmentSupplier);
        this.commonResources =
            new EnvironmentTagExtractor(DEFAULT_FFWD_RESOURCE_PREFIX, environmentSupplier);
    }

    public static CommonEnvironmentDecorator create() {
        return new CommonEnvironmentDecorator();
    }

    @Override
    public Map<String, String> withTags(final Map<String, String> tags) {
        return commonTags.addTags(tags);
    }

    @Override
    public Map<String, String> withResources(final Map<String, String> resources) {
        return commonResources.addTags(resources);
    }
}
