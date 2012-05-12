package net.derstefon.tapestry.facebook.components;

import net.derstefon.tapestry.facebook.services.FacebookServiceInformation;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Submit;
import org.apache.tapestry5.corelib.components.TextArea;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.FacebookType;

public class FacebookStatusUpdate {

	@SessionState
	@Property
	private FacebookServiceInformation information;

	@Component(parameters = { "value=statusMessageValue" })
	private TextArea statusMessage;

	@Component
	private Form form;

	@Component
	private Submit submit;

	@Property
	private String statusMessageValue;

	public void onSuccessFromForm() {
		FacebookClient facebookClient = new DefaultFacebookClient(
				information.getAccessToken());

		facebookClient.publish("me/feed", FacebookType.class,
				Parameter.with("message", statusMessageValue));
	}

}
