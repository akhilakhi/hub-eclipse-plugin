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
package com.blackducksoftware.integration.eclipse.services.connection;

import java.io.IOException;
import java.net.URISyntaxException;

import com.blackducksoftware.integration.eclipse.internal.ComponentModel;
import com.blackducksoftware.integration.exception.IntegrationException;
import com.blackducksoftware.integration.hub.buildtool.Gav;
import com.blackducksoftware.integration.util.TimedLRUCache;

public abstract class AbstractComponentLookupService {
	protected final TimedLRUCache<Gav, ComponentModel> componentLoadingCache;

	private final int CACHE_CAPACITY = 10000;

	private final int CACHE_TTL = 3600000;

	protected final AbstractConnectionService connectionService;

	public AbstractComponentLookupService(final AbstractConnectionService connectionService){
		this.componentLoadingCache = new TimedLRUCache<>(CACHE_CAPACITY, CACHE_TTL);
		this.connectionService = connectionService;
	}

	public abstract ComponentModel lookupComponent(final Gav gav)  throws IOException, URISyntaxException, IntegrationException;

	public boolean hasActiveConnection(){
		return connectionService.hasActiveConnection();
	}

}