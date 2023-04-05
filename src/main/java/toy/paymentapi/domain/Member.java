package toy.paymentapi.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "member_tb")
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;
    private String name;
    private String phone;
    private String email;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<CouponIssue> couponIssue = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<PointHistory> pointHistories = new ArrayList<>();
}
