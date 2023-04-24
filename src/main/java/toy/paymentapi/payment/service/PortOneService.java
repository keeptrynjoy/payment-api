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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import toy.paymentapi.payment.dto.PortOneAccessDto;
import toy.paymentapi.payment.dto.PortOnePaymentDto;


import java.util.Objects;

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

    @Transactional(readOnly = true)
    public PortOnePaymentDto getPaymentInfo(String impUid){
        RestTemplate restTemplate = new RestTemplate();

        //1. get access token
        PortOneAccessDto token = getToken(restTemplate);

        ResponseEntity<String> response = null;

        try {
            //2. make request URL
            StringBuilder sb = new StringBuilder();
            sb.append("/");
            sb.append(impUid);
            sb.append("?_");
            sb.append(token);

            //3. GET payments
            response = restTemplate
                    .getForEntity(creatApiUrl(PAYMENTS_IMPUID,sb), String.class);

        } catch (HttpClientErrorException ex){
            throw new RuntimeException("Error Response "+ ex.getMessage());
        }

        //4. JSON Parser -> convert DTO
        return parsingResponseBody(response, PortOnePaymentDto.class);
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
            StringBuilder sb = new StringBuilder();

            //POST REQUEST to PortOne API -> Response : JSON
             response = restTemplate
                    .exchange(creatApiUrl(AUTHENTICATE,sb), HttpMethod.POST, request, String.class);

        } catch (HttpStatusCodeException ex){
            throw new RuntimeException("Error Response "+ ex.getMessage());
        }

        return parsingResponseBody(response,PortOneAccessDto.class);
    }

    private <T> T parsingResponseBody(ResponseEntity<String> response, Class<T> returnClass){

        //1. response status check
        HttpStatus responseStatusCode = response.getStatusCode();

        if(responseStatusCode.isError()){
            throw new ResponseStatusException(response.getStatusCode()
                    ,"received the error code from Port One");
        }

        //2. null check
        String responseBody = Objects.requireNonNull(response.getBody(),"JSON Document has no body");

        ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);

        JsonNode resultJsonData = null;

        try {
            //3. JSON Parser -> convert String
            JsonNode root = mapper.readTree(responseBody);
            resultJsonData = root.path("response");

        } catch (JsonProcessingException ex){
            log.error(ex.getMessage());
            throw new IllegalStateException("The target could not be found in the JSON document.");
        }

        //4. JSON Parser -> convert DTO
        return mapper.convertValue(resultJsonData, returnClass);
    }


    private String creatApiUrl(PortOneRestApi resource, StringBuilder sb){

        sb.append(HOST.getUri());
        sb.append(resource.getUri());

        return sb.toString();
    }
}
