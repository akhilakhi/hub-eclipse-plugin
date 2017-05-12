/**
 * com.blackducksoftware.integration.eclipse.plugin
 *
 * Copyright (C) 2017 Black Duck Software, Inc.
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

import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;

import com.blackducksoftware.integration.eclipse.internal.ComponentModel;
import com.blackducksoftware.integration.eclipse.services.BlackDuckEclipseServicesFactory;
import com.blackducksoftware.integration.eclipse.services.connection.free.FreeConnectionService;
import com.blackducksoftware.integration.eclipse.services.connection.hub.HubConnectionService;

public class TableDoubleClickListener implements IDoubleClickListener {
	private final HubConnectionService hubConnectionService;
	private final FreeConnectionService freeConnectionService;

	public TableDoubleClickListener(){
		this.hubConnectionService = BlackDuckEclipseServicesFactory.getInstance().getHubConnectionService();
		this.freeConnectionService = BlackDuckEclipseServicesFactory.getInstance().getFreeConnectionService();
	}

	@Override
	public void doubleClick(final DoubleClickEvent event) {
		final IStructuredSelection selection = (IStructuredSelection) event.getSelection();
		if (selection.getFirstElement() instanceof ComponentModel) {
			final ComponentModel selectedObject = (ComponentModel) selection.getFirstElement();
			if (!selectedObject.getComponentIsKnown()) {
				return;
			}
			if(hubConnectionService.hasActiveConnection()){
				hubConnectionService.displayExpandedComponentInformation(selectedObject);
			}else{
				freeConnectionService.displayExpandedComponentInformation(selectedObject);
			}
		}
	}

}