package com.example.damnbreadback.service;

import com.example.damnbreadback.dto.PostDto;
import com.example.damnbreadback.dto.PostFilter;
import com.example.damnbreadback.dto.UserDTO;
import com.example.damnbreadback.entity.History;
import com.example.damnbreadback.repository.*;
import com.example.damnbreadback.entity.Post;
import com.example.damnbreadback.entity.Scrap;
import com.example.damnbreadback.entity.User;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    @Autowired
    private final PostRepository postRepository;

    @Autowired
    private final HistoryRepository historyRepository;

    @Autowired
    private final CareerRepository careerRepository;
    @Autowired
    private final UserService userService;

    @Autowired
    private final ScrapRepository scrapRepository;

    @Override
    public List<Post> getPosts() throws ExecutionException, InterruptedException {
        return postRepository.findAll();
    }

    @Override
    public Long createPost(String writerId, Post postRequest) throws ExecutionException, InterruptedException {
        postRequest.setPublisher(userService.findUserIdById(writerId));
        postRequest.setWorkDay(postRequest.getWorkDate().getDayOfWeek().getValue());
        return postRepository.save(postRequest).getPostId();
    }

    public Optional<Post> getPostById(Long id) throws ExecutionException, InterruptedException {
        return postRepository.findById(id);
    }

    @Override
    public Page<Post> findPosts(int page) {
        PageRequest pageRequest = PageRequest.of(page, 20);
        return postRepository.findAllByOrderByCreatedDateDesc(pageRequest);
    }

    @Transactional
    @Override
    public Boolean removePost(Long id) {
        // First, delete associated History records
        historyRepository.deleteByPostPostId(id);

        // Then, delete the Post
        try {
            postRepository.deleteById(id);
            return true; // Deletion was successful
        } catch (EmptyResultDataAccessException e) {
            return false; // Post with the given id doesn't exist
        }
    }

    @Transactional
    public Post patchPostInfo(Long id, Map<Object, Object> fields) throws ExecutionException, InterruptedException {
        Post targetPost = postRepository.findById(id).get();
        if(targetPost == null) return null;

        fields.forEach((key, value) -> {
//            if (key.equals("nickname")) {
//                targetPost.setNickname((String)value);
//            }
//            if (key.equals("pw")) {
//                targetPost.setPw((String)value);
//            }
//            if (key.equals("email")) {
//                targetPost.setEmail((String)value);
//            }
//            if (key.equals("phone")) {
//                targetPost.setPhone((String)value);
//            }
//            if (key.equals("home")) {
//                targetPost.setHome((String)value);
//            }
//            if (key.equals("introduce")) {
//                targetPost.setIntroduce((String)value);
//            }
//            if (key.equals("hopeJob")) {
//                targetPost.setHopeJob((String)value);
//            }
//            if (key.equals("hopeLocation")) {
//                targetPost.setHopeLocation((String)value);
//            }
//            if (key.equals("isPublic")) {
//                targetPost.setIsPublic((String)value);
//            }


        });

//        userRepository.save(User.toEntity(targetUser));


//        System.out.println("target ::: " + targetUser.getUserId());
//
//        if (targetUser == null) {
//            return null;
//        }
//
//        return targetUser;
        return null;
    }

    @Override
    public Page getPostFilter(PostFilter postFilter, int page) {
        PageRequest pageRequest = PageRequest.of(page, 20);

        Specification<Post> spec = (root, query, criteriaBuilder) -> null;

        if(postFilter.getLocation() != null)
            spec = spec.and(PostSpecification.hasLocation(postFilter.getLocation()));
        if(postFilter.getJob() != null)
            spec = spec.and(PostSpecification.hasJob(postFilter.getJob()));
        if(postFilter.getDayOfWeek() != null)
            spec = spec.and(PostSpecification.hasDayOfWeek(postFilter.getDayOfWeek()));
        if(postFilter.getWorkTime() != null) {
            for (Integer i :postFilter.getWorkTime()
                 ) {
                if(i.intValue() == 0) spec = spec.and(PostSpecification.hasWorkTime(7, 12));
                else if(i.intValue() == 1) spec = spec.and(PostSpecification.hasWorkTime(12, 17));
                else if(i.intValue() == 2) spec = spec.and(PostSpecification.hasWorkTime(17, 24));
                else spec = spec.and(PostSpecification.hasWorkTime(0,7));
            }
        }
        if(postFilter.getIsFree()) { // 무관제외
            if(postFilter.getGenderLimit() != null) // 성별 선택
                spec = spec.and(PostSpecification.hasGender(postFilter.getGenderLimit()));
            else { // 무관제외 ㅇ 성별선택 x
                spec = spec.and(PostSpecification.hasGender2(true));
            }
        } else { // 무관 포함
            if(postFilter.getGenderLimit() != null) { // 성별 선택
                spec = spec.and(PostSpecification.hasGender(postFilter.getGenderLimit()));
                spec = spec.and(PostSpecification.hasGender2(false));
            }
        }
        if(postFilter.getMinAge() != -1)
            spec = spec.and(PostSpecification.overAge(postFilter.getMinAge()));
        if(postFilter.getHourPay() != -1)
            spec = spec.and(PostSpecification.overHourPay(postFilter.getHourPay()));
        if(postFilter.getPayMethod() != null)
            spec = spec.and(PostSpecification.hasPayMethod(postFilter.getPayMethod()));

        return postRepository.findAll(spec, pageRequest);
    }

    //의뢰자로 post찾기
    @Override
    public List<Post> getPostByPublisher(Long id){
        return postRepository.findPostsByPublisher(id);
    }

    @Override
    public Boolean bookmark(String name, int postNum) throws ExecutionException, InterruptedException{
        Optional<Post> post = postRepository.findById((long) postNum);
        UserDTO user = userService.getUserById(userService.findUserIdById(name));

        if(post.isPresent()) {
            Scrap newScrap = new Scrap(post.get(), User.toEntity(user));
            scrapRepository.save(newScrap);
            return true;
        }
        else return false;
    }

}