package cn.fjlcx.application.config;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;

public class CustomPathMatchingFilterChainResolver extends PathMatchingFilterChainResolver {  
	  private CustomDefaultFilterChainManager customDefaultFilterChainManager;  
	  public void setCustomDefaultFilterChainManager(  
	        CustomDefaultFilterChainManager customDefaultFilterChainManager) {  
	      this.customDefaultFilterChainManager = customDefaultFilterChainManager;  
	      setFilterChainManager(customDefaultFilterChainManager);  
	  }  
	  
	  public FilterChain getChain(ServletRequest request, ServletResponse response, FilterChain originalChain) {  
	      FilterChainManager filterChainManager = getFilterChainManager();  
	      if (!filterChainManager.hasChains()) {  
	          return null;  
	      }  
	      String requestURI = getPathWithinApplication(request);  
	      List<String> chainNames = new ArrayList<String>();  
	      for (String pathPattern : filterChainManager.getChainNames()) {  
	        if (pathMatches(pathPattern, requestURI)) {  
	        chainNames.add(pathPattern);  
	        }  
	      }  
	      if(chainNames.size() == 0) {  
	        return null;  
	      }  
	      return customDefaultFilterChainManager.proxy(originalChain, chainNames);  
	  }  
	}   
