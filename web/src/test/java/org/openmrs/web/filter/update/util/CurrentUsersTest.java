/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.web.filter.update.util;

import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.openmrs.User;
import org.openmrs.api.UserService;
import org.openmrs.api.context.Context;
import org.openmrs.web.filter.util.CurrentUsers;
import org.openmrs.web.test.BaseWebContextSensitiveTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;

public class CurrentUsersTest extends BaseWebContextSensitiveTest {

	private static final String USER_SET = "org/openmrs/CurrentUserTest.xml";
	@Autowired
	UserService userService;

	@Test
	
	public void getCurrentUsernames_shoulReturnUserNamesForLoggedInUsers() {
		executeDataSet(USER_SET);
		MockHttpSession session = new MockHttpSession();
		User user = userService.getUser(5508);
		Context.authenticate(user.getUsername(),"User12345");
		Assert.assertEquals(Context.getAuthenticatedUser().getUsername(),user.getUsername());
		User loggedInUser = Context.getAuthenticatedUser();
		CurrentUsers.addUser(session,loggedInUser);
		List<String> currentUserNames = CurrentUsers.getCurrentUsernames(session);
		Assert.assertTrue(currentUserNames.contains("firstaccount"));

	}

}
