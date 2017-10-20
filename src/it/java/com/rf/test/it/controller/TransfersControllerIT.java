package com.rf.test.it.controller;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThan;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.ValidatableResponse;
import com.rf.test.it.resources.TransferResource;
import com.rf.test.utils.ServicesEndpoints;
import com.tsm.rf.RfApplication;
import com.tsm.rf.util.TransferTestBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RfApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({ "it" })
@FixMethodOrder(MethodSorters.JVM)
public class TransfersControllerIT {

    @LocalServerPort
    private int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void save_NullDestinationAccountGiven_ShouldReturnError() {
        // Set Up
        TransferResource resource = TransferResource.build().assertFields().destinationAccount(null);

        // Do Test
        given().body(resource).contentType(ContentType.JSON).when().post(ServicesEndpoints.TRANSFER_POST).then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("message[0]", is("The destination account is required."));
    }

    @Test
    public void save_EmptyDestinationAccountGiven_ShouldReturnError() {
        // Set Up
        TransferResource resource = TransferResource.build().assertFields().destinationAccount("");

        // Do Test
        given().body(resource).contentType(ContentType.JSON).when().post(ServicesEndpoints.TRANSFER_POST).then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("message[0]", is("The destination account size must be between 3 and 20."));
    }

    @Test
    public void save_LargeDestinationAccountGiven_ShouldReturnError() {
        // Set Up
        TransferResource resource = TransferResource.build().assertFields()
            .destinationAccount(TransferTestBuilder.LARGE_DESTINATION_ACCOUNT);

        // Do Test
        given().body(resource).contentType(ContentType.JSON).when().post(ServicesEndpoints.TRANSFER_POST).then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("message[0]", is("The destination account size must be between 3 and 20."));
    }

    @Test
    public void save_NullOriginAccountGiven_ShouldReturnError() {
        // Set Up
        TransferResource resource = TransferResource.build().assertFields().originAccount(null);

        // Do Test
        given().body(resource).contentType(ContentType.JSON).when().post(ServicesEndpoints.TRANSFER_POST).then()
            .statusCode(HttpStatus.BAD_REQUEST.value()).body("message[0]", is("The origin account is required."));
    }

    @Test
    public void save_EmptyOriginAccountGiven_ShouldReturnError() {
        // Set Up
        TransferResource resource = TransferResource.build().assertFields().originAccount("");

        // Do Test
        given().body(resource).contentType(ContentType.JSON).when().post(ServicesEndpoints.TRANSFER_POST).then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("message[0]", is("The origin account size must be between 3 and 20."));
    }

    @Test
    public void save_LargeOriginAccountGiven_ShouldReturnError() {
        // Set Up
        TransferResource resource = TransferResource.build().assertFields()
            .originAccount(TransferTestBuilder.LARGE_ORIGIN_ACCOUNT);

        // Do Test
        given().body(resource).contentType(ContentType.JSON).when().post(ServicesEndpoints.TRANSFER_POST).then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("message[0]", is("The origin account size must be between 3 and 20."));
    }

    @Test
    public void save_NullScheduleDateGiven_ShouldReturnError() {
        // Set Up
        TransferResource resource = TransferResource.build().assertFields().scheduleDate(null);

        // Do Test
        given().body(resource).contentType(ContentType.JSON).when().post(ServicesEndpoints.TRANSFER_POST).then()
            .statusCode(HttpStatus.BAD_REQUEST.value()).body("message[0]", is("The schedule date is required."));
    }

    @Test
    public void save_InvalidScheduleDateGiven_ShouldReturnError() {
        // Set Up
        TransferResource resource = TransferResource.build().assertFields().scheduleDate(TransferTestBuilder.INVALID_DATE);

        // Do Test
        given().body(resource).contentType(ContentType.JSON).when().post(ServicesEndpoints.TRANSFER_POST).then()
            .statusCode(HttpStatus.BAD_REQUEST.value()).body("message[0]", is("The schedule date is invalid."));
    }

    @Test
    public void save_PastScheduleDateGiven_ShouldReturnError() {
        // Set Up
        TransferResource resource = TransferResource.build().assertFields().scheduleDate(TransferTestBuilder.PAST_SCHEDULE_DATE);

        // Do Test
        given().body(resource).contentType(ContentType.JSON).when().post(ServicesEndpoints.TRANSFER_POST).then()
            .statusCode(HttpStatus.BAD_REQUEST.value()).body("message[0]", is("The schedule date is invalid."));
    }

    @Test
    public void save_NullTransferValueGiven_ShouldReturnError() {
        // Set Up
        TransferResource resource = TransferResource.build().assertFields().transferValue(null);

        // Do Test
        given().body(resource).contentType(ContentType.JSON).when().post(ServicesEndpoints.TRANSFER_POST).then()
            .statusCode(HttpStatus.BAD_REQUEST.value()).body("message[0]", is("The transfer value is required."));
    }

    @Test
    public void save_InvalidTransferValueGiven_ShouldReturnError() {
        // Set Up
        TransferResource resource = TransferResource.build().assertFields().transferValue(0d);

        // Do Test
        given().body(resource).contentType(ContentType.JSON).when().post(ServicesEndpoints.TRANSFER_POST).then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("message[0]", is("The transfer value must be greater than zero."));
    }

    @Test
    public void save_TodayScheduleDateGiven_ShouldCreateTransfer() {
        // Set Up
        TransferResource resource = TransferResource.build().scheduleDate(TransferTestBuilder.getTodayScheduleDateString()).assertFields();

        // Do Test
        ValidatableResponse response = given().contentType(ContentType.JSON).body(resource).when()
            .post(ServicesEndpoints.TRANSFER_POST).then().statusCode(HttpStatus.CREATED.value());

        // Assertions
        response.body("id", notNullValue(), "destinationAccount", is(resource.getDestinationAccount()), "originAccount",
            is(resource.getOriginAccount()), "scheduleDate", is(resource.getScheduleDate()), "tax", notNullValue(),

            "createdDate", notNullValue(), "transferValue", is(resource.getTransferValue().floatValue()));
    }

    @Test
    public void save_ValidResourceGiven_ShouldCreateTransfer() {
        // Set Up
        TransferResource resource = TransferResource.build().assertFields();

        // Do Test
        ValidatableResponse response = given().contentType(ContentType.JSON).body(resource).when()
            .post(ServicesEndpoints.TRANSFER_POST).then().statusCode(HttpStatus.CREATED.value());

        // Assertions
        response.body("id", notNullValue(), "destinationAccount", is(resource.getDestinationAccount()), "originAccount",
            is(resource.getOriginAccount()), "scheduleDate", is(resource.getScheduleDate()), "tax", notNullValue(),

            "createdDate", notNullValue(), "transferValue", is(resource.getTransferValue().floatValue()));
    }

    @Test
    public void find_ValidResourceGiven_ShouldReturnContent() {
        // Set Up
        TransferResource.build().create();

        // Do Test
        given().contentType(ContentType.JSON).when().get(ServicesEndpoints.TRANSFER_FIND + "?images=false").then()
            .statusCode(HttpStatus.OK.value()).body("size()", is(greaterThan(0)));
    }

}
