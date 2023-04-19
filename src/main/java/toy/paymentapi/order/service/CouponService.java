package toy.paymentapi.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.paymentapi.order.domain.CouponIssue;
import toy.paymentapi.order.domain.CouponIssueReason;
import toy.paymentapi.order.domain.CouponIssueStatus;
import toy.paymentapi.order.repository.CouponIssueRepository;

import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class CouponService {

    CouponIssueRepository couponIssueRepository;

    /**
    * 쿠폰 상태 변경 -> '사용 완료'
     * 1. 정상적으로 사용상태 변경시 true 반환
     * 2. 발급된 쿠폰 이미 사용한 상태일 경우 false 반환
    **/
    @Transactional
    public boolean changeToUsedCoupon(Long memberId, Long couponIssueId){
        CouponIssue findCouponIssued = findCouponById(couponIssueId);

        if(findCouponIssued.getUseStatus() == CouponIssueStatus.OFF){
            return false;
        }

        findCouponIssued.updateCouponIssue(CouponIssueStatus.OFF, CouponIssueReason.BUY_ORDER);

        return true;
    }

    @Transactional(readOnly = true)
    public CouponIssue findCouponById(Long couponIssueId){

        return couponIssueRepository.findById(couponIssueId)
                .orElseThrow(NoSuchElementException::new);

    }
}
