package com.raremile.push.servlet;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.AsyncContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;

/*
 * @author Charu joshi
 */
@WebServlet(urlPatterns = "/*", asyncSupported = true)
public class NewFiWebServlet extends HttpServlet {

	/**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    @RequestMapping
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
	      AsyncContext aCtx = request.startAsync(request, response); 
	      // This could be a cluser-wide cache.
	      ServletContext appScope = request.getServletContext();
	      String task=(String) request.getParameter("task");
	      String taskId=(String) request.getParameter("taskId");
	      String channel=task+"-"+taskId;
	      Map<String, List<AsyncContext>> aucWatchers = (Map<String, List<AsyncContext>>)appScope.getAttribute("watchers");
	      if(aucWatchers==null){
	    	  synchronized (appScope) {
	    		  aucWatchers=new HashMap<String, List<AsyncContext>>();
	    		  appScope.setAttribute("watchers",aucWatchers);
	    	  }
	      }
	      List<AsyncContext> watchers = (List<AsyncContext>)aucWatchers.get(channel);
	      if(watchers==null){
	    	  synchronized (appScope) {
	    		  watchers=new ArrayList<AsyncContext>();
	    		  aucWatchers.put(channel, watchers);
	    	  }
	      }
	      synchronized (appScope) {
	    	  watchers.add(aCtx); // register a watcher
	      }
	      aCtx.setTimeout(90000);
	   }
    public void doPost(HttpServletRequest request, HttpServletResponse resp){
    	ServletContext appScope = request.getServletContext();
    	String task=(String) request.getParameter("task");
	    String taskId=(String) request.getParameter("taskId");
	    String message=(String) request.getParameter("data");
	    String channel=task+"-"+taskId;
	    Map<String, List<AsyncContext>> aucWatchers = (Map<String, List<AsyncContext>>)appScope.getAttribute("watchers");
	    if(aucWatchers==null){
	    	synchronized (appScope) {
	    		aucWatchers=new HashMap<String, List<AsyncContext>>();
	    		appScope.setAttribute("watchers",aucWatchers);
	    	}
	    }
	    List<AsyncContext> watchers = (List<AsyncContext>)aucWatchers.get(channel);
	    if(watchers==null){
	    	synchronized (appScope) {
	    		watchers=new ArrayList<AsyncContext>();
	    	}
	    }
	    synchronized (appScope) {
	    	List<AsyncContext> toremove=new ArrayList<AsyncContext>();
		    for(AsyncContext actx : watchers){
		    	try {
		    		HttpServletResponse resonse=(HttpServletResponse) actx.getResponse();
		    		resonse.setContentType("application/json;charset=UTF-8");
					ServletOutputStream stream=resonse.getOutputStream();
					String str=message;
					stream.write(str.getBytes(Charset.defaultCharset()));
					actx.complete();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}catch(IllegalStateException e){
					e.printStackTrace();
				}
		    	toremove.add(actx);
		    }
		    watchers.removeAll(toremove);
	    }
    }
	   
}