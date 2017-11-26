/**
 * 
 */
package com.sai.user.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

/**
 * @author Saikrishna Gudla
 *
 */

@Component
public class UserUtils {
	
	@SuppressWarnings("deprecation")
	public boolean validateDate(String dt){
		DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");

		try {
		     df.parse(dt);
		     String[] year=dt.split("-");
		     Date dd = new Date();
		     if(Integer.parseInt(year[2])<dd.getYear()){
		    	 return true; 
		     }else{
		    	 return false;
		     }
		    
		} catch (ParseException e) {
		     return false;
		}
	}

}
