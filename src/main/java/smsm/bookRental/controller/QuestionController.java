package smsm.bookRental.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import smsm.bookRental.converter.QuestionConverter;
import smsm.bookRental.dto.QuestionDto;
import smsm.bookRental.entity.Member;
import smsm.bookRental.entity.Question;
import smsm.bookRental.service.AnswerService;
import smsm.bookRental.service.MemberService;
import smsm.bookRental.service.QuestionService;

import java.nio.file.AccessDeniedException;
import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/question")
@Controller
public class QuestionController {

    private final QuestionService questionService;
    private final AnswerService answerService;
    private final MemberService memberService;


    // 질문 목록 전체 불러오기
    @GetMapping("/list")
    public String qList(Model model) {
        List<QuestionDto> questionList = this.questionService.getList();
        model.addAttribute("questionList", questionList);
        return "question/questionList";
    }


    // 질문 상세 페이지 이동
    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Long id) {
        Question question = this.questionService.getQuestion(id);
        QuestionDto questionDto = QuestionConverter.toDto(question);
        model.addAttribute("question", questionDto);
        return "question/detail";
    }


    // 질문 등록 -> form으로 이동
    @GetMapping("/new")
    public String createQuestion(Model model) {
        model.addAttribute("questionDto", new QuestionDto());
        return "question/questionForm";
    }

    @PostMapping("/new")
    public String create(@Valid QuestionDto questionDto,
                         BindingResult bindingResult,
                         Authentication authentication) {

        if (bindingResult.hasErrors()) {
            return "question/questionForm";  // 폼 에러 시 폼 페이지로 다시 이동
        }

        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/member/login";  // 인증되지 않은 사용자는 로그인 페이지로 리다이렉트
        }

        String email = ((UserDetails) authentication.getPrincipal()).getUsername();
        Member member = memberService.findByEmail(email);

        if (member == null) {
            throw new RuntimeException("회원 정보가 존재하지 않습니다.");
        }

        this.questionService.create(questionDto, member);
        return "redirect:/question/list";
    }


    // 질문 수정 -> form을 이동
    @GetMapping("/edit/{id}")
    public String editQuestionForm(Model model, @PathVariable("id") Long id) {
        Question question = this.questionService.getQuestion(id);
        QuestionDto questionDto = QuestionConverter.toDto(question);
        model.addAttribute("question", questionDto);
        return "question/editForm";
    }

    @PostMapping("/edit/{id}")
    public String editQuestion(@PathVariable("id") Long id,
                               @ModelAttribute QuestionDto questionDto,
                               Principal principal) {

        String username = principal.getName();

        try {
            questionService.update(id, questionDto, username);
        } catch (AccessDeniedException e) {
            return "error/accessDenied";
        }

        return "redirect:/question/detail/" + id;
    }
}
