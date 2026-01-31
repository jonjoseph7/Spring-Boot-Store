package com.codewithmosh.store.users;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserDto {
    private Long id;
    private String name;
    private String email;
//    @JsonFormat(pattern = "yyyy-mm-dd hh:mm:ss")
//    private LocalDateTime createdAt;
}
