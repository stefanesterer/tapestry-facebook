package net.derstefon.tapestry.facebook.components;

import net.derstefon.tapestry.facebook.services.FacebookService;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Submit;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.annotations.Inject;

public class FacebookLinkPublish {

	@Inject
	private FacebookService facebookService;
	
	@Component(parameters = { "value=linkValue" })
	private TextField link;

	@Component
	private Form form;

	@Component
	private Submit submit;

	@Property
	private String linkValue;

	public void onSuccessFromForm() {
		facebookService.publishLink(linkValue);
	}
}
