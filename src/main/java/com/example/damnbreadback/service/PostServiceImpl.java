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

import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    @Autowired
    private final PostRepository postRepository;

    @Autowired
    private final HistoryRepository historyRepository;

    @Autowired
    private final UserRepository userRepository;
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
//        postRequest.setWorkDay(postRequest.getWorkDate().getDayOfWeek().getValue());
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
        try{
            Post targetPost = postRepository.findById(id).get();
            if(targetPost == null) return null;

            fields.forEach((key, value) -> {
                if (key.equals("title")) {
                    targetPost.setTitle((String)value);
                }
                if (key.equals("job")) {
                    targetPost.setJob((String)value);
                }
                if (key.equals("content")) {
                    targetPost.setContent((String)value);
                }
                if (key.equals("branchName")) {
                    targetPost.setBranchName((String)value);
                }
                if (key.equals("location")) {
                    targetPost.setLocation((String)value);
                }
                if (key.equals("workStart")) {
                    targetPost.setWorkStart((Date)value);
                }
                if (key.equals("workEnd")) {
                    targetPost.setWorkEnd((Date)value);
                }
                if (key.equals("hourPay")) {
                    targetPost.setHourPay((Integer) value);
                }
                if (key.equals("payMethod")) {
                    targetPost.setPayMethod((Boolean)value);
                }
                if (key.equals("deadline")) {
                    targetPost.setDeadline((Date) value);
                }
                if (key.equals("recruitNumber")) {
                    targetPost.setRecruitNumber((Integer) value);
                }
                if (key.equals("genderLimit")) {
                    targetPost.setGenderLimit((Boolean)value);
                }
                if (key.equals("careerLimit")) {
                    targetPost.setCareerLimit((Integer)value);
                }
                if (key.equals("ageMax")) {
                    targetPost.setAgeMax((Integer)value);
                }
                if (key.equals("ageMin")) {
                    targetPost.setAgeMin((Integer)value);
                }
                if (key.equals("additionalLimit")) {
                    targetPost.setAdditionalLimit((String)value);
                }

            });

            postRepository.save(targetPost);

            System.out.println("target ::: " + targetPost.getPostId());

            if (targetPost == null) {
                return null;
            }

            return targetPost;

        }catch (Error error){
            System.out.println(error);
            return null;
        }


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
    public Boolean scrap(Long userId, int postNum) throws ExecutionException, InterruptedException{
        Optional<Post> post = postRepository.findById((long) postNum);
        UserDTO user = userService.getUserById(userId);

        if(post.isPresent()) {
            Scrap newScrap = new Scrap(post.get(), User.toEntity(user));
            scrapRepository.save(newScrap);
            return true;
        }
        else return false;
    }

    @Override
    public User reportReview(Long damnId, String badge) throws ExecutionException, InterruptedException {
        Optional<Post> post = postRepository.findById(damnId);

        if(post.isPresent()){
            User matchedUser = post.get().getMatchedUser();
            matchedUser.setBadge(badge);
            userRepository.save(matchedUser);

            return matchedUser;
        }
        else {
            return null;
        }
    }

}