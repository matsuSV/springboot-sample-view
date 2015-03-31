package springboot.sample.controller;

import org.apache.log4j.NDC;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import springboot.sample.service.HttpClient;

@Controller
@EnableAutoConfiguration
public class DataBaseInformationController {

	@RequestMapping("/dbinfo")
	public String dbinfo() {
		return "dbinfo";
	}

	@RequestMapping("/connect")
	@ResponseBody
	public boolean connect( @RequestParam("clazz")        String clazz,
							@RequestParam("url")          String url,
							@RequestParam("user")         String user,
							@RequestParam("password")     String password,
							@RequestParam("db2v10driver") boolean db2v10driver) {

		// set Unique ID
		long uid = System.currentTimeMillis();
		NDC.push(String.valueOf(uid)); // do you think it calls NDC.remove method ?
//		MDC.put("UserName", "querytester");

		HttpClient hc = new HttpClient();
		return hc.request(HttpClient.GET_REQUEST_METHOD, clazz, url, user, password, db2v10driver);
	}

	public static void main(String[] args) {
		SpringApplication.run(DataBaseInformationController.class, args);
	}
}
