package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.model.Post;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;


@Repository
public class PostRepository {
    private final List<Post> postList = new CopyOnWriteArrayList<>();
    private final AtomicLong postID = new AtomicLong(0);

    public List<Post> all() {
        return postList;
    }

    public Optional<Post> getById(long id) {
        Post post = null;
        for (Post p : postList) {
            if (p.getId() == id) post = p;
        }
        return Optional.ofNullable(post);
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            postID.getAndIncrement();
            post.setId(postID.get());
        } else {
            for (Post p : postList) {
                if (p.getId() == post.getId()) {
                    p.setContent(post.getContent());
                } else {
                    postID.getAndIncrement();
                    post.setId(postID.get());
                }
            }
        }
        postList.add(post);
        return post;
    }

    public void removeById(long id) {
        postList.removeIf(p -> p.getId() == id);
    }
}
