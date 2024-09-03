package smsm.bookRental.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import smsm.bookRental.converter.AnswerConverter;
import smsm.bookRental.dto.AnswerDto;
import smsm.bookRental.entity.Answer;
import smsm.bookRental.entity.Member;
import smsm.bookRental.entity.Question;
import smsm.bookRental.excption.DataNotFoundException;
import smsm.bookRental.repository.MemberRepository;
import smsm.bookRental.service.AnswerService;
import smsm.bookRental.service.QuestionService;

import java.nio.file.AccessDeniedException;

@RequiredArgsConstructor
@RequestMapping("answer")
@Controller
public class AnswerController {

    private final AnswerService answerService;
    private final QuestionService questionService;
    private final MemberRepository memberRepository;


    // 새 답변 등록
    @PostMapping("/new/{id}")
    public String create(Model model, @PathVariable("id") Long id,
                         @RequestParam(value = "content") String content,
                         @AuthenticationPrincipal UserDetails userDetails) {

        Question question = this.questionService.getQuestion(id);

        Member member = this.memberRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        this.answerService.create(question, member, content);
        return String.format("redirect:/question/detail/%s", id);
    }


    // 답변 수정 -> form으로 이동
    @GetMapping("/edit/{id}")
    public String editAnswerForm(Model model, @PathVariable("id") Long id,
                                 @AuthenticationPrincipal UserDetails userDetails) {
        Answer answer = answerService.getAnswer(id);
        Member member = this.memberRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        if (!answer.getMember().equals(member)) {
            return "error/accessDenied";
        }

        AnswerDto answerDto = AnswerConverter.toDto(answer);
        model.addAttribute("answer", answerDto);
        return "answer/editForm";
    }

    @PostMapping("/edit/{id}")
    public String editAnswer(@PathVariable("id") Long id,
                             @RequestParam(value = "content") String content,
                             @AuthenticationPrincipal UserDetails userDetails) {

        Member member = this.memberRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        try {
            answerService.update(id, content, member);
        } catch (RuntimeException e) {
            return "error/accessDenied";
        } catch (Exception e) {
            e.printStackTrace();
            return "error/accessDenied";
        }

        Answer answer = answerService.getAnswer(id);
        return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
    }


    // 답변 삭제
    @PostMapping("/delete/{id}")
    public String deleteAnswer(@PathVariable("id") Long id,
                               @AuthenticationPrincipal UserDetails userDetails) {

        Member member = this.memberRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        try {
            Answer answer = answerService.getAnswer(id);
            if (!answer.getMember().equals(member)) {
                return "error/accessDenied";
            }
            answerService.delete(id, member);
        } catch (DataNotFoundException e) {
            return "error/dataNotFound";
        } catch (RuntimeException e) {
            return "error/accessDenied";
        } catch (Exception e) {
            e.printStackTrace();
            return "error/accessDenied";
        }

        return String.format("redirect:/question/detail/%s", id);
    }
}
