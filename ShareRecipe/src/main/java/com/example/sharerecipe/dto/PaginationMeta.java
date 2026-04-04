package com.example.sharerecipe.dto;

public class PaginationMeta {
	private int page;
	private int pageSize;
	private long totalItems;
	private int totalPages;

	public PaginationMeta(int page, int pageSize, long totalItems, int totalPages) {
		this.page = page;
		this.pageSize = pageSize;
		this.totalItems = totalItems;
		this.totalPages = totalPages;
	}

	public int getPage() {
		return page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public long getTotalItems() {
		return totalItems;
	}

	public int getTotalPages() {
		return totalPages;
	}
}
