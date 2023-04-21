package io.gihub.apilogic.multiclient;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.gihub.apilogic.multiclient.flow.dispatcher.DataDispatcherFactory;
import io.gihub.apilogic.multiclient.models.AuthorizableEventBodyImpl;
import io.gihub.apilogic.multiclient.models.HotelSearchParams;
import io.gihub.apilogic.multiclient.models.MulticlientEventImpl;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

public class MultiClientPlaygroundTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @SneakyThrows
    @Test
    public void hotelAvailabilityTest() {
        var dataDispatcherFactory = new DataDispatcherFactory<>();
        var multiclientEvent = new MulticlientEventImpl<>();
        var authorizableEventBody = new AuthorizableEventBodyImpl<>();
        authorizableEventBody.setAuthorization(getCredentials());
        authorizableEventBody.setData(hotelSearchParams());
        multiclientEvent.setEvents(List.of("vendor_1/hotelAvailability"));
        multiclientEvent.setBody(authorizableEventBody);
        var results = dataDispatcherFactory.dispatch(multiclientEvent);
        System.out.println(objectMapper.writeValueAsString(results));
        Assertions.assertNotNull(results);
    }

    @SneakyThrows
    @Test
    public void cities() {
        var dataDispatcherFactory = new DataDispatcherFactory<>();
        var multiclientEvent = new MulticlientEventImpl<>();
        var authorizableEventBody = new AuthorizableEventBodyImpl<>();
        authorizableEventBody.setAuthorization(getCredentials());
        authorizableEventBody.setData(hotelSearchParams());
        multiclientEvent.setEvents(List.of("vendor_1/cities"));
        multiclientEvent.setBody(authorizableEventBody);
        var results = dataDispatcherFactory.dispatch(multiclientEvent);
        Assertions.assertNotNull(results);
    }

    @SneakyThrows
    private HotelSearchParams hotelSearchParams() {
        var is = getClass().getClassLoader().getResourceAsStream("hotelSearchParam.json");
        return objectMapper.readValue(is, HotelSearchParams.class);
    }

    @SneakyThrows
    private Map<String, Object> getCredentials() {
        var is = getClass().getClassLoader().getResourceAsStream("credentials.json");
        return objectMapper.readValue(is, new TypeReference<>() {});
    }

}
