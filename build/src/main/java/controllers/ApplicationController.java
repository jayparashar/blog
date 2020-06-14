/**
 * Copyright (C) 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Copyright (C) 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package controllers;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import javax.naming.Context;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Singleton;

import models.Questions;
import ninja.Result;
import ninja.Results;

@Singleton
public class ApplicationController {

	
	public Result index() {

		return Results.html();

	}

	//	public Result helloWorldJson() {
	//
	//		SimplePojo simplePojo = new SimplePojo();
	//		simplePojo.content = "Hello World! Hello Json!";
	//
	//		return Results.json().render(simplePojo);
	//
	//	}

	public static class SimplePojo {

		public String content;

	}

	public Result random(Context context) throws JsonParseException, JsonMappingException, IOException {

		String json = jsonGetRequest("http://jservice.io/api/random");
		
		json = StringUtils.removeStart(StringUtils.removeEnd(json, "]"), "[");
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		Questions q = mapper.readValue(json, Questions.class);
		System.out.println(q.toString());

		Result result = Results.html();
		result.render("id", q.getId());
		result.render("question", q.getQuestion());
		result.render("answer", q.getAnswer());
		result.render("airdate", q.getAirdate());
		result.render("value", q.getValue());
		result.render("difficulty", q.getDifficulty());

		return result;

	}

	private static String streamToString(InputStream inputStream) {
		@SuppressWarnings("resource")
		String text = new Scanner(inputStream, "UTF-8").useDelimiter("\\Z").next();
		return text;
	}

	public static String jsonGetRequest(String urlQueryString) {
		String json = null;
		try {
			URL url = new URL(urlQueryString);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("charset", "utf-8");
			connection.connect();
			InputStream inStream = connection.getInputStream();
			json = streamToString(inStream); // input stream to string
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return json;
	}
}
