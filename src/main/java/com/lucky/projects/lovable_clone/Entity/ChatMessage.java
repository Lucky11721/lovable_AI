package com.lucky.projects.lovable_clone.Entity;

import com.lucky.projects.lovable_clone.Enum.MessageRole;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessage {

    Long id;
    ChatSession chatSession;

    String content;

    MessageRole  role;

    String toolCalls; // JSON Array of Tools Called

    Integer tokensUsed;

    Instant createdAt;
}
