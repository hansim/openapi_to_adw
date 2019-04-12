package com.oracle.adw.batch.RealEstateTrade;

import java.util.List;

import javax.sql.DataSource;

import com.oracle.adw.repository.entity.Estate_Real_Trx_Apt;
import com.oracle.adw.repository.jpa.AddressSiGuJpaRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableBatchProcessing
public class BatchJobConfig {

    public static final int CHUNK_AND_PAGE_SIZE = 100;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    AddressSiGuJpaRepository addressSiGuJpaRepository;

    @Value("${open.api.public.data.servicekey}")
    private String serviceKey;

    @Value("${open.api.public.data.apttrade.url}")
    private String resturl;

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    private static final Logger log = LoggerFactory.getLogger(AptTradeSvcToADWTask.class);

    @Bean
    @Primary
    public JpaTransactionManager jpaTransactionManager() {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setDataSource(dataSource);
        return transactionManager;
    }
    
    @Bean(name = "realEstateTradeJob")
    public Job simpleJob(@Qualifier("simpleStep1") Step simpleStep1) {
        return jobBuilderFactory.get("simpleJob").start(simpleStep1).build();
    }

    @Bean
    ItemReader<List<AptTradeDTO>> realEstateTradeReader() {
        return new RESTRealEstateTradeReader(restTemplate());
    }

    @Bean
    ItemProcessor<List<AptTradeDTO>, List<Estate_Real_Trx_Apt>> realEstateTradeProcessor() {
        return new RESTRealEstateTradeProcessor();
    }

    @Bean
    ItemWriter<List<Estate_Real_Trx_Apt>> realEstateTradeWriter() {
        return new RESTRealEstateTradeWriter();
    }
    
    @Bean
    public Step simpleStep1(JpaTransactionManager transactionManager
                           , ItemReader<List<AptTradeDTO>> realEstateTradeReader
                           , ItemProcessor<List<AptTradeDTO>, List<Estate_Real_Trx_Apt>> realEstateTradeProcessor
                           , ItemWriter<List<Estate_Real_Trx_Apt>> realEstateTradeWriter) {
        return stepBuilderFactory.get("realEstateTradeStep")
                .transactionManager(transactionManager)
                .<List<AptTradeDTO>, List<Estate_Real_Trx_Apt>>chunk(1)
                .reader(realEstateTradeReader)
                .processor(realEstateTradeProcessor)
                .writer(realEstateTradeWriter)
                .build();
    }
}