package toy.paymentapi.payment.service;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;
import toy.paymentapi.payment.dto.PortOneAccessDto;

@SpringBootTest
@Slf4j
class PortOneServiceTest {

    @Autowired
    PortOneService portOneService;

    @DisplayName("토근 발급 테스트 - 정상 처리")
    @Test
    void getTokenTest(){
        //given
        RestTemplate restTemplate = new RestTemplate();

        //when
        PortOneAccessDto token = portOneService.getToken(restTemplate);

        //then
        String accessToken = token.getAccess_token();
        log.info(accessToken);
        Assertions.assertThat(accessToken).isNotEmpty();
    }
}