package com.example.sharerecipe.dto;

import java.util.List;

public class PageResponse<T> {
	private List<T> data;
	private PaginationMeta meta;

	public PageResponse(List<T> data, PaginationMeta meta) {
		this.data = data;
		this.meta = meta;
	}

	public List<T> getData() {
		return data;
	}

	public PaginationMeta getMeta() {
		return meta;
	}
}
