package com.nice.avishkar.entities;

import lombok.*;

@Data
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class CastedVote {
    String name;
    String Candidate;
    String Constituency;
    boolean handled;
}
