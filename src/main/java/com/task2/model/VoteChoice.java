package com.task2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteChoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private long totalVote;

    @Lob
    private String name;

    @ManyToOne(fetch=FetchType.LAZY,cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "vote_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Vote vote;

}
