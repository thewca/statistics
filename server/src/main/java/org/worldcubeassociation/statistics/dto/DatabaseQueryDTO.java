package org.worldcubeassociation.statistics.dto;

import java.util.List;

import lombok.Data;

@Data
public class DatabaseQueryDTO {
	private List<String> headers; 	// Column names
	private List<List<String>> content;

	// Based on spring Page
	private int number; // Current page
	private int numberOfElements; // Elements in the current page
	private int size; // Current page size
	private int totalElements; // Total count
	private int totalPages;
	private boolean hasContent;
	private boolean hasNextPage;
	private boolean hasPreviousPage;
	private boolean firstPage;
	private boolean lastPage;
}
