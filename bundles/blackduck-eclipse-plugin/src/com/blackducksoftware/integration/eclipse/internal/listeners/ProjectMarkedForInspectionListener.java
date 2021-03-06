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
package com.blackducksoftware.integration.eclipse.internal.listeners;

import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;

import com.blackducksoftware.integration.eclipse.services.inspector.ComponentInspectorPreferencesService;
import com.blackducksoftware.integration.eclipse.services.inspector.ComponentInspectorService;
import com.blackducksoftware.integration.eclipse.services.inspector.ComponentInspectorViewService;

public class ProjectMarkedForInspectionListener implements IPropertyChangeListener {
    private final ComponentInspectorService inspectorService;
    private final ComponentInspectorViewService inspectorViewService;
    private final ComponentInspectorPreferencesService componentInspectorPreferencesService;

    public ProjectMarkedForInspectionListener(final ComponentInspectorService inspectorService, final ComponentInspectorPreferencesService componentInspectorPreferencesService, final ComponentInspectorViewService inspectorViewService) {
        super();
        this.inspectorService = inspectorService;
        this.inspectorViewService = inspectorViewService;
        this.componentInspectorPreferencesService = componentInspectorPreferencesService;
    }

    @Override
    public void propertyChange(final PropertyChangeEvent event) {
        final String projectName = event.getProperty();
        inspectorService.inspectProject(projectName);

        if (!componentInspectorPreferencesService.isProjectMarkedForInspection(projectName)) {
            inspectorViewService.resetDisplay();
        }
    }

}
