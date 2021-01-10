package com.uvideo.miaosha.vo;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private long id;
    private String uniqueId;
    private String nickname;
    private String password;
    private Date createTime;
    private Date lastModifiedTime;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", uniqueId='" + uniqueId + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", createTime=" + createTime +
                ", lastModifiedTime=" + lastModifiedTime +
                '}';
    }
}
