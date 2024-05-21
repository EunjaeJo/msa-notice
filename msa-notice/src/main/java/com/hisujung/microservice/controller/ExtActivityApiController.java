package com.hisujung.microservice.controller;

import com.hisujung.microservice.dto.ExtActCrawlingDto;
import com.hisujung.microservice.dto.ExtActListResponseDto;
import com.hisujung.microservice.service.ExtActService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RequestMapping("notice/externalact")
@RequiredArgsConstructor
@RestController
public class ExtActivityApiController {

    private final ExtActService extActService;

    @GetMapping("/")
    public List<ExtActListResponseDto> findAll() {
        return extActService.findAllByDesc();
    }

    @GetMapping("/keyword")
    public List<ExtActListResponseDto> findByTitle(@RequestParam String keyword) {
        return extActService.findByTitle(keyword);
    }

/*
    @GetMapping("/id")
    public ExtActListResponseDto findById(@RequestParam String memberId, @RequestParam Long id) {
        return extActService.findById(memberId, id);
    }
*/

    //====== 대외활동 좋아요 눌렀을 때 =======
    @PostMapping("like")
    public Long saveLike(@RequestParam String memberId, @RequestParam Long actId) {
        return extActService.saveLike(memberId, actId);
    }

    //대외활동 좋아요 삭제
    @DeleteMapping("/like-cancel")
    public Long deleteLike(@RequestParam String memberId, @RequestParam Long id) {
        extActService.deleteLike(memberId, id);
        return id;
    }

    //회원의 대외활동 좋아요 리스트 조회
    @GetMapping("/like-list")
    public List<ExtActListResponseDto> findByMember(@RequestParam String memberId) {
        return extActService.findLikedByUser(memberId);
    }

    // 대외활동 크롤링 데이터 저장
//    @RabbitListener(queues = "external_act_queue")
//    public void ExtProcessMessage(ExtActCrawlingDto extActCrawlingDto) {
//        extActService.saveActivity(extActCrawlingDto);
//    }

    //====== 대외활동 참여 체크 눌렀을 때 =======
    @PostMapping("/check")
    public Long saveCheck(@RequestParam String memberId, @RequestParam Long actId) {
        return extActService.saveCheck(actId, memberId);
    }

    @DeleteMapping("/check-cancel")
    public Long deleteCheck(@RequestParam String memberId, @RequestParam Long id) {
        extActService.deleteCheck(memberId, id);
        return id;
    }

    @DeleteMapping("/check-cancel2")
    public List<Long> deleteCheck(@RequestParam String memberId, @RequestParam List<Long> ids) {
        extActService.deleteCheck2(memberId, ids); // List<Long>를 받는 메서드를 호출합니다.
        return ids;
    }


    @GetMapping("/checked-list")
    public List<ExtActListResponseDto> findCheckedByMember(@RequestParam String memberId) {
        return extActService.findCheckedByUser(memberId);
    }
}
