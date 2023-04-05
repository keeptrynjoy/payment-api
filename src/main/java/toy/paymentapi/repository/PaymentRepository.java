package toy.paymentapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toy.paymentapi.domain.Item;
import toy.paymentapi.domain.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
