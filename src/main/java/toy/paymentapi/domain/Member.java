package toy.paymentapi.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "member_tb")
@NoArgsConstructor
@AllArgsConstructor
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

    public Member(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public void savePoint(Point point){
        points.add(point);
        point.ownerMember(this);
    }

}
