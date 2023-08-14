package com.example.damnbreadback.repository;

import com.example.damnbreadback.entity.Story;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoryRepository extends JpaRepository<Story, Long> {

//    @Autowired
//    SessionFactory sessionFactory = null;
//
//    public default List<Story> getAllStories() throws ExecutionException, InterruptedException {
//        Session session = sessionFactory.getCurrentSession();
//        String hql = "from Story";
//
//        Query<Story> query = session.createQuery(hql, Story.class);
//        List<Story> storyList = query.getResultList();
//
//        return storyList;
//    }

//    public default Story getStory(Long storyId) throws ExecutionException, InterruptedException {
//        Session session = sessionFactory.getCurrentSession();
//        Story story = (Story) session.get(Story.class, storyId);
//
//        return story;
//    }


    @Override
    List<Story> findAll();

    @Override
    Optional<Story> findById(Long storyId);

    Page<Story> findAllByOrderByCreatedDateDesc(PageRequest pageable);


    @Override
    <S extends Story> S save(S entity);
}
