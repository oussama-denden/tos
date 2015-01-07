package com.nordnet.opale.finder.swagger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;

/**
 * Classe configuration de swagger.
 * 
 * @author akram-moncer
 * 
 */
@Configuration
@EnableSwagger
public class SwaggerConfig {

	/**
	 * {@link SpringSwaggerConfig}.
	 */
	private SpringSwaggerConfig springSwaggerConfig;

	/**
	 * recuperation du {@link SpringSwaggerConfig} du context.
	 * 
	 * @param springSwaggerConfig
	 *            {@link SpringSwaggerConfig}.
	 */
	@Autowired
	public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig) {
		this.springSwaggerConfig = springSwaggerConfig;
	}

	/**
	 * creation de l'objet {@link SwaggerSpringMvcPlugin} en lui associant l {@link ApiInfo}.
	 * 
	 * @return {@link SwaggerSpringMvcPlugin}.
	 */
	@Bean
	public SwaggerSpringMvcPlugin customImplementation() {
		return new SwaggerSpringMvcPlugin(this.springSwaggerConfig).apiInfo(apiInfo());
	}

	/**
	 * creation des information de l'api.
	 * 
	 * @return {@link ApiInfo}.
	 */
	private ApiInfo apiInfo() {
		ApiInfo apiInfo = new ApiInfo("Opale Finder API", "Liste des services Opale-Finder", null, null, null, null);
		return apiInfo;
	}

}
