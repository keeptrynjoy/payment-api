package toy.paymentapi.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "member_tb")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String name;
    private String phone;
    private String email;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<CouponIssue> couponIssues = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Point> points = new ArrayList<>();

    @Builder
    public Member(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public void savePoint(Point point){
        points.add(point);
        point.ownerMember(this);
    }


    //== 생성 메서드 ==//
    public static Member createMember(String name,String phone, String email){
        return Member.builder()
                .name(name)
                .phone(phone)
                .email(email)
                .build();
    }
}
