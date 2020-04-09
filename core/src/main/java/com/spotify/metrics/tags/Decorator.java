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

import java.util.Map;

/**
 * Decorates metrics with additional tags and resource identifiers.
 */
public interface Decorator {

    /**
     * Creates and returns a new map with extracted tags added.
     *
     * @param tags map with existing tags
     * @return map with extracted tags added
     */
    Map<String, String> withTags(Map<String, String> tags);

    /**
     * Creates and returns a new map with extracted resource identifiers added.
     *
     * @param resources map with existing resources identifiers.
     * @return map with extracted resources identifiers added.
     */
    Map<String, String> withResources(Map<String, String> resources);
}
