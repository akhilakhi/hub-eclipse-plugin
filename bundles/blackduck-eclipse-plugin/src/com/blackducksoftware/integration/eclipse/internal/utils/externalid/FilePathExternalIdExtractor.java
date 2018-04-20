/**
 * com.blackducksoftware.integration.eclipse.plugin
 *
 * Copyright (C) 2018 Black Duck Software, Inc.
 * http://www.blackducksoftware.com/
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.blackducksoftware.integration.eclipse.internal.utils.externalid;

import java.net.URL;
import java.util.Optional;

import com.blackducksoftware.integration.hub.bdio.model.externalid.ExternalId;
import com.blackducksoftware.integration.hub.bdio.model.externalid.ExternalIdFactory;

public class FilePathExternalIdExtractor {
    private final ExternalIdFactory externalIdFactory;

    public FilePathExternalIdExtractor() {
        externalIdFactory = new ExternalIdFactory();
    }

    public Optional<ExternalId> getExternalIdFromLocalMavenUrl(final URL filePath, final URL localMavenRepoPath) {
        ExternalId constructedExternalId = null;

        String stringPath = filePath.getFile();
        stringPath = stringPath.replace(localMavenRepoPath.getFile(), "");
        final String[] pathArray = stringPath.split("/");
        final StringBuilder groupIdBuilder = new StringBuilder();
        for (int i = 0; i < pathArray.length - 3; i++) {
            groupIdBuilder.append(pathArray[i]);
            if (i != pathArray.length - 4) {
                groupIdBuilder.append(".");
            }
        }
        final String groupId = groupIdBuilder.toString();
        final String artifactId = pathArray[pathArray.length - 3];
        final String version = pathArray[pathArray.length - 2];
        constructedExternalId = externalIdFactory.createMavenExternalId(groupId, artifactId, version);

        return Optional.ofNullable(constructedExternalId);

    }

    public Optional<ExternalId> getExternalIdFromLocalGradleUrl(final URL filePath) {
        ExternalId constructedExternalId = null;

        if (filePath != null) {
            final String[] pathArray = filePath.getFile().split("/");
            if (pathArray.length > 4) {
                final String groupId = pathArray[pathArray.length - 5];
                final String artifactId = pathArray[pathArray.length - 4];
                final String version = pathArray[pathArray.length - 3];
                constructedExternalId = externalIdFactory.createMavenExternalId(groupId, artifactId, version);
            }
        }

        return Optional.ofNullable(constructedExternalId);
    }
}
