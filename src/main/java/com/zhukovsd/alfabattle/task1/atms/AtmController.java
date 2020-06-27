package com.zhukovsd.alfabattle.task1.atms;

import com.zhukovsd.alfabattle.task1.alfabankapi.atm.model.AtmModel;
import com.zhukovsd.alfabattle.task1.alfabankapi.atm.model.AtmsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
public class AtmController {

    public static double distance(double lat1, double lat2, double lon1,
                                  double lon2, double el1, double el2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }

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

//    Запрос: GET http://IP:8080/atms/nearest?latitude=string&longitude=string&payments=boolean

    @GetMapping(value = "/atms/nearest")
    public Model getNearest(
            @RequestParam Double latitude,
            @RequestParam Double longitude,
            @RequestParam(defaultValue = "false") boolean payments
    ) {
        Map<String, Double> distancesByDeviceId = new HashMap<>();

        AtmsModel model = this.queryAlfaBankAPI();
        List<AtmModel> atms = model.getData().getAtms();

        for (AtmModel atm : atms) {
            if (atm.getCoordinates() == null) {
                continue;
            }

            if (atm.getCoordinates().getLatitude() == null) {
                continue;
            }

            if (atm.getCoordinates().getLatitude().equals("0")) {
                continue;
            }

            if (payments && (atm.getServices().getPayments().equals("N"))) {
                continue;
            }

            Double distance =
                Math.sqrt(
                    Math.pow((Double.parseDouble(atm.getCoordinates().getLatitude()) - latitude), 2)
                    + Math.pow((Double.parseDouble(atm.getCoordinates().getLongitude()) - longitude), 2)
                );

//            Double distance = this.distance(
//                    Double.parseDouble(atm.getCoordinates().getLatitude()), latitude,
//                    Double.parseDouble(atm.getCoordinates().getLongitude()), longitude, 0, 0
//            );

            distancesByDeviceId.put(atm.getDeviceId(), distance);
        }

        String deviceId = Collections.max(distancesByDeviceId.entrySet(), Comparator.comparingDouble(Map.Entry::getValue)).getKey();
        AtmModel atm = this.findAtmById(model, deviceId);

        return new Model(
                atm.getDeviceId(),
                atm.getCoordinates().getLatitude(),
                atm.getCoordinates().getLongitude(),
                atm.getAddress().getCity(),
                atm.getAddress().getLocation(),
                atm.getServices().getPayments().equals("Y")
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
