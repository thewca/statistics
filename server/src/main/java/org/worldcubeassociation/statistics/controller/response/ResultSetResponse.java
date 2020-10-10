package org.worldcubeassociation.statistics.controller.response;

import java.util.List;

import lombok.Data;

@Data
public class ResultSetResponse {
	// Name of the columns
	private List<String> headers;

	private List<List<String>> content;

}
