package toy.paymentapi.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toy.paymentapi.repository.MemberRepository;
import toy.paymentapi.repository.PointRepository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class PointTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PointRepository pointRepository;


    @Test
    @Transactional
    void createPointByOrder() {
        //given
        Member member = new Member("김성민","01040289186","sungmin4218@gmail.com");
        Member saveMember = memberRepository.save(member);

        int afterPoint = 100;
        int usePoint = 10;

        //when
        Point pointByOrder = Point.createPointByOrder(afterPoint, usePoint, 1L, saveMember);
        Point savePoint = pointRepository.save(pointByOrder);

        //then
        assertThat(savePoint).isEqualTo(pointByOrder);
    }
}