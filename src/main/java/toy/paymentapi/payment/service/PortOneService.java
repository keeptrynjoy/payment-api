package toy.paymentapi.payment.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import toy.paymentapi.payment.dto.PortOneAccessDto;


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
    public void getPaymentInfo(String impUid){
        RestTemplate restTemplate = new RestTemplate();

        //1. 토큰 발급
        PortOneAccessDto token = getToken(restTemplate);

        //2. GET 요청
        ResponseEntity<String> response = restTemplate.getForEntity(creatApiUrl(PAYMENTS_IMPUID) +"/"+ impUid, String.class);

        //3. JSON Parser -> convert DTO
//        parsingResponseBody(response.getBody(),)

        //4. 예외 처리

    }

    /**
    * 토큰 발급 요청
     * return
     * - PortOneAccessDto
     *   - String access_token
    **/
//    @Transactional(propagation = Propagation.MANDATORY)
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

        return parsingResponseBody(response,PortOneAccessDto.class);
    }

    private <T> T parsingResponseBody(ResponseEntity<String> response, Class<T> returnClass){

        if(!response.getStatusCode().isError()){
            ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);

            JsonNode responseBody = null;
            try {
                //JSON Parser -> convert String
                JsonNode root = mapper.readTree(response.getBody());
                responseBody = root.path("response");
            } catch (JsonProcessingException ex){
                log.error(ex.getMessage());
                throw new IllegalStateException("JSON Document has no Body");
            }

            //JSON Parser -> convert DTO
            return mapper.convertValue(responseBody, returnClass);
        }

        throw new ResponseStatusException(response.getStatusCode()
                ,"received the error code from Port One");
    }


    private String creatApiUrl(PortOneRestApi resource){

        StringBuilder sb = new StringBuilder();

        sb.append(HOST.getUri());
        sb.append(resource.getUri());

        String getTokenUrl = sb.toString();

        return getTokenUrl;
    }


}
