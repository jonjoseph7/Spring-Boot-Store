package com.jonathanjoseph.store.users;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UpdateUserRequest {
    private String name;
    private String email;
}
