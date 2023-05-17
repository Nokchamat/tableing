package com.zerobase.zerobasetableing.domain.form;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignInForm {

    private String userId;

    private String password;

}
