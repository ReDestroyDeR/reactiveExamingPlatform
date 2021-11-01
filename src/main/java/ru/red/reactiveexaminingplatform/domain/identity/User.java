package ru.red.reactiveexaminingplatform.domain.identity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.userdetails.UserDetails;
import ru.red.reactiveexaminingplatform.domain.exam.Participation;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@Document
public class User implements UserDetails {
    @Id
    private UUID uuid;
    private String username;
    private String password;
    private final Set<Authority> authorities;
    private final Set<Participation> participationSet;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;

    {
        this.uuid = UUID.randomUUID();
        this.authorities = new HashSet<>();
        this.participationSet = new HashSet<>();

        // TODO: Change Behaviour
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
        this.enabled = true;
    }
}
