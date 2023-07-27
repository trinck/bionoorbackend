package com.bionoor.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bionoor.api.models.Media;
import com.bionoor.api.repositories.MediaRepository;

import jakarta.persistence.EntityNotFoundException;


@Service
public class MediaService{

	@Autowired
	private MediaRepository mediaRepository;
	

	public Media add(Media toSave) {
		// TODO Auto-generated method stub
		return this.mediaRepository.save(toSave);
	}

	

	public List<Media> getAll() {
		// TODO Auto-generated method stub
		return this.mediaRepository.findAll();
	}

	

	public String delete(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Media modify(Media modified) {
		// TODO Auto-generated method stub
		return this.mediaRepository.save(modified);
	}


	public Media getById(Long id) {
		
		
		Media media = this.mediaRepository.findById(id).orElse(null);
		
		if(media == null) {
			throw new EntityNotFoundException("media with id: "+id+" doesn't exists");
		}
		return media;
	}

   

}
