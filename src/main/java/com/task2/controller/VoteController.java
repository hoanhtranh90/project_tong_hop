package com.task2.controller;

import com.task2.model.User;
import com.task2.model.UserDto;
import com.task2.model.Vote;
import com.task2.model.VoteChoice;
import com.task2.repository.VoteChoiceRepository;
import com.task2.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/vote")
public class VoteController {

    @Autowired
    VoteRepository voteRepository;

    @Autowired
    VoteChoiceRepository voteChoiceRepository;

    @GetMapping
    public ResponseEntity<?> getAllVote(){

        return ResponseEntity.ok(voteRepository.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getVote(@PathVariable("id") Long vode_id) {
//        Vote vote = voteRepository.findVById(vode_id);
//        System.out.println(vote.getVoteChoices());
        return ResponseEntity.ok(voteRepository.findById(vode_id));
    }
    @PostMapping
    public ResponseEntity<?> createVote(@RequestBody Vote vote) {
//        Vote vote1 = new Vote();
//        vote1.setTitle(vote.getTitle());
//        vote1.setVoteChoices(vote.getVoteChoices());
        voteRepository.save(vote);
        return ResponseEntity.ok(vote);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> EditAcc(@PathVariable("id") Long vote_id,
                                     @RequestBody Vote vote){


        //ham viet trong service
//        voteChoiceRepository.deleteByVoteId(vote_id);
        voteChoiceRepository.deleteByVoteId(vote_id);
        Vote v = voteRepository.findVById(vote_id);
        v.setTitle(vote.getTitle());
        v.setVoteChoices(vote.getVoteChoices());
        voteRepository.save(v);

        //
        return new ResponseEntity("Update thanh cong", HttpStatus.OK);
    }


    //******************answer**************//
    @PostMapping("/{id}")
    public ResponseEntity<?> voteAnswer(@PathVariable("id") Long vote_id,
                                        @RequestParam("answer") Long answer_id
                                        ){
        VoteChoice v = voteChoiceRepository.findByAnswerId(answer_id);
        v.setTotalVote(v.getTotalVote()+1);
        voteChoiceRepository.save(v);

        return new ResponseEntity("Vote thanh cong", HttpStatus.OK);
    }
}
