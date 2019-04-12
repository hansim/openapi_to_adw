package com.oracle.adw.controller;

import java.util.ArrayList;
import java.util.List;

import com.oracle.adw.repository.entity.Sample_Han;
import com.oracle.adw.service.jdbc.SampleJdbcService;
import com.oracle.adw.service.jpa.SampleJpaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class SampleController {

	@Autowired
    SampleJpaService sampleJpaService;

    @Autowired
    SampleJdbcService sampleJdbcService;

    @RequestMapping(value = "/sample1", method = RequestMethod.GET)
    public Iterable<Sample_Han> getAllSampleJpa() {
        return sampleJpaService.getAllSample();
    }

    @RequestMapping(value = "/sample2", method = RequestMethod.GET)
    public String saveall() {

        List<Sample_Han> sample_han_list = new ArrayList<Sample_Han>();
        Sample_Han sample = new Sample_Han();
        sample.set아이디(1);
        sample.set이름("이름1");
        sample_han_list.add(sample);

        sample.set아이디(2);
        sample.set이름("이름2");
        sample_han_list.add(sample);

        sample.set아이디(3);
        sample.set이름("이름3");
        sample_han_list.add(sample);

        return sampleJpaService.saveAll(sample_han_list);
    }

    @RequestMapping(value = "/sample3", method = RequestMethod.GET)
    public String save() {

        Sample_Han sample = new Sample_Han();
        sample.set아이디(4);
        sample.set이름("이름4");

        return sampleJpaService.save(sample);
    }

	public static void main(String[] args) {
		SpringApplication.run(SampleController.class, args);
	}
}