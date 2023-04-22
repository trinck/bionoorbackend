package services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import models.Media;
import repositories.MediaRepository;

@Service
public class MediaService {

    @Autowired
    private MediaRepository mediaRepository;

    public Media save(Media media) {
        return mediaRepository.save(media);
    }

    public List<Media> getAllMedia() {
        return mediaRepository.findAll();
    }

    public Media getMediaById(Long id) {
        Optional<Media> media = mediaRepository.findById(id);
        if (media.isPresent()) {
            return media.get();
        } else {
           // throw new NotFoundException("Media not found with id " + id);
        	return media.get();
        }
    }

    public void deleteMediaById(Long id) {
        Optional<Media> media = mediaRepository.findById(id);
        if (media.isPresent()) {
            mediaRepository.deleteById(id);
        } else {
           // throw new NotFoundException("Media not found with id " + id);
        }
    }

}
