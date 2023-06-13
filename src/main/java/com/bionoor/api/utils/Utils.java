package com.bionoor.api.utils;

import org.hibernate.Hibernate;

public class Utils {

	
	
	
	 @SuppressWarnings({"unchecked"})
	  protected static <T> T getTargetObject(Object proxy, Class<T> targetClass) throws Exception {
		 if (Hibernate.isInitialized(proxy) && proxy.getClass().getName().contains("HibernateProxy")) {
		      return targetClass.cast(Hibernate.unproxy(proxy));
		    } else {
		      return targetClass.cast(proxy);
		    }
		 
	  }
	
}
