package com.oracle.adw.controller;

import java.util.ArrayList;
import java.util.List;

import com.oracle.adw.repository.entity.Estate_Real_Trx_Apt;
import com.oracle.adw.service.jpa.RealEstateTradeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class RealEstateTradeController {

	@Autowired
    RealEstateTradeService realEstateTradeService;

    @RequestMapping(value = "/aptTrade", method = RequestMethod.GET)
    public String getAllSampleJpa() {

        List<Estate_Real_Trx_Apt> estate_Real_Trx_Apt_list = new ArrayList<Estate_Real_Trx_Apt>();
        
        Estate_Real_Trx_Apt estate_Real_Trx_Apt = new Estate_Real_Trx_Apt();
        estate_Real_Trx_Apt.set거래금액("1,000");
        estate_Real_Trx_Apt.set계약월("201903");
        estate_Real_Trx_Apt.set지역코드("1000");
        estate_Real_Trx_Apt.set건물명("건물명");

        estate_Real_Trx_Apt_list.add(estate_Real_Trx_Apt);
        
        realEstateTradeService.save(estate_Real_Trx_Apt);
        //realEstateTradeService.saveAll(estate_Real_Trx_Apt_list);

        return "Success..";
    }
    
	public static void main(String[] args) {
		SpringApplication.run(SampleController.class, args);
	}

}