package com.demo.step_definitions;

import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class step_definitions {
    public Response response;
    public String base_url = "https://api-ssl.bitly.com";
    public String access_token = "59d5fca6bdb88c2f9e081c8dcbe29abf26f3f158";


    @Given("I execute GET url to Retrieve a Group details")
    public void iSetEndPointUrlToRetrieveAGroupDetails() {
        //RestAssured.baseURI = BASE_URL;
        //String base_url = "https://api-ssl.bitly.com";

        RequestSpecification httprequest = RestAssured.given();
        httprequest.header("Content-Type", "application/json");
        httprequest.header("Authorization", "Bearer " + access_token);
        response = httprequest.request(Method.GET, base_url + "/v4/groups/Bm4166jJLxX");

        //int scode = response.getStatusCode();
        //Assert.assertEquals(200, response.getStatusCode());
        //int scode = httprequest.request(Method.GET,"https://api-ssl.bitly.com/v4/groups/Bm4166jJLxX").getStatusCode();
        //System.out.println("Status Code :" + scode);
        //System.out.println(response.getBody().asString());
        //resp = response.response();
    }

    @Then("^the response status code should be (\\d+)$")
    public void theResponseStatusCodeShouldBe(int status) {
        int statusCode = response.getStatusCode();
        Assert.assertEquals(status, response.getStatusCode());
        System.out.println("Response Status Code: " + statusCode);
        System.out.println("Response Body :\n" + response.getBody().asString());
    }

    @And("^I execute and verify Retrieved Group details with below values:$")
    public void iExecuteAndVerifyRetrievedGroupDetailsWithBelowValues(DataTable dataTable) {
        JSONObject jsonObject = new JSONObject(response.getBody().asString());
        System.out.println(jsonObject.get("guid"));
        System.out.println(jsonObject.get("name"));
        System.out.println(jsonObject.get("role"));

        //List<List<String>> groupDetailsDatatable = dataTable.as;
        //List<List<String>> groupDetailsDatatable = dataTable.asLists();
        Map<String, String> groupDetailsDatatable = dataTable.asMap(String.class, String.class);

        //int rowcount = groupDetailsDatatable.size();
        String key, value;
        AtomicReference<String> guid = new AtomicReference<>("");
        AtomicReference<String> name = new AtomicReference<>("");
        AtomicReference<String> role = new AtomicReference<>("");

        groupDetailsDatatable.entrySet().stream().forEach(keyValue -> {
            switch (keyValue.getKey()) {
                case "guid":
                    //guid = String.valueOf(jsonObject.get("guid"));
                    guid.set(keyValue.getValue());
                    Assert.assertEquals(keyValue.getValue(), String.valueOf(jsonObject.get("guid")));
                    break;
                case "name":
                    name.set(keyValue.getValue());
                    Assert.assertEquals(keyValue.getValue(), jsonObject.get("name"));
                    break;
                case "role":
                    role.set(keyValue.getValue());
                    Assert.assertEquals(keyValue.getValue(), jsonObject.get("role"));
                    break;
                default:
                    Assert.fail();
                    break;
            }
        });
    }

    @Given("^I execute GET method with invalid group_guid$")
    public void iSetEndPointUrlToInvalidGroup_guid() {
        RequestSpecification httprequest = RestAssured.given();
        httprequest.header("Content-Type", "application/json");
        httprequest.header("Authorization", "Bearer " + access_token);
        response = httprequest.request(Method.GET, base_url + "/v4/groups/dummy$1234");
    }

    @And("^I assert the response of invalid group_guid with below values:$")
    public void iAssertTheResponseOfInvalidGroup_guidWithBelowValues(DataTable dataTable) {
        JSONObject jsonObject = new JSONObject(response.getBody().asString());
        //System.out.println(jsonObject.get("message"));
        //System.out.println(jsonObject.get("resource"));
        Assert.assertEquals(jsonObject.get("message"), "FORBIDDEN");
        Assert.assertEquals(jsonObject.get("resource"), "groups");
    }

    @Given("^I set end point url to Retrieve Bitlinks for Group$")
    public void iSetEndPointUrlToRetrieveSortedBitlinksForGroup() {
        RequestSpecification httprequest = RestAssured.given();
        httprequest.header("Content-Type", "application/json");
        httprequest.header("Authorization", "Bearer " + access_token);
        response = httprequest.request(Method.GET, base_url + "/v4/groups/Bm4166jJLxX/bitlinks");
    }

    @And("^I verify Retrieved Sorted Bitlinks response with below values:$")
    public void iVerifyRetrievedSortedBitlinksResponseWithBelowValues(DataTable dataTable) {
        JSONObject jsonObject = new JSONObject(response.getBody().asString());
        //JSONObject jsonObject1 = jsonObject.getJSONObject("links");
        JSONArray links = jsonObject.getJSONArray("links");
        JSONObject jsonObject1 = links.getJSONObject(0);
        System.out.println("First Array object:\n" + jsonObject1);

        //System.out.println(jsonObject1.get("created_by"));
        Assert.assertEquals(jsonObject1.get("created_by"), "bhagappa");
        Assert.assertEquals(jsonObject1.get("client_id"), "ece654beaf35f9c29f610ffd4fb128702b4bad15");
        //Assert.assertEquals(jsonObject1.get("long_url"), "ece654beaf35f9c29f610ffd4fb128702b4bad15");
    }

    @Given("^I execute GET method to Retrieve Sorted Bitlinks for Group$")
    public void iExecuteGETMethodToRetrieveSortedBitlinksForGroup() {
        RequestSpecification httprequest = RestAssured.given();
        httprequest.header("Content-Type", "application/json");
        httprequest.header("Authorization", "Bearer " + access_token);
        //httprequest.setDateTimeFormatter(
        //        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        response = httprequest.request(Method.GET, base_url + "/v4/groups/Bm4166jJLxX/bitlinks/clicks?unit=month&units=5");
    }

    @And("^I assert the response with values on Retrieved Sorted Bitlinks$")
    public void iAssertTheResponseWithValuesOnRetrievedSortedBitlinks() {
        JSONObject jsonObject = new JSONObject(response.getBody().asString());
        System.out.println(jsonObject.get("links"));
        //JSONArray links = jsonObject.getJSONArray("links");
        //JSONObject jsonObject1 = links.getJSONObject(0);
    }

    /*@Given("^I set GET end point url to invalid group_guid to Retrieve Sorted Bitlinks$")
    public void iSetGETEndPointUrlToInvalidGroup_guidToRetrieveSortedBitlinks() {
        RequestSpecification httprequest = RestAssured.given();
        httprequest.header("Content-Type", "application/json");
        httprequest.header("Authorization", "Bearer " + access_token);
        response = httprequest.request(Method.GET, base_url + "/v4/groups/test$635363/bitlinks/clicks?unit=month&units=5");
    }*/

    @And("^I assert the invalid group_guid response with below values:$")
    public void iAssertTheInvalidGroup_guidResponseWithBelowValues(DataTable dataTable) {
        JSONObject jsonObject = new JSONObject(response.getBody().asString());
        Assert.assertEquals(jsonObject.get("message"), "FORBIDDEN");
        Assert.assertEquals(jsonObject.get("resource"), "bitlinks");
    }

    @Given("^I set end point url to null group_guid to Retrieve Sorted Bitlinks$")
    public void iSetEndPointUrlToNullGroup_guidToRetrieveSortedBitlinks() {
        RequestSpecification httprequest = RestAssured.given();
        httprequest.header("Content-Type", "application/json");
        httprequest.header("Authorization", "Bearer " + access_token);
        response = httprequest.request(Method.GET, base_url + "/v4/groups/null/bitlinks/clicks?unit=month&units=5");
    }

    @Given("^I set end point url to \"([^\"]*)\" group_guid to Retrieve Sorted Bitlinks$")
    public void iSetEndPointUrlToGroup_guidToRetrieveSortedBitlinks(String group_guid) throws Throwable {
        RequestSpecification httprequest = RestAssured.given();
        httprequest.header("Content-Type", "application/json");
        httprequest.header("Authorization", "Bearer " + access_token);
        //System.out.println("/v4/groups/" + group_guid + "/bitlinks/clicks?unit=month&units=5");
        response = httprequest.request(Method.GET, base_url + "/v4/groups/" + group_guid + "/bitlinks/clicks?unit=month&units=5");
    }

    @Given("^I set GET end point url with invalid values in sorting$")
    public void iSetGETEndPointUrlToInvalidValuesInSorting() {
        RequestSpecification httprequest = RestAssured.given();
        httprequest.header("Content-Type", "application/json");
        httprequest.header("Authorization", "Bearer " + access_token);
        response = httprequest.request(Method.GET, base_url + "/v4/groups/Bm4166jJLxX/bitlinks/clicks?unit=test232&units=25");
    }

    @And("^I assert the response of invalid values in sorting with below values:$")
    public void iAssertTheResponseOfInvalidValuesInSortingWithBelowValues(DataTable dataTable) {
        JSONObject jsonObject = new JSONObject(response.getBody().asString());
        Assert.assertEquals(jsonObject.get("message"), "INVALID_ARG_UNIT");
        Assert.assertEquals(jsonObject.get("resource"), "bitlinks");
    }

    @Given("^I POST with request_options and save as response$")
    public void iPOSTWithRequest_optionsAndSaveAsResponse() {
        String requestParams = "{\n" +
                "  \"domain\": \"bit.ly\",  \n" +
                "  \"title\": \"Bitly API Documentation\",  \n" +
                "  \"group_guid\": \"Bm4166jJLxX\",  \n" +
                "  \"long_url\": \"https://dev.bitly.com\"  \n" +
                "}";

        RequestSpecification httprequest = RestAssured.given();
        httprequest.header("Content-Type", "application/json");
        httprequest.header("Authorization", "Bearer " + access_token);
        response = httprequest.body(requestParams).post(base_url + "/v4/bitlinks");
        //System.out.println("POST Response Received :\n" + response.getBody().asString());

        /*Map<String,Object> requestParams = new LinkedHashMap<String, Object>();
        requestParams.put("domain","bit.ly");
        requestParams.put("title","Bitly API Documentation");
        requestParams.put("group_guid","Bm4166jJLxX");
        requestParams.put("long_url","https://dev.bitly.com");*/
    }

    @And("^I assert the response of Created Bitlink below values:$")
    public void iAssertTheResponseOfCreatedBitlinkBelowValues(DataTable dataTable) {
        JSONObject jsonObject = new JSONObject(response.getBody().asString());
        Assert.assertEquals(jsonObject.get("title"), "Bitly API Documentation");
    }

    @When("^I POST request body without \"([^\"]*)\" attribute to create Bitlink$")
    public void iPOSTRequestBodyWithoutAttributeToCreateBitlink(String request_attr) {
        //System.out.println(request_attr);
        RequestSpecification httprequest = RestAssured.given();
        httprequest.header("Content-Type", "application/json");
        httprequest.header("Authorization", "Bearer " + access_token);
        if (request_attr == "long_url") {
            String requestParams = "{\n" +
                    "  \"domain\": \"bit.ly\",  \n" +
                    "  \"title\": \"Bitly API Documentation\",  \n" +
                    "  \"group_guid\": \"Bm4166jJLxX\"\n" +
                    "  \n" +
                    "}";
            response = httprequest.body(requestParams).post(base_url + "/v4/bitlinks");
        }
        if (request_attr == "group_guid") {
            String requestParams = "{\n" +
                    "    \"domain\": \"bit.ly\",\n" +
                    "    \"title\": \"Bitly API Documentation\",\n" +
                    "    \"long_url\": \"https://dev.bitly.com\"\n" +
                    "}";
            response = httprequest.body(requestParams).post(base_url + "/v4/bitlinks");
        }
        if (request_attr == "deeplinks") {
            String requestParams = "{\n" +
                    "    \"domain\": \"bit.ly\",\n" +
                    "    \"title\": \"Bitly API Documentation\",\n" +
                    "    \"long_url\": \"https://dev.bitly.com\"\n" +
                    "}";
            response = httprequest.body(requestParams).post(base_url + "/v4/bitlinks");
        }
    }

    @When("^I POST request body with junk values/special characters in group_guid and long_url attribute$")
    public void iPOSTRequestBodyWithJunkValuesSpecialCharactersInGroup_guidAndLong_urlAttribute() {
        String requestParams = "{\n" +
                "    \"domain\": \"bit.ly\",\n" +
                "    \"title\": \"Bitly API Documentation\",\n" +
                "    \"group_guid\": \"xyz$dummy\",\n" +
                "    \"long_url\": \"test@1331\"\n" +
                "}";
        RequestSpecification httprequest = RestAssured.given();
        httprequest.header("Content-Type", "application/json");
        httprequest.header("Authorization", "Bearer " + access_token);
        response = httprequest.body(requestParams).post(base_url + "/v4/bitlinks");
    }
}
