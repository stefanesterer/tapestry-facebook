package net.derstefon.tapestry.facebook.components;

import net.derstefon.tapestry.facebook.services.FacebookService;
import net.derstefon.tapestry.facebook.services.FacebookServiceInformation;
import net.smartam.leeloo.common.exception.OAuthSystemException;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * Represents the Link to authenticate to facebook via oauth2
 * 
 */
public class FacebookAuthenticationLink {

	@Inject
	private FacebookService facebookService;

	@SessionState
	@Property
	private FacebookServiceInformation facebookServiceInformation;

	public String getFacebookAuthentificationLink() throws OAuthSystemException {
		return facebookService.getFacebookAuthentificationLink();
	}

	public boolean isLoggedIn() {
		return facebookServiceInformation.getAccessToken() != null;
	}

}
