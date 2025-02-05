package com.webber.jogging.strava;

import com.webber.jogging.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "strava_authentication")
public class StravaAuthentication {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "expiration_date", nullable = false)
    private LocalDateTime expirationDate;

    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "refresh_token")
    private String refreshToken;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public StravaAuthentication(String accessToken, String refreshToken, User user, LocalDateTime expirationDate) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.user = user;
        this.expirationDate = expirationDate;
    }

    public StravaAuthentication() {
        super();
    }

    @Override
    public String toString() {
        return "StravaAuthentication{" +
                "id=" + id +
                ", expirationDate=" + expirationDate +
                ", accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", user=" + user +
                '}';
    }
}
