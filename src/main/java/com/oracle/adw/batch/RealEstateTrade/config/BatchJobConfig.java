package com.oracle.adw.batch.RealEstateTrade.config;

import java.util.List;

import javax.sql.DataSource;

import com.oracle.adw.batch.RealEstateTrade.dto.AptTradeDTO;
import com.oracle.adw.batch.RealEstateTrade.dto.HouseTradeDTO;
import com.oracle.adw.batch.RealEstateTrade.dto.MansionTradeDTO;
import com.oracle.adw.batch.RealEstateTrade.listener.RealEstateTradeJobListener;
import com.oracle.adw.batch.RealEstateTrade.listener.RealEstateTradeStepListener;
import com.oracle.adw.batch.RealEstateTrade.processor.RESTRealEstateAptTradeProcessor;
import com.oracle.adw.batch.RealEstateTrade.processor.RESTRealEstateHouseTradeProcessor;
import com.oracle.adw.batch.RealEstateTrade.processor.RESTRealEstateMansionTradeProcessor;
import com.oracle.adw.batch.RealEstateTrade.reader.RESTRealEstateAptTradeReader;
import com.oracle.adw.batch.RealEstateTrade.reader.RESTRealEstateHouseTradeReader;
import com.oracle.adw.batch.RealEstateTrade.reader.RESTRealEstateMansionTradeReader;
import com.oracle.adw.batch.RealEstateTrade.writer.RESTRealEstateAptTradeWriter;
import com.oracle.adw.batch.RealEstateTrade.writer.RESTRealEstateHouseTradeWriter;
import com.oracle.adw.batch.RealEstateTrade.writer.RESTRealEstateMansionTradeWriter;
import com.oracle.adw.repository.entity.Estate_Real_Apt_Trx;
import com.oracle.adw.repository.entity.Estate_Real_House_Trx;
import com.oracle.adw.repository.entity.Estate_Real_Mansion_Trx;
import com.oracle.adw.repository.jpa.AddressSiGuJpaRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
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
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
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

    @Autowired
    RealEstateTradeJobListener realEstateTradeJobListener;

    @Autowired
    RealEstateTradeStepListener realEstateTradeStepListener;

    @Value("${open.api.public.data.servicekey}")
    private String serviceKey;

    @Value("${open.api.public.data.apttrade.url}")
    private String resturl;

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    private static final Logger logger = LoggerFactory.getLogger(BatchJobConfig.class);

    @Bean
    @Primary
    public JpaTransactionManager jpaTransactionManager() {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setDataSource(dataSource);
        return transactionManager;
    }

    @Bean
    @StepScope
    ItemReader<List<AptTradeDTO>> realEstateAptTradeReader() {
        return new RESTRealEstateAptTradeReader(restTemplate());
    }

    @Bean
    @StepScope
    ItemReader<List<HouseTradeDTO>> realEstateHouseTradeReader() {
        return new RESTRealEstateHouseTradeReader(restTemplate());
    }

    @Bean
    @StepScope
    ItemReader<List<MansionTradeDTO>> realEstateMansionTradeReader() {
        return new RESTRealEstateMansionTradeReader(restTemplate());
    }

    @Bean
    @StepScope
    ItemProcessor<List<AptTradeDTO>, List<Estate_Real_Apt_Trx>> realEstateAptTradeProcessor() {
        return new RESTRealEstateAptTradeProcessor();
    }

    @Bean
    @StepScope
    ItemProcessor<List<HouseTradeDTO>, List<Estate_Real_House_Trx>> realEstateHouseTradeProcessor() {
        return new RESTRealEstateHouseTradeProcessor();
    }

    @Bean
    @StepScope
    ItemProcessor<List<MansionTradeDTO>, List<Estate_Real_Mansion_Trx>> realEstateMansionTradeProcessor() {
        return new RESTRealEstateMansionTradeProcessor();
    }

    @Bean
    @StepScope
    ItemWriter<List<Estate_Real_Apt_Trx>> realEstateAptTradeWriter() {
        return new RESTRealEstateAptTradeWriter();
    }

    @Bean
    @StepScope
    ItemWriter<List<Estate_Real_House_Trx>> realEstateHouseTradeWriter() {
        return new RESTRealEstateHouseTradeWriter();
    }

    @Bean
    @StepScope
    ItemWriter<List<Estate_Real_Mansion_Trx>> realEstateMansionTradeWriter() {
        return new RESTRealEstateMansionTradeWriter();
    }
    
    @Bean(name = "realEstateTradeJob")
    public Job realEstateTradeJob(@Qualifier("realEstateAptTradeStep") Step realEstateAptTradeStep, @Qualifier("realEstateHouseTradeStep") Step realEstateHouseTradeStep, @Qualifier("realEstateMansionTradeStep") Step realEstateMansionTradeStep) {

        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
		threadPoolTaskExecutor.setCorePoolSize(CHUNK_AND_PAGE_SIZE);
        threadPoolTaskExecutor.afterPropertiesSet();
        
        Flow splitFlow = new FlowBuilder<Flow>("realEstateTradeStepSplitFlow")
			.split(threadPoolTaskExecutor)
			.add(
				new FlowBuilder<Flow>("realEstateAptTradeStepFlow").start(realEstateAptTradeStep).build(),
                new FlowBuilder<Flow>("realEstateHouseTradeStepFlow").start(realEstateHouseTradeStep).build(),
                new FlowBuilder<Flow>("realEstateMansionTradeStepFlow").start(realEstateMansionTradeStep).build()
            ).build();
            
        return jobBuilderFactory.get("국토부 전국 부동산 실거래가 데이터 수집")
                                .listener(realEstateTradeJobListener)
                                .start(splitFlow)
                                .end()
                                .build();
    }
    
    @Bean
    public Step realEstateAptTradeStep(JpaTransactionManager transactionManager
                           , ItemReader<List<AptTradeDTO>> realEstateAptTradeReader
                           , ItemProcessor<List<AptTradeDTO>, List<Estate_Real_Apt_Trx>> realEstateAptTradeProcessor
                           , ItemWriter<List<Estate_Real_Apt_Trx>> realEstateAptTradeWriter) {
        return stepBuilderFactory.get("아파트매매 실거래자료 수집 스텝")
                .transactionManager(transactionManager)
                .<List<AptTradeDTO>, List<Estate_Real_Apt_Trx>>chunk(1)
                .reader(realEstateAptTradeReader)
                .processor(realEstateAptTradeProcessor)
                .writer(realEstateAptTradeWriter)
                .listener(realEstateTradeStepListener)
                .build();
    }

    @Bean
    public Step realEstateHouseTradeStep(JpaTransactionManager transactionManager
                           , ItemReader<List<HouseTradeDTO>> realEstateHouseTradeReader
                           , ItemProcessor<List<HouseTradeDTO>, List<Estate_Real_House_Trx>> realEstateHouseTradeProcessor
                           , ItemWriter<List<Estate_Real_House_Trx>> realEstateHouseTradeWriter) {
        return stepBuilderFactory.get("단독/가구 매매 실거래자료 수집 스텝")
                .transactionManager(transactionManager)
                .<List<HouseTradeDTO>, List<Estate_Real_House_Trx>>chunk(1)
                .reader(realEstateHouseTradeReader)
                .processor(realEstateHouseTradeProcessor)
                .writer(realEstateHouseTradeWriter)
                .listener(realEstateTradeStepListener)
                .build();
    }

    @Bean
    public Step realEstateMansionTradeStep(JpaTransactionManager transactionManager
                           , ItemReader<List<MansionTradeDTO>> realEstateMansionTradeReader
                           , ItemProcessor<List<MansionTradeDTO>, List<Estate_Real_Mansion_Trx>> realEstateMansionTradeProcessor
                           , ItemWriter<List<Estate_Real_Mansion_Trx>> realEstateMansionTradeWriter) {
        return stepBuilderFactory.get("연립다세대 매매 실거래자료 수집 스텝")
                .transactionManager(transactionManager)
                .<List<MansionTradeDTO>, List<Estate_Real_Mansion_Trx>>chunk(1)
                .reader(realEstateMansionTradeReader)
                .processor(realEstateMansionTradeProcessor)
                .writer(realEstateMansionTradeWriter)
                .listener(realEstateTradeStepListener)
                .build();
    }
}