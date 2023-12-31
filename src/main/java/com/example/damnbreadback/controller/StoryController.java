package com.example.damnbreadback.controller;

import com.example.damnbreadback.dto.CommentDTO;
import com.example.damnbreadback.dto.PostDto;
import com.example.damnbreadback.dto.StoryDTO;
import com.example.damnbreadback.entity.Story;
import com.example.damnbreadback.service.StoryService;
import com.example.damnbreadback.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/damnstory")
public class StoryController {

    @Autowired
    private StoryService storyService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<Object> getAllStories(@RequestParam int page) throws ExecutionException, InterruptedException {
        System.out.println("get All Stories");
        List<StoryDTO> list = storyService.findStories(page-1);

        if(list.isEmpty()) return new ResponseEntity<>("null exception", HttpStatus.NO_CONTENT);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getStory(@PathVariable Long id) throws ExecutionException, InterruptedException {
        Optional<Story> story = storyService.getStory(id);
        if(story.isPresent()){
            return ResponseEntity.ok().body(StoryDTO.toDTO(story.get()));
        }
        else return new ResponseEntity<>("null exception", HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{id}/comment")
    public ResponseEntity<Object> commentStory(Authentication authentication, @PathVariable Long id, @RequestBody CommentDTO commentDTO) throws ExecutionException, InterruptedException {
        if(authentication == null) return ResponseEntity.badRequest().body("올바르지 않은 인증입니다");
        commentDTO.setStoryId(id);
        commentDTO.setWriterId(userService.findUserIdById(authentication.getName()));
        boolean result = storyService.createComment(commentDTO);

        if(result) return new ResponseEntity<>("댓글을 작성했습니다.", HttpStatus.OK);
        else return new ResponseEntity<>("댓글을 작성할 수 없습니다.", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{id}/comment")
    public ResponseEntity<Object> getCommentStory(Authentication authentication, @PathVariable Long id) throws ExecutionException, InterruptedException {
        List<CommentDTO> commentDTOList = storyService.getComment(id);

        if(commentDTOList!=null) return ResponseEntity.ok().body(commentDTOList);
        else return new ResponseEntity<>("댓글을 조회할 수 없습니다.", HttpStatus.BAD_REQUEST);
    }



    @RequestMapping(path="/new", method = RequestMethod.POST)
    public ResponseEntity<Object> createStory(Authentication authentication, @RequestBody StoryDTO uploadRequest) throws ExecutionException, InterruptedException {

        Story created = storyService.createStory(authentication.getName(), uploadRequest);
        if(created == null) return new ResponseEntity<>("null exception", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<> (created, HttpStatus.OK);
    }

}