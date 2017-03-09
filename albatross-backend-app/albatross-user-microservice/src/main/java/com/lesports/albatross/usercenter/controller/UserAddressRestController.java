package com.lesports.albatross.usercenter.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lesports.albatross.commons.util.ApiResponseEntityUtils;
import com.lesports.albatross.commons.web.domain.ResponseWrapper;
import com.lesports.albatross.commons.web.versioning.ApiVersion;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "user addresses", tags = "user addresses")
@RestController
@ApiVersion("1")
public class UserAddressRestController {



    @ApiOperation(value = "testAddresses", notes = "getAddresses")
    @RequestMapping(value = "/addresses/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseWrapper<String>> findUserAddress(@PathVariable(value = "id") Long userAddressId) {

        return ApiResponseEntityUtils.ok(ResponseWrapper.ok("test addressess").build());
    }


}
