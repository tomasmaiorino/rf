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
        TransferResource resource = TransferResource.TransferSupport.Support().assertFields().destinationAccount(null).build();

        // Do Test
        given().body(resource).contentType(ContentType.JSON).when().post(ServicesEndpoints.TRANSFER_POST).then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("message[0]", is("The destination account is required."));
    }

    @Test
    public void save_EmptyDestinationAccountGiven_ShouldReturnError() {
        // Set Up
        TransferResource resource = TransferResource.TransferSupport.Support().assertFields().destinationAccount("").build();

        // Do Test
        given().body(resource).contentType(ContentType.JSON).when().post(ServicesEndpoints.TRANSFER_POST).then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("message[0]", is("The destination account size must be between 3 and 20."));
    }

    @Test
    public void save_LargeDestinationAccountGiven_ShouldReturnError() {
        // Set Up
        TransferResource resource = TransferResource.TransferSupport.Support().assertFields()
            .destinationAccount(TransferTestBuilder.LARGE_DESTINATION_ACCOUNT).build();

        // Do Test
        given().body(resource).contentType(ContentType.JSON).when().post(ServicesEndpoints.TRANSFER_POST).then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("message[0]", is("The destination account size must be between 3 and 20."));
    }

    @Test
    public void save_NullOriginAccountGiven_ShouldReturnError() {
        // Set Up
        TransferResource resource = TransferResource.TransferSupport.Support().assertFields().originAccount(null).build();

        // Do Test
        given().body(resource).contentType(ContentType.JSON).when().post(ServicesEndpoints.TRANSFER_POST).then()
            .statusCode(HttpStatus.BAD_REQUEST.value()).body("message[0]", is("The origin account is required."));
    }

    @Test
    public void save_EmptyOriginAccountGiven_ShouldReturnError() {
        // Set Up
        TransferResource resource = TransferResource.TransferSupport.Support().assertFields().originAccount("").build();

        // Do Test
        given().body(resource).contentType(ContentType.JSON).when().post(ServicesEndpoints.TRANSFER_POST).then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("message[0]", is("The origin account size must be between 3 and 20."));
    }

    @Test
    public void save_LargeOriginAccountGiven_ShouldReturnError() {
        // Set Up
        TransferResource resource = TransferResource.TransferSupport.Support().assertFields()
            .originAccount(TransferTestBuilder.LARGE_ORIGIN_ACCOUNT).build();

        // Do Test
        given().body(resource).contentType(ContentType.JSON).when().post(ServicesEndpoints.TRANSFER_POST).then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("message[0]", is("The origin account size must be between 3 and 20."));
    }

    @Test
    public void save_NullScheduleDateGiven_ShouldReturnError() {
        // Set Up
        TransferResource resource = TransferResource.TransferSupport.Support().assertFields().scheduleDate(null).build();

        // Do Test
        given().body(resource).contentType(ContentType.JSON).when().post(ServicesEndpoints.TRANSFER_POST).then()
            .statusCode(HttpStatus.BAD_REQUEST.value()).body("message[0]", is("The schedule date is required."));
    }

    @Test
    public void save_InvalidScheduleDateGiven_ShouldReturnError() {
        // Set Up
        TransferResource resource = TransferResource.TransferSupport.Support().assertFields().scheduleDate(TransferTestBuilder.INVALID_DATE)
            .build();

        // Do Test
        given().body(resource).contentType(ContentType.JSON).when().post(ServicesEndpoints.TRANSFER_POST).then()
            .statusCode(HttpStatus.BAD_REQUEST.value()).body("message[0]", is("The schedule date is invalid."));
    }

    @Test
    public void save_PastScheduleDateGiven_ShouldReturnError() {
        // Set Up
        TransferResource resource = TransferResource.TransferSupport.Support().assertFields()
            .scheduleDate(TransferTestBuilder.PAST_SCHEDULE_DATE).build();

        // Do Test
        given().body(resource).contentType(ContentType.JSON).when().post(ServicesEndpoints.TRANSFER_POST).then()
            .statusCode(HttpStatus.BAD_REQUEST.value()).body("message[0]", is("The schedule date is invalid."));
    }

    @Test
    public void save_NullTransferValueGiven_ShouldReturnError() {
        // Set Up
        TransferResource resource = TransferResource.TransferSupport.Support().assertFields().transferValue(null).build();

        // Do Test
        given().body(resource).contentType(ContentType.JSON).when().post(ServicesEndpoints.TRANSFER_POST).then()
            .statusCode(HttpStatus.BAD_REQUEST.value()).body("message[0]", is("The transfer value is required."));
    }

    @Test
    public void save_InvalidTransferValueGiven_ShouldReturnError() {
        // Set Up
        TransferResource resource = TransferResource.TransferSupport.Support().assertFields().transferValue(0d).build();

        // Do Test
        given().body(resource).contentType(ContentType.JSON).when().post(ServicesEndpoints.TRANSFER_POST).then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("message[0]", is("The transfer value must be greater than zero."));
    }

    @Test
    public void save_InapplicableTaxGiven_ShouldReturnError() {
        // Set Up
        TransferResource resource = TransferResource.TransferSupport.Support()
            .scheduleDate(TransferTestBuilder.getScheduleDateString(41))
            .transferValue(90.00)
            .assertFields().build();

        // Do Test
        given().body(resource).contentType(ContentType.JSON).when().post(ServicesEndpoints.TRANSFER_POST).then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("message", is("The tax can not be applied."));
    }

    @Test
    public void save_TodayScheduleDateGiven_ShouldCreateTransfer() {
        // Set Up
        TransferResource resource = TransferResource.TransferSupport.Support()
            .scheduleDate(TransferTestBuilder.getTodayScheduleDateString()).assertFields()
            .build();

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
        TransferResource resource = TransferResource.TransferSupport.Support().assertFields().build();

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
        TransferResource.TransferSupport.Support().create();

        // Do Test
        given().contentType(ContentType.JSON).when().get(ServicesEndpoints.TRANSFER_FIND + "?images=false").then()
            .statusCode(HttpStatus.OK.value()).body("size()", is(greaterThan(0)));
    }

}
