package toy.paymentapi.repository;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toy.paymentapi.domain.Member;
import toy.paymentapi.domain.Point;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class PointQueryRepositoryTest {

    @Autowired
    private PointQueryRepository pointQueryRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PointRepository pointRepository;

    @Test
    @Transactional
    void findPoint() {
        //given
        Member member = new Member("김성민","01040289186","sungmin4218@gmail.com");
        Member saveMember = memberRepository.save(member);
        Point pointByOrder = Point.createPointByOrder(100, 10, 1L, saveMember);
        Point save1 = pointRepository.save(pointByOrder);
        Point pointByOrder2 = Point.createPointByOrder(90, 10, 2L, saveMember);
        Point save2 = pointRepository.save(pointByOrder2);

        //when
        Integer pointByMember = pointQueryRepository.findPointByMember(saveMember.getId());
        Point point = pointRepository.findById(save2.getId()).get();

        //then
        log.info(String.valueOf(save1.getBeforePoint()));
        log.info(String.valueOf(save2.getBeforePoint()));
        log.info(String.valueOf(point.getWriteDate()));
        Assertions.assertThat(pointByMember).isEqualTo(90);
    }

}