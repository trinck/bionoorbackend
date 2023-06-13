package com.bionoor.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bionoor.api.models.Media;
import com.bionoor.api.repositories.MediaRepository;


@Service
public class MediaService implements ServiceInterface<Media>{

	@Autowired
	private MediaRepository mediaRepository;
	
	@Override
	public Media add(Media toSave) {
		// TODO Auto-generated method stub
		return this.mediaRepository.save(toSave);
	}

	@Override
	public String delete(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Media modify(Media modified) {
		// TODO Auto-generated method stub
		return this.mediaRepository.save(modified);
	}

	@Override
	public Media getById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

   

}
