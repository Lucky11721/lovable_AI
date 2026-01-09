package com.lucky.projects.lovable_clone.error;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(makeFinal = true , level = AccessLevel.PRIVATE)
public class BadRequestException extends RuntimeException {
    String message;

}
