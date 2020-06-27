package com.zhukovsd.alfabattle.task1.atms;

import com.zhukovsd.alfabattle.task1.alfabankapi.atm.model.AtmModel;
import com.zhukovsd.alfabattle.task1.alfabankapi.atm.model.AtmsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class AtmController {

    @Autowired
    RestTemplate restTemplate;

    @GetMapping(value = "/atms/{deviceId}")
    public Model getAtm(@PathVariable("deviceId") String deviceId) {
        AtmsModel model = queryAlfaBankAPI();

        AtmModel alfaBankAtmModel = this.findAtmById(model, deviceId);

        return new Model(
                alfaBankAtmModel.getDeviceId(),
                alfaBankAtmModel.getCoordinates().getLatitude(),
                alfaBankAtmModel.getCoordinates().getLongitude(),
                alfaBankAtmModel.getAddress().getCity(),
                alfaBankAtmModel.getAddress().getLocation(),
                alfaBankAtmModel.getServices().getPayments().equals("Y")
        );
    }

    private AtmsModel queryAlfaBankAPI() {
        String url = "https://apiws.alfabank.ru/alfabank/alfadevportal/atm-service/atms";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("x-ibm-client-id", "bcdbdff6-113b-42aa-80e2-72fc6a3c7b41");
        headers.set("content-type", "application/json");
        headers.set("accept", "application/json");

        Map<String, String> params = new HashMap<String, String>();

        HttpEntity<AtmsModel> entity = new HttpEntity<>(headers);
        HttpEntity<AtmsModel> response = restTemplate.exchange(url, HttpMethod.GET, entity, AtmsModel.class, params);

        return response.getBody();
    }

    private AtmModel findAtmById(AtmsModel model, String deviceId) {
        List<AtmModel> atms = model.getData().getAtms();

        Optional<AtmModel> atm = atms.stream().filter(atmModel -> atmModel.getDeviceId().equals(deviceId)).findFirst();

        return atm.orElseThrow(AtmNotFoundException::new);
    }

    @ExceptionHandler(value = AtmNotFoundException.class)
    public ResponseEntity<ErrorJsonResponse> handleException(Exception e) {
        return new ResponseEntity<>(new ErrorJsonResponse("atm not found"), HttpStatus.NOT_FOUND);
    }

}
