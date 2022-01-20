package com.nice.avishkar.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@EqualsAndHashCode
public class CastedVote {
    String name;
    String Candidate;
    boolean handled;
}
