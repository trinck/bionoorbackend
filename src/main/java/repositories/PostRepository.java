package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import models.Category;
import models.Post;
import models.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByPublished(boolean published);
    List<Post> findByTitleContaining(String title);
    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);
	List<Post> findByTitleContainingIgnoreCase(String keyword);
}

