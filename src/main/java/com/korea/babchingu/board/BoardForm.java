package com.korea.babchingu.board;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardForm {
    @NotEmpty(message = "제목을 입력해주세요")
    private String title;
    @NotEmpty(message = "내용을 입력해주세요")
    @Size(max = 5000, message = "내용은 최대 5000자까지 입력할 수 있습니다.")
    private String content;

//    @NotEmpty(message = "주소를 입력해주세요")
    private String address;
    private String jibun;
    private String restName;

}
