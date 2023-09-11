package com.example.damnbreadback.service;

import com.example.damnbreadback.dto.PostFilter;
import com.example.damnbreadback.dto.UserDTO;
import com.example.damnbreadback.repository.PostRepository;
import com.example.damnbreadback.repository.PostSpecification;
import com.example.damnbreadback.repository.ScrapRepository;
import com.example.damnbreadback.entity.Post;
import com.example.damnbreadback.entity.Scrap;
import com.example.damnbreadback.entity.User;
import com.example.damnbreadback.repository.UserSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    @Autowired
    private final PostRepository postRepository;

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