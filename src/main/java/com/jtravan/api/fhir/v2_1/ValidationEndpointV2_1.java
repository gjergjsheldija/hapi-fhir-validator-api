package com.jtravan.api.fhir.v2_1;

import ca.uhn.fhir.context.FhirVersionEnum;
import com.jtravan.api.fhir.model.ValidationResponse;
import com.jtravan.exceptions.UnhandledFhirVersionException;
import com.jtravan.model.FhirMediaType;
import com.jtravan.services.ValidationService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Rest endpoints for the FHIR version 2.1 validation
 */
@RestController
@RequestMapping(
        value = "/fhir/v2_1",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = {FhirMediaType.APPLICATION_JSON_VALUE, FhirMediaType.APPLICATION_FHIR_JSON_VALUE})
@Api(tags = {"FHIR v2.1 (or 1.4.0)"})
public class ValidationEndpointV2_1 {

    private final ValidationService validationService;

    @Autowired
    public ValidationEndpointV2_1(ValidationService validationService) {
        this.validationService = validationService;
    }

    @PostMapping(value = "/validate")
    @ApiOperation(value = "Validates a FHIR object against the v2.1 schemas", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "The resource was not found"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 422, message = "Cannot process FHIR JSON")
    }
    )
    public ValidationResponse validate(@ApiParam(name = "fhirJson", value = "FHIR JSON object to be validated", required = true)
                                       @RequestBody Object fhirJson) throws IOException, UnhandledFhirVersionException {
        return validationService.validate(fhirJson, FhirVersionEnum.DSTU2_1);
    }

}
