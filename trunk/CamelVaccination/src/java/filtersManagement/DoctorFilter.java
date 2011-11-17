package filtersManagement;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logManagement.Log4k;
import userManagement.*;

/**
 *
 * @author FastLDL
 */
public class DoctorFilter implements Filter {
    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
	throws IOException, ServletException {
    } 

    private void doAfterProcessing(ServletRequest request, ServletResponse response)
	throws IOException, ServletException {
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    @Override
    public void doFilter(ServletRequest inRequest, ServletResponse inResponse,
                         FilterChain chain)
	throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest)inRequest;
        HttpServletResponse response = (HttpServletResponse)inResponse;
        String webPage = request.getServletPath();

	doBeforeProcessing(inRequest, inResponse);
	
	Throwable problem = null;
	try {
            User user = (User) request.getSession().getAttribute("loggedUser");
            if (user == null){
                String errMsg = "Un visitatore ha cercato di accedere " + 
                        "indebitamente alla pagina" + webPage;
                Log4k.warn(DoctorFilter.class.getName(), errMsg);
            } else {
                if (!user.getIsDoctor()) {
                    String errMsg = "L'utente " + user.getUsername()
                            + " (id = " + user.getId()
                            + ") ha cercato di accedere indebitamente " +
                            "alla pagina" + webPage;
                    Log4k.warn(DoctorFilter.class.getName(), errMsg);
                } else {
                    chain.doFilter(inRequest, inResponse);
                }
            }
	}
	catch(Throwable t) {
	    // If an exception is thrown somewhere down the filter chain,
	    // we still want to execute our after processing, and then
	    // rethrow the problem after that.
	    problem = t;
	    Log4k.warn(DoctorFilter.class.getName(), t.getMessage());
	}

	doAfterProcessing(inRequest, inResponse);

	// If there was a problem, we want to rethrow it if it is
	// a known type, otherwise log it.
	if (problem != null) {
	    if (problem instanceof ServletException) throw (ServletException)problem;
	    if (problem instanceof IOException) throw (IOException)problem;
	    sendProcessingError(problem, inResponse);
	}
    }
    
    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
	return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
	this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter 
     */
    @Override
    public void destroy() {
        filterConfig = null;
    }

    /**
     * Init method for this filter 
     */
    @Override
    public void init(FilterConfig filterConfig) { 
	this.filterConfig = filterConfig;	
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
	if (filterConfig == null) return ("DoctorFilter()");
	StringBuffer sb = new StringBuffer("DoctorFilter(");
	sb.append(filterConfig);
	sb.append(")");
	return (sb.toString());
    }

    private void sendProcessingError(Throwable t, ServletResponse response) {
	String stackTrace = getStackTrace(t); 

	if(stackTrace != null && !stackTrace.equals("")) {
	    try {
		response.setContentType("text/html");
		PrintStream ps = new PrintStream(response.getOutputStream());
		PrintWriter pw = new PrintWriter(ps); 
		pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N
		    
		// PENDING! Localize this for next official release
		pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n"); 
		pw.print(stackTrace); 
		pw.print("</pre></body>\n</html>"); //NOI18N
		pw.close();
		ps.close();
		response.getOutputStream().close();
	    }
	    catch(Exception ex) {}
	}
	else {
	    try {
		PrintStream ps = new PrintStream(response.getOutputStream());
		t.printStackTrace(ps);
		ps.close();
		response.getOutputStream().close();
	    }
	    catch(Exception ex) {}
	}
    }

    public static String getStackTrace(Throwable t) {
	String stackTrace = null;
	try {
	    StringWriter sw = new StringWriter();
	    PrintWriter pw = new PrintWriter(sw);
	    t.printStackTrace(pw);
	    pw.close();
	    sw.close();
	    stackTrace = sw.getBuffer().toString();
	}
	catch(Exception ex) {}
	return stackTrace;
    }
}
