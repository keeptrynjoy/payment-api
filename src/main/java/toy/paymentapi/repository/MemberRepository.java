package toy.paymentapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toy.paymentapi.domain.Item;
import toy.paymentapi.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
