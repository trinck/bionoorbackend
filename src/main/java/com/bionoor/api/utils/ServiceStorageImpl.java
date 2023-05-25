package com.bionoor.api.utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.bionoor.api.models.Media;
import com.bionoor.api.services.MediaService;

@Service
public class ServiceStorageImpl  implements ServiceStorageIn{

	@Value("${app.moduls.media.storage}")
	private  String rootMedia;
	
	@Value("${app.users.profile.storage}")
	private String rootProfile;
	
	private Path pathMedia;
	
	@Autowired
	MediaService mediaService;
	
	@Override
	public void init() {
		
		this.pathMedia = Paths.get(rootMedia);
		
		try {
		      Files.createDirectories(this.pathMedia);
		    } catch (IOException e) {
		      throw new RuntimeException("Could not initialize folder for media!");
		    }
	}

	@Override
	public Media store(MultipartFile file, String filename) {
	
		Media media = new Media();
				
		media.setSize(file.getSize());
		
		media.setType(file.getContentType());
		
		
		
		try {
		      Files.copy(file.getInputStream(), this.pathMedia.resolve(filename));
		      media.setName(filename);
		    } catch (Exception e) {
		      if (e instanceof FileAlreadyExistsException) {
		    	      
		        try { 
	    			 
		    		Date date = new Date();
					Files.copy(file.getInputStream(), this.pathMedia.resolve(""+date.getTime()+filename));
					media.setName(""+date.getTime()+filename);
					
				} catch (IOException eio) {
					// TODO Auto-generated catch block
					eio.printStackTrace();
				}
		        
		      }else {
		    	  
		    	  e.printStackTrace();;
		      }
		      
		      
		    }
		
		media.setUrl(this.rootMedia+"/"+media.getName());
		return this.mediaService.add(media);
		
	}

	@Override
	public Stream<Path> loadAll() {
	
		try {
		      return Files.walk(this.pathMedia, 1).filter(path -> !path.equals(this.rootMedia)).map(this.pathMedia::relativize);
		    } catch (IOException e) {
		      throw new RuntimeException("Could not load the files!");
		    }
		  
	}

	@Override
	public Path load(String filename) {
		
		try {
		      Path file = this.pathMedia.resolve(filename);
		      Resource resource = new UrlResource(file.toUri());

		      if (resource.exists() || resource.isReadable()) {
		        return (Path) resource;
		      } else {
		        throw new RuntimeException("Could not read the file!");
		      }
		    } catch (MalformedURLException e) {
		      throw new RuntimeException("Error: " + e.getMessage());
		    }
	}

	@Override
	public Resource loadAsResource(String filename) {
		
		try {
		      Path file = this.pathMedia.resolve(filename);
		      Resource resource = new UrlResource(file.toUri());

		      if (resource.exists() || resource.isReadable()) {
		        return  resource;
		      } else {
		        throw new RuntimeException("Could not read the file!");
		      }
		    } catch (MalformedURLException e) {
		      throw new RuntimeException("Error: " + e.getMessage());
		    }
	}

	@Override
	public void deleteAll() {
		
		FileSystemUtils.deleteRecursively(this.pathMedia.toFile());
		
	}

	@Override
	public List<Media> storeAll(List<MultipartFile> files) {
		
		
		List<Media> medias = new ArrayList<>();
		
		for(MultipartFile file : files ) {
			
			Media media = new Media();
			
			media.setSize(file.getSize());
			
			media.setType(file.getContentType());
			
			
			try {
			      Files.copy(file.getInputStream(), this.pathMedia.resolve(file.getOriginalFilename()));
			      media.setName(file.getOriginalFilename());
			    }catch (Exception e) {
				      if (e instanceof FileAlreadyExistsException) {
			    	      
					        try { 
				    			 
					    		Date date = new Date();
								Files.copy(file.getInputStream(), this.pathMedia.resolve(""+date.getTime()+file.getOriginalFilename()));
								media.setName(""+date.getTime()+file.getOriginalFilename());
								
							} catch (IOException eio) {
								// TODO Auto-generated catch block
								eio.printStackTrace();
							}
					        
					      }else {
					    	  
					    	  e.printStackTrace();;
					      }
					      
					    }
			
							media.setUrl(this.rootMedia+"/"+media.getName());
							
							medias.add(media);
					}
		
		
		
		return medias;
	}

}
