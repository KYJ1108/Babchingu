package com.korea.babchingu.member;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class SendEmailService {
    private final JavaMailSender mailSender;
    private static final String FORM_ADDRESS= "yysh1020@gmail.com";
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    public void createMailAndChargePassword(MemberPwRequestDto memberPwRequestDto) throws CustomException {
        String tempPassword = getTempPassword();
        MailDto dto = new MailDto();
        dto.setAddress(memberPwRequestDto.getEmail());
        dto.setTitle(memberPwRequestDto.getLoginId() + "님의 임시비밀번호 안내 이메일입니다.");
        dto.setMessage("안녕하세요. 임시비밀번호 안내 관련 메일입니다. \n" + memberPwRequestDto.getLoginId() + "님의 임시비밀번호는 [ " + tempPassword + "]입니다.");

        // Update the password in the database
        updatePassword(tempPassword, memberPwRequestDto);

        // Send the email
        mailSend(dto.getAddress(), dto.getTitle(), dto.getMessage());
    }

    public void updatePassword(String tempPassword, MemberPwRequestDto requestDto) {
        String encodedPassword = passwordEncoder.encode(tempPassword);
        Member member = memberRepository.findByLoginId(requestDto.getLoginId()).orElseThrow();

        member.setPassword(encodedPassword);
        memberRepository.save(member);
    }

    public String getTempPassword() {
        char[] charSet = new char[]{
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
        };
        StringBuilder stringBuilder = new StringBuilder();
        int idx;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            stringBuilder.append(charSet[idx]);
        }
        return stringBuilder.toString();
    }

    public void mailSend(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        try {
            mailSender.send(message);
        } catch (Exception e) {
        }
    }
}