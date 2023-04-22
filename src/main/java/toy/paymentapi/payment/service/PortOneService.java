package toy.paymentapi.payment.service;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import toy.paymentapi.payment.dto.PortOneAccessDto;

import java.util.Map;

import static toy.paymentapi.payment.service.PortOneRestApi.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PortOneService {

    @Value("${imp_key}")
    private String key;

    @Value("${imp_secret}")
    private String secret;

    /**
    * 토큰 발급 요청 처리
     * return
     * - PortOneAccessDto
     *      -  access_token
    **/
    @Transactional(propagation = Propagation.MANDATORY)
    public PortOneAccessDto getToken(){
        RestTemplate restTemplate = new RestTemplate();

        //Http Entity create
        HttpEntity<PortOneAccessDto> request = new HttpEntity<>(new PortOneAccessDto(key, secret));

        //POST REQUEST to PortOne API -> Response : JSON
        ResponseEntity<String> response = restTemplate
                .exchange(creatApiUrl(AUTHENTICATE), HttpMethod.POST, request, String.class);

        if(!response.getStatusCode().isError()){
            Gson gson = new Gson();

            //JSON Parser -> convert String
            String responseBody = gson.fromJson(response.getBody(), Map.class).get("response").toString();

            //JSON Parser -> convert DTO
            return gson.fromJson(responseBody, PortOneAccessDto.class);
        }

        throw new RuntimeException("An access token was not issued from Port One.");
    }

    @Transactional
    public void getPaymentInfo(){

        PortOneAccessDto token = getToken();

        RestTemplate restTemplate = new RestTemplate();

    }


    private String creatApiUrl(PortOneRestApi resource){

        StringBuilder sb = new StringBuilder();

        sb.append(HOST.getUri());
        sb.append(resource.getUri());

        String getTokenUrl = sb.toString();

        return getTokenUrl;
    }
}
