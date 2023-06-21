package com.bionoor.api.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.bionoor.api.models.Media;
import com.bionoor.api.services.MediaService;

@Controller
public class AdminMedia {

	
	@Value("${app.name}")
	private String name;
	@Value("${app.logo}")
	 private String logo;
	
	@Autowired
	private MediaService mediaService;
	
	@GetMapping(value = "/medias")
	public String getMedias(Model model) {
		
		model.addAttribute("name", name);
		
		List<Media>  listmedias = this.mediaService.getAll();
		
		int size = listmedias.size();
		int elementByCol = size/3;
		List<Media> col1 = listmedias.subList(0, elementByCol);
		List<Media> col2 = listmedias.subList(elementByCol, elementByCol*2);
		List<Media> col3 = listmedias.subList(elementByCol*2, size);
		
		List<List<Media>> medias = new ArrayList();
		medias.add(col1);
		medias.add(col2);
		medias.add(col3);
		
		model.addAttribute("medias", medias);
		
		return "medias/listmedias";
	}
}
