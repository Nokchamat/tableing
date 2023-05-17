package com.zerobase.zerobasetableing.domain.form;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpForm {

    private String userId;

    private String password;

    private String name;

    private Integer age;

    private String phoneNumber;

}
