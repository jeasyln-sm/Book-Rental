package smsm.bookRental.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import smsm.bookRental.entity.Member;
import smsm.bookRental.entity.Question;
import smsm.bookRental.repository.MemberRepository;
import smsm.bookRental.service.AnswerService;
import smsm.bookRental.service.QuestionService;

@RequiredArgsConstructor
@RequestMapping("answer")
@Controller
public class AnswerController {

    private final AnswerService answerService;
    private final QuestionService questionService;
    private final MemberRepository memberRepository;


    @PostMapping("/new/{id}")
    public String create(Model model, @PathVariable("id") Long id,
                         @RequestParam(value = "content") String content,
                         @AuthenticationPrincipal UserDetails userDetails) {

        // 질문 객체를 가져온다
        Question question = this.questionService.getQuestion(id);

        // 로그인 된 사용자 정보로부터 Member 객체를 가져온다
        Member member = this.memberRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        // 답변 생성
        this.answerService.create(question, member, content);
        return String.format("redirect:/question/detail/%s", id);
    }
}
