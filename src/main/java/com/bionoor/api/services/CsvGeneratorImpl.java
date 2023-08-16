package com.bionoor.api.services;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.bionoor.api.models.Product;
import com.opencsv.CSVWriter;
@Service
public class CsvGeneratorImpl  implements CsvGeneratorIn{
	 
	 public byte[] generateCsv(List<Object> entities)  {
	       
	        try  {
	        	
	        	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	             OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
	             CSVWriter csvWriter = new CSVWriter(writer);
	             
	        	
	            // Write header
	            Class cls = entities.get(0).getClass();
	            List<Class> classes = new ArrayList();
	            List<Field> allFields = new ArrayList();
	            Class tempClass = cls;
	            
	           	            
	            while(tempClass.getSuperclass()!= null) {
	            	
	            	tempClass = tempClass.getSuperclass();
	            	classes.add(tempClass);
	            	allFields.addAll(List.of(tempClass.getDeclaredFields()));
	            }
	            
	           
	            classes.add(cls);
	            allFields.addAll(List.of(cls.getDeclaredFields()));
	           // Class parentClasse =  cls.getSuperclass();
	           
	        	            
	            List<String> allowedTypes = List.of("String","Double", "Long","Boolean","Integer","Date","int","boolean");
	         
	            
	            Map<String,Method> methods = new HashMap<>();
	            
	            
	            for(Class classe: classes) {
	            	
	            	 for(Method method: classe.getMethods()) {
	 	            	
	 	            	if(method.getName().startsWith("get") && allowedTypes.contains(method.getReturnType().getSimpleName())) {
	 	            		
	 	            		
	 	            		String methodName = method.getName().substring(3);
	 	            		
	 	            		String firstChar = methodName.substring(0, 1);
	 	            		methodName = methodName.replaceFirst(firstChar, firstChar.toLowerCase());
	 	            		methods.put(methodName, method);
	 	            	}else if(method.getName().startsWith("is") && allowedTypes.contains(method.getReturnType().getSimpleName())) {
	 	            		
							/*
							 * String methodName = method.getName().substring(2);
							 * 
							 * String firstChar = methodName.substring(0, 1); methodName =
							 * methodName.replaceFirst(firstChar, firstChar.toLowerCase());
							 * parentMethod.put(methodName, method);
							 */
	 	            		methods.put(method.getName(), method);
	 	            	}
	 	            	
	 	            }
	            	
	            }
	            
	           
	            
	            List<String> headerList = new ArrayList<String>();
	            
	            for(Field field: allFields) {
	            	if(allowedTypes.contains(field.getType().getSimpleName())) {
	            		headerList.add(field.getName());
	            	}
	            }
	            
	            //write header
	            csvWriter.writeNext(headerList.toArray(new String[0]));
	            
	            
	           
	            //write data
	            for(Object p: entities) {
	            	
	            	 List<String> data = new ArrayList();
	            	for(Field field: allFields) {
		            	if(allowedTypes.contains(field.getType().getSimpleName())) {
		            		headerList.add(field.getName());
		            		try {
								data.add(String.valueOf(methods.get(field.getName()).invoke(p, null)).replaceAll(";", "-").replaceAll("[\\r\\n]+", " "));
							} catch (IllegalAccessException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
		            	}
		            }
	            	
	            	
	            	 csvWriter.writeNext(data.toArray(new String[0]));
	            }
	            
	            	           	            
	            // Flush the writer to ensure all data is written
	            csvWriter.flush();
	            
		        // Return the CSV file as a resource
		       
	            return outputStream.toByteArray();
	        } catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			} catch (IOException e) {
	            e.printStackTrace();
	        }
			
	        
	       
	        return null;
	       
	    }
	
}
