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
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class IndexFilter
 */
@WebFilter("/index.jsp")
public class IndexFilter implements Filter {

	private static final String HOME_JSP = "/clientMain.jsp";
	private static final String APPLICATION_NAME = "/MBankWeb";
	private static final String CLIENT_ACTION_ATTR = "client_action";
	private static final String FORWARD_PATH = "/Controller?command=client_main"; 

	
    /**
     * Default constructor. 
     */
    public IndexFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpSession session = req.getSession(false);
		if(session != null && req.getSession().getAttribute(CLIENT_ACTION_ATTR) != null)
		{

			System.out.println("Valid session exists, returning to client main page.");
			req.getRequestDispatcher(FORWARD_PATH).forward(request, response);
		}
		else
		{
			chain.doFilter(request, response);
		}

	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
