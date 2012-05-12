package net.derstefon.tapestry.facebook.services;

import java.io.IOException;

import net.derstefon.tapestry.facebook.pages.FacebookAuthenticationPage;
import net.smartam.leeloo.client.OAuthClient;
import net.smartam.leeloo.client.URLConnectionClient;
import net.smartam.leeloo.client.request.OAuthClientRequest;
import net.smartam.leeloo.client.response.GitHubTokenResponse;
import net.smartam.leeloo.client.response.OAuthAccessTokenResponse;
import net.smartam.leeloo.common.exception.OAuthProblemException;
import net.smartam.leeloo.common.exception.OAuthSystemException;
import net.smartam.leeloo.common.message.types.GrantType;

import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.ApplicationStateManager;
import org.apache.tapestry5.services.PageRenderLinkSource;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.FacebookType;

public class FacebookService {

	private final PageRenderLinkSource linkSource;
	private final ApplicationStateManager applicationStateManager;

	private final static String SCOPE = "user_photos,user_videos,email,user_birthday,publish_actions,offline_access,publish_stream,status_update";

	private String callBackUrl = null;
	private final String clientId;
	private final String clientSecret;

	public FacebookService(@Symbol(FacebookSymbols.CLIENT_ID) String clientId,
			@Symbol(FacebookSymbols.CLIENT_SECRET) String clientSecret,
			PageRenderLinkSource linkSource,
			ApplicationStateManager applicationStateManager) {
		super();
		this.linkSource = linkSource;
		this.applicationStateManager = applicationStateManager;
		this.clientId = clientId;
		this.clientSecret = clientSecret;
	}

	public void getUserAccessToken(String code) throws IOException,
			OAuthSystemException, OAuthProblemException {

		OAuthClientRequest request = OAuthClientRequest
				.tokenLocation("https://graph.facebook.com/oauth/access_token")
				.setGrantType(GrantType.AUTHORIZATION_CODE)
				.setClientId(clientId).setClientSecret(clientSecret)
				.setRedirectURI(getCallBackUrl()).setCode(code)
				.setParameter("scope", SCOPE).buildBodyMessage();
		OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
		OAuthAccessTokenResponse oAuthResponse = oAuthClient.accessToken(
				request, GitHubTokenResponse.class);

		String accessToken = oAuthResponse.getAccessToken();
		// String expiresIn = oAuthResponse.getExpiresIn();

		FacebookServiceInformation info = getFacebookServiceInformation();
		info.setActionToken(accessToken);
	}

	private FacebookServiceInformation getFacebookServiceInformation() {
		return applicationStateManager.get(FacebookServiceInformation.class);
	}

	public String getCallBackUrl() {

		if (callBackUrl == null) {
			callBackUrl = linkSource.createPageRenderLink(
					FacebookAuthenticationPage.class).toAbsoluteURI()
					+ "/";
		}
		return callBackUrl;
	}

	public String getFacebookAuthentificationLink() throws OAuthSystemException {
		return OAuthClientRequest
				.authorizationLocation(
						"https://graph.facebook.com/oauth/authorize")
				.setClientId(clientId).setRedirectURI(getCallBackUrl())
				.buildQueryMessage().getLocationUri();
	}

	public void publishLink(String link) {
		FacebookClient facebookClient = new DefaultFacebookClient(
				getFacebookServiceInformation().getAccessToken());
		facebookClient.publish("me/feed", FacebookType.class,
				Parameter.with("link", link));
	}

}
