package toy.paymentapi.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toy.paymentapi.order.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
