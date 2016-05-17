package com.bowen;

import com.bowen.model.CarInfo;
import org.asynchttpclient.*;
import org.asynchttpclient.proxy.ProxyServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

/**
 * Created by bcl on 16/5/9.
 */

public class Application implements CommandLineRunner {
    @Autowired
    private MongoTemplate mongoTemplate;
    Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }

    @Override
    public void run(String... args) throws Exception {
        List<CarInfo> carInfoList = mongoTemplate.findAll(CarInfo.class,"car_info");
        AsyncHttpClientConfig cf = new DefaultAsyncHttpClientConfig.Builder()
                .setProxyServer(new ProxyServer.Builder("127.0.0.1", 8888)).build();

        AsyncHttpClient asyncHttpClient = new DefaultAsyncHttpClient(cf);
        carInfoList.forEach(car-> {
            logger.info(car.LicenseNo+" start");
            BoundRequestBuilder builder = asyncHttpClient.preparePost("http://192.168.218.50:8080/CSP/sinosig/default_price");
            builder.addFormParam("city","北京市");
            builder.addFormParam("license",car.LicenseNo);
            builder.addFormParam("name",car.LicenseOwner);
            builder.addFormParam("idCode",car.CredentislasNum);
            builder.addFormParam("frameNo",car.CarVin);
            builder.addFormParam("engineNo",car.EngineNo);
            builder.addFormParam("phone","15801303167");
            builder.execute(new AsyncCompletionHandler<Response>(){

                @Override
                public Response onCompleted(Response response) throws Exception{
                    logger.info(car.LicenseNo+" end");
                    logger.info(response.getResponseBody());
                    return response;
                }

                @Override
                public void onThrowable(Throwable t){
                    logger.error(t.getMessage(),t);
                }
            });

        });
    }
}
