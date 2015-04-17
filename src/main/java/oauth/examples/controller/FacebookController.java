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

import facebook4j.Facebook;
import facebook4j.FacebookFactory;
import facebook4j.FacebookException;

@Controller
public class FacebookController
{
	private static Logger logger = Logger.getLogger(FacebookController.class);
	
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private SessionObjects sessionObjects;
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public ModelAndView initial()
	{
		return new ModelAndView("initial/index");
	}
	
	@RequestMapping(value="/facebook/", method=RequestMethod.GET)
	public ModelAndView index()
	{
		return new ModelAndView("facebook/index");
	}
	
	@RequestMapping(value="/facebook/show", method=RequestMethod.GET)
	public ModelAndView show()
	{
		return new ModelAndView("facebook/show");
	}
	
	@RequestMapping(value="/facebook/signin", method=RequestMethod.GET)
	public RedirectView signin() throws IOException
	{
		Facebook facebook = new FacebookFactory().getInstance();
		
		// Storing the facebook object for further use
		sessionObjects.setFacebook(facebook);
		
		// Building the correct URL to return to our application
        StringBuffer callbackURL = request.getRequestURL();
        callbackURL.replace(callbackURL.lastIndexOf("/"), callbackURL.length(), "").append("/callback");
        
        // URL to ask for the acceptance of permissions
        // It attaches the URL to return to your application
        String authAuthorizationURL = facebook.getOAuthAuthorizationURL(callbackURL.toString());
        
        return new RedirectView(authAuthorizationURL);
	}
	
	@RequestMapping(value="/facebook/callback", method=RequestMethod.GET)
	public RedirectView callback(String code) throws FacebookException, IOException
	{
		sessionObjects.getFacebook().getOAuthAccessToken(code);	
		StringBuffer url = request.getRequestURL().replace(request.getRequestURL().lastIndexOf("/"), 
				request.getRequestURL().length(), "/show");
		return new RedirectView(url.toString());
	}
	
	@RequestMapping(value="/facebook/logout", method=RequestMethod.GET)
	public RedirectView logout() throws IOException
	{
		String accessToken = sessionObjects.getFacebook().getOAuthAccessToken().getToken();
		
		// Flush up the session
        request.getSession().invalidate();

        // Log out of action
        StringBuffer next = request.getRequestURL().replace((request.getRequestURL().lastIndexOf("/") + 1), request.getRequestURL().length(), "");
        return new RedirectView("http://www.facebook.com/logout.php?next=" + next.toString() + "&access_token=" + accessToken);
	}
	
	@RequestMapping(value="/facebook/post", method=RequestMethod.POST)
	public ModelAndView post(@RequestParam String message) throws IOException, FacebookException
	{
		sessionObjects.getFacebook().postStatusMessage(message);

		ModelAndView modelAndView = new ModelAndView("facebook/show");		
		modelAndView.addObject("messageSuccess", "See your timeline! The message has been posted in your timeline!");
		
        return modelAndView;
	}
}
