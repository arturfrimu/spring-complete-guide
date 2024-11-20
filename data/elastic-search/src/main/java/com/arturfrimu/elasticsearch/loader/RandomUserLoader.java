package com.arturfrimu.elasticsearch.loader;

import com.arturfrimu.elasticsearch.entity.User;
import com.arturfrimu.elasticsearch.entity.dataclass.Address;
import com.arturfrimu.elasticsearch.entity.dataclass.Company;
import com.arturfrimu.elasticsearch.entity.dataclass.Geo;
import com.arturfrimu.elasticsearch.repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class RandomUserLoader implements CommandLineRunner {

    @NonFinal
    @Value("${script.populate-elastic-search}")
    private Boolean populateElasticSearch;

    private static final String RANDOM_USER_API_URL = "https://randomuser.me/api/";

    UserRepository userRepository;
    RestTemplate restTemplate = new RestTemplate();

    public void fetchAndStoreUsers() {
        ResponseEntity<ApiResponse> response = restTemplate.exchange(RequestEntity.get(RANDOM_USER_API_URL).build(), user);
        User randomUser = mapUser(Objects.requireNonNull(response.getBody()));
        userRepository.save(Objects.requireNonNull(randomUser));
        log.info("Saved {} users", userRepository.count());
    }

    public User mapUser(ApiResponse apiResponse) {
        Result result = apiResponse.getResults().get(0);

        User user = new User();
        user.setId(null);
        user.setName(result.getName().getFirst() + " " + result.getName().getLast());
        user.setUsername(result.getLogin().getUsername());
        user.setEmail(result.getEmail());

        Address address = new Address();
        address.setStreet(result.getLocation().getStreet().getName());
        address.setSuite(String.valueOf(result.getLocation().getStreet().getNumber()));
        address.setCity(result.getLocation().getCity());
        address.setZipcode(result.getLocation().getPostcode());
        Geo geo = new Geo();
        geo.setLat(result.getLocation().getCoordinates().getLatitude());
        geo.setLng(result.getLocation().getCoordinates().getLongitude());
        address.setGeo(geo);
        user.setAddress(address);

        user.setPhone(result.getPhone());
        user.setWebsite("example.com");

        // Map Company
        Company company = new Company();
        company.setName("Default Company");
        company.setCatchPhrase("Innovative Solutions");
        company.setBs("Business Services");
        user.setCompany(company);

        return user;
    }

    @Override
    public void run(String... args) {
        if (populateElasticSearch) {
            userRepository.deleteAll();

            fetchAndStoreUsers();
        }
    }

    //@formatter:off
    private static final ParameterizedTypeReference<ApiResponse> user = new ParameterizedTypeReference<>() {};

    @Data
    public static class ApiResponse {
        private List<Result> results;
    }

    @Data
    public static class Result {
        private String gender;
        private Name name;
        private Location location;
        private String email;
        private Login login;
        private Dob dob;
        private String phone;
        private String cell;
        private Id id;
        private Picture picture;
        private String nat;
    }

    @Data
    public static class Name {
        private String title;
        private String first;
        private String last;
    }

    @Data
    public static class Location {
        private Street street;
        private String city;
        private String state;
        private String country;
        private String postcode;
        private Coordinates coordinates;
    }

    @Data
    public static class Street {
        private int number;
        private String name;
    }

    @Data
    public static class Coordinates {
        private String latitude;
        private String longitude;
    }

    @Data
    public static class Login {
        private String uuid;
        private String username;
    }

    @Data
    public static class Dob {
        private String date;
        private int age;
    }

    @Data
    public static class Id {
        private String name;
        private String value;
    }

    @Data
    public static class Picture {
        private String large;
        private String medium;
        private String thumbnail;
    }


}
