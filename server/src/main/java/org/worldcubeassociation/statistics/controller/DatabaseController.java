package org.worldcubeassociation.statistics.controller;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.worldcubeassociation.statistics.service.DatabaseService;

@RestController
@RequestMapping("database")
public class DatabaseController {
	
	@Autowired
	private DatabaseService databaseService;
	
	@GetMapping("query")
	public List<LinkedHashMap<String, String>> getResultSet(String sqlQuery){
		return databaseService.getResultSet(sqlQuery);
	}

}
