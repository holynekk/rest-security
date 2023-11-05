package com.holynekk.apisecurity.api.response.dos;

import com.holynekk.apisecurity.entity.HexColor;

import java.util.List;

public class HexColorPaginationResponse {

    private List<HexColor> colors;

    private int currentPage;

    private int sizes;

    private int totalPages;

    public List<HexColor> getColors() {
        return colors;
    }

    public void setColors(List<HexColor> colors) {
        this.colors = colors;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getSizes() {
        return sizes;
    }

    public void setSizes(int sizes) {
        this.sizes = sizes;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
