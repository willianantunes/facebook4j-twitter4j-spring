package oauth.examples.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oauth.examples.component.SessionObjects;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.RequestToken;
import facebook4j.Facebook;
import facebook4j.FacebookFactory;
import facebook4j.FacebookException;

@Controller
public class TwitterController
{
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(TwitterController.class);
	
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private SessionObjects sessionObjects;
	
	@RequestMapping(value="/twitter/", method=RequestMethod.GET)
	public ModelAndView index()
	{
		return new ModelAndView("twitter/index");
	}
	
	@RequestMapping(value="/twitter/show", method=RequestMethod.GET)
	public ModelAndView show()
	{
		return new ModelAndView("twitter/show");
	}
	
	@RequestMapping(value="/twitter/signin", method=RequestMethod.GET)
	public RedirectView signin() throws TwitterException
	{
		Twitter twitter = new TwitterFactory().getInstance();
		
		// Storing the twitter object for further use
		sessionObjects.setTwitter(twitter);
		
		// Building the correct URL to return to our application
        StringBuffer callbackURL = request.getRequestURL();
        callbackURL.replace(callbackURL.lastIndexOf("/"), callbackURL.length(), "").append("/callback");
        
        // URL to ask for the acceptance of permissions
        // It attaches the URL to return to your application
        RequestToken requestToken = twitter.getOAuthRequestToken(callbackURL.toString());
        sessionObjects.setTwitterRequestToken(requestToken);
        
        return new RedirectView(requestToken.getAuthenticationURL());
	}
	
	@RequestMapping(value="/twitter/callback", method=RequestMethod.GET)
	public RedirectView callback(String oauth_verifier) throws TwitterException
	{
		sessionObjects.getTwitter().getOAuthAccessToken(sessionObjects.getTwitterRequestToken(), oauth_verifier);
		sessionObjects.setTwitterRequestToken(null);
		
		StringBuffer url = request.getRequestURL().replace(request.getRequestURL().lastIndexOf("/"), 
				request.getRequestURL().length(), "/show");

		return new RedirectView(url.toString());
	}
	
	@RequestMapping(value="/twitter/logout", method=RequestMethod.GET)
	public RedirectView logout() throws IOException
	{
		// Flushs up the session
        request.getSession().invalidate();
        
		StringBuffer url = request.getRequestURL().replace(request.getRequestURL().lastIndexOf("/"), request.getRequestURL().length(), "/");
        return new RedirectView(url.toString());
	}
	
	@RequestMapping(value="/twitter/post", method=RequestMethod.POST)
	public ModelAndView post(@RequestParam String message) throws TwitterException
	{
		sessionObjects.getTwitter().updateStatus(message);

		ModelAndView modelAndView = new ModelAndView("twitter/show");		
		modelAndView.addObject("messageSuccess", "See your twitter account! <br />The message has been posted in it!");
		
        return modelAndView;
	}
}
