package com.bionoor.api.utils;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.bionoor.api.models.Media;

public interface ServiceStorageIn {

	

		void init();

		Media store(MultipartFile file, String filename);
		
		List<Media> storeAll(List<MultipartFile> files);

		Stream<Path> loadAll();

		Path load(String filename);

		Resource loadAsResource(String filename);

		void deleteAll();

	
}
