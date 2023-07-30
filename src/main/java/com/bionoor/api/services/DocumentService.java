package com.bionoor.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bionoor.api.models.Document;
import com.bionoor.api.repositories.DocumentRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DocumentService {

	
	@Autowired
	private DocumentRepository documentRepository;
	
	
	public Document add(Document toSave) {
		// TODO Auto-generated method stub
		return this.documentRepository.save(toSave);
	}

	

	public List<Document> getAll() {
		// TODO Auto-generated method stub
		return this.documentRepository.findAll();
	}

	

	public String delete(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Document modify(Document modified) {
		// TODO Auto-generated method stub
		return this.documentRepository.save(modified);
	}


	public Document getById(Long id) {
		
		
		Document document = this.documentRepository.findById(id).orElse(null);
		
		if(document == null) {
			throw new EntityNotFoundException("document with id: "+id+" doesn't exists");
		}
		return document;
	}

   
}
