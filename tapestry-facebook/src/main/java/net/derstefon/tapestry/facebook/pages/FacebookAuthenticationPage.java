package net.derstefon.tapestry.facebook.pages;

import java.io.IOException;

import net.derstefon.tapestry.facebook.components.FacebookAuthenticationLink;
import net.derstefon.tapestry.facebook.components.FacebookLinkPublish;
import net.derstefon.tapestry.facebook.components.FacebookStatusUpdate;
import net.derstefon.tapestry.facebook.components.FacebookUserInformation;
import net.derstefon.tapestry.facebook.services.FacebookService;
import net.derstefon.tapestry.facebook.services.FacebookServiceInformation;
import net.smartam.leeloo.common.exception.OAuthProblemException;
import net.smartam.leeloo.common.exception.OAuthSystemException;

import org.apache.tapestry5.annotations.ActivationRequestParameter;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.corelib.components.ActionLink;
import org.apache.tapestry5.ioc.annotations.Inject;

public class FacebookAuthenticationPage {

	@Component
	private FacebookAuthenticationLink faceBookAuthentication;

	@Component
	private FacebookUserInformation userInformation;

	@Component
	private FacebookStatusUpdate statusUpdate;

	@Component
	private FacebookLinkPublish linkPublish;

	@Component
	private ActionLink logout;

	@Property
	@ActivationRequestParameter
	private String code;

	@Inject
	private FacebookService facebookService;

	@SessionState
	@Property
	private FacebookServiceInformation information;

	@SetupRender
	private void setup() throws IOException, OAuthSystemException,
			OAuthProblemException {
		if (code != null) {
			facebookService.getUserAccessToken(code);
		}
	}

	void onActionFromLogout() {
		information.setActionToken(null);
	}

}
