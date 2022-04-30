package com.demo.dynamodb.config;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.ConversionSchemas;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig.TableNameOverride;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverterFactory;
import lombok.RequiredArgsConstructor;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@RequiredArgsConstructor
@EnableDynamoDBRepositories(
        dynamoDBMapperConfigRef = "customerDynamoDBMapperConfig",
        basePackages = "com.demo.dynamodb.repository")
public class DynamoDbConfig {

    @Value("${spring.profiles.active}")
    private String profile;

    @Bean
    public AWSCredentialsProvider awsCredentialsProvider() {
        return new DefaultAWSCredentialsProviderChain();
    }

//    @Bean(name = "amazonDynamoDB")
//    public AmazonDynamoDB amazonDynamoDb() {
//        return AmazonDynamoDBClientBuilder.standard()
//                .withCredentials(credentialsProvider)
//                .withRegion(region)
//                .build();
//    }

    @Bean(name = "amazonDynamoDB")
    public AmazonDynamoDB amazonDynamoDb(AWSCredentialsProvider awsCredentialsProvider) {
        AmazonDynamoDB amazonDynamoDb = AmazonDynamoDBClientBuilder.standard()
                .withCredentials(awsCredentialsProvider)
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "ap-northeast-2"))
                .build();
        return amazonDynamoDb;
    }

    @Bean
    public TableNameOverride tableNameOverride() {
        return TableNameOverride.withTableNamePrefix(profile + "-");
    }

    @Bean(name = "customerDynamoDBMapperConfig")
    @Primary
    public DynamoDBMapperConfig dynamoDBMapperConfig(TableNameOverride tableNameOverride) {
        // DynamoDBMapperConfig DEFAULT 참고
        DynamoDBMapperConfig dynamoDBMapperConfig = DynamoDBMapperConfig.builder()
                .withSaveBehavior(DynamoDBMapperConfig.SaveBehavior.UPDATE)
                .withConsistentReads(DynamoDBMapperConfig.ConsistentReads.EVENTUAL)
                .withPaginationLoadingStrategy(DynamoDBMapperConfig.PaginationLoadingStrategy.LAZY_LOADING)
                .withTableNameResolver(DynamoDBMapperConfig.DefaultTableNameResolver.INSTANCE)
                .withBatchWriteRetryStrategy(DynamoDBMapperConfig.DefaultBatchWriteRetryStrategy.INSTANCE)
                .withBatchLoadRetryStrategy(DynamoDBMapperConfig.DefaultBatchLoadRetryStrategy.INSTANCE)
                .withTypeConverterFactory(DynamoDBTypeConverterFactory.standard())
                .withConversionSchema(ConversionSchemas.V2_COMPATIBLE)
                .withTableNameOverride(tableNameOverride)
                .build();
        return dynamoDBMapperConfig;
    }

}
