package com.app.babybaby.entity.user;


import com.app.babybaby.type.GenderType;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@ToString
@Table(name = "TBL_KID")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Kid {
    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private Long id;
    @NotNull
    private String kidName;
    @NotNull
    private Long kidAge;
    @NotNull
    @Enumerated(EnumType.STRING)
    private GenderType kidGender;

    @ManyToOne(fetch = FetchType.LAZY)
    private User parent;



}
