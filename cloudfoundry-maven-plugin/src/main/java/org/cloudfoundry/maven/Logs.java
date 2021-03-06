/*

 * Copyright 2009-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.cloudfoundry.maven;

import org.apache.maven.plugin.MojoExecutionException;
import org.cloudfoundry.client.lib.CloudFoundryException;
import org.springframework.http.HttpStatus;

/**
 * Shows application logs
 *
 * @author Ali Moghadam
 * @since 1.0.0
 *
 * @goal logs
 * @phase process-sources
 */

public class Logs extends AbstractApplicationAwareCloudFoundryMojo {

	@Override
	protected void doExecute() throws MojoExecutionException {

		try {
			getClient().getApplication(getAppname());
		} catch (CloudFoundryException e) {
			getLog().info("Application Not Found");
			return;
		}

		getLog().info("============== /logs/stderr.log ==============" + "\n");
		try {
			getLog().info(getClient().getFile(getAppname(), 0, "logs/stderr.log"));
		} catch (CloudFoundryException e) {
			if (HttpStatus.NOT_FOUND.equals(e.getStatusCode())) {
				getLog().info("File Doesn't Exist");
			}
		}

		getLog().info("============== /logs/stdout.log ==============" + "\n");
		try {
			getLog().info(getClient().getFile(getAppname(), 0, "logs/stdout.log"));
		} catch (CloudFoundryException e) {
			if (HttpStatus.NOT_FOUND.equals(e.getStatusCode())) {
				getLog().info("File Doesn't Exist");
			}
		}
	}
}