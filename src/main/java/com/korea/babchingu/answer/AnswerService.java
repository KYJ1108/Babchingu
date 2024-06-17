package com.korea.babchingu.answer;

import com.korea.babchingu.DataNotFoundException;
import com.korea.babchingu.board.Board;
import com.korea.babchingu.comment.Comment;
import com.korea.babchingu.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;

    public Answer create(Comment comment, String content, Member member) {
        Answer answer = new Answer();
        answer.setComment(comment);
        answer.setContent(content);
        answer.setMember(member);
        answerRepository.save(answer);

        return answer;
    }

    public Answer getAnswer(Long id) {
        Optional<Answer> answer = this.answerRepository.findById(id);
        if (answer.isPresent()) {
            return answer.get();
        } else {
            throw new DataNotFoundException("answer not found");
        }
    }

    public Answer update(Long id, String content) {
        Answer answer = getAnswer(id);
        answer.setContent(content);
        return answerRepository.save(answer);
    }

    public void delete(Long id) {
        answerRepository.deleteById(id);
    }
}
