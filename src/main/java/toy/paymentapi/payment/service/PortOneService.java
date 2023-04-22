package toy.paymentapi.payment.service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpStatusCodeException;
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

    @Transactional
    public void getPaymentInfo(){
        RestTemplate restTemplate = new RestTemplate();

        //1. 토큰 발급
        PortOneAccessDto token = getToken(restTemplate);

        //2. GET 요청


        //3. JSON Parser -> convert DTO


        //4. 예외 처리

    }

    /**
    * 토큰 발급 요청
     * return
     * - PortOneAccessDto
     *   - String access_token
    **/
    @Transactional(propagation = Propagation.MANDATORY)
    public PortOneAccessDto getToken(RestTemplate restTemplate){

        //Http Entity create
        HttpEntity<PortOneAccessDto> request = new HttpEntity<>(new PortOneAccessDto(key, secret));

        ResponseEntity<String> response = null;

        try {
            //POST REQUEST to PortOne API -> Response : JSON
             response = restTemplate
                    .exchange(creatApiUrl(AUTHENTICATE), HttpMethod.POST, request, String.class);

        } catch (HttpStatusCodeException ex){
            throw new RuntimeException("Error Response "+ ex.getMessage());
        }


        if(!response.getStatusCode().isError()){
            Gson gson = new Gson();
            String responseBody ="";

            try {
                //JSON Parser -> convert String
                 responseBody = gson.fromJson(response.getBody(), Map.class).get("response").toString();

            } catch (JsonSyntaxException ex){
                log.error(ex.getMessage());
                throw new IllegalStateException("JSON Document has no Body");
            }

            //JSON Parser -> convert DTO
            return gson.fromJson(responseBody, PortOneAccessDto.class);
        }

        throw new RuntimeException("An access token was not issued from Port One.");
    }


    private String creatApiUrl(PortOneRestApi resource){

        StringBuilder sb = new StringBuilder();

        sb.append(HOST.getUri());
        sb.append(resource.getUri());

        String getTokenUrl = sb.toString();

        return getTokenUrl;
    }
}
