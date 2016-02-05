package mbank;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter(urlPatterns = {"/Controller", "/activities.jsp", "/deposits.jsp", "/clientDetails.jsp", "/clientMain.jsp", "/mainMenu.jsp", "/systemProperties.jsp"})
public class SessionFilter implements Filter {

	private static final String APPLICATION_NAME = "/MBankWeb"; 
	private static final String CONTROLLER_PATH ="/Controller";
	private static final String LOGIN_PAGE = "/index.jsp";
	
	private static final String CLIENT_ACTION_ATTR = "client_action";
	private static final String COMMAND_PARAM = "command";
	private static final String LOGIN_COMMAND = "login";
	
    
	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		System.out.println("RequestFilter.doFilter()");
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession(false); 
		HttpServletResponse res = (HttpServletResponse) response;
		
		// login 
		if(req.getParameter(COMMAND_PARAM) != null && req.getParameter(COMMAND_PARAM).equals(LOGIN_COMMAND))
		{
			System.out.println("Forwarding to the Controller for handling login action");
			request.getServletContext().getRequestDispatcher(CONTROLLER_PATH).forward(request, response);
		}
		else
		{

			if(session != null)
			{
				if(req.getSession().getAttribute(CLIENT_ACTION_ATTR) != null)
				{
					System.out.println("Valid session, forwarding to the Controller.");
					request.getServletContext().getRequestDispatcher(CONTROLLER_PATH).forward(request, response);
				} else {
					System.out.println("No valid session/ClientAction - redirecting to login page");
					res.sendRedirect(APPLICATION_NAME + LOGIN_PAGE);
				}
			}
			else
			{
			
				System.out.println("No valid session/ClientAction - redirecting to login page");
				res.sendRedirect(APPLICATION_NAME + LOGIN_PAGE);
			}
		}
	}


	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		
	}

	
	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
