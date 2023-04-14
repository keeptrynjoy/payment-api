package toy.paymentapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.paymentapi.domain.CouponIssue;
import toy.paymentapi.domain.CouponIssueReason;
import toy.paymentapi.domain.Enum.CouponIssueStatus;
import toy.paymentapi.repository.CouponIssueRepository;
import toy.paymentapi.repository.CouponRepository;
import toy.paymentapi.repository.QueryRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class CouponService {

    private final QueryRepository queryRepository;

    /**
    * 쿠폰 상태 변경 -> '사용 완료'
     * 1. 정상적으로 사용상태 변경시 true 반환
     * 2. 발급된 쿠폰 이미 사용한 상태일 경우 false 반환
    **/
    public boolean changeToUsedCoupon(Long memberId, Long couponIssueId){
        CouponIssue findCouponIssued = queryRepository.findCouponIssuedByMember(memberId, couponIssueId);

        if(findCouponIssued.getUseStatus() == CouponIssueStatus.OFF){
            return false;
        }

        findCouponIssued.updateCouponIssue(CouponIssueStatus.OFF, CouponIssueReason.BUY_ORDER);

        return true;
    }
}
