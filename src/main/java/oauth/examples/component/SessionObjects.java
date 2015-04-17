package oauth.examples.component;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import twitter4j.Twitter;
import twitter4j.auth.RequestToken;
import facebook4j.Facebook;

@Component("sessionObjects")
@Scope(proxyMode=ScopedProxyMode.TARGET_CLASS, value=WebApplicationContext.SCOPE_SESSION)
public class SessionObjects
{
	private Facebook facebook;
	private Twitter twitter;
	private RequestToken twitterRequestToken;

	public Facebook getFacebook()
	{
		return facebook;
	}

	public void setFacebook(Facebook facebook)
	{
		this.facebook = facebook;
	}

	public Twitter getTwitter()
	{
		return twitter;
	}

	public void setTwitter(Twitter twitter)
	{
		this.twitter = twitter;
	}

	public RequestToken getTwitterRequestToken()
	{
		return twitterRequestToken;
	}

	public void setTwitterRequestToken(RequestToken twitterRequestToken)
	{
		this.twitterRequestToken = twitterRequestToken;
	}
}
