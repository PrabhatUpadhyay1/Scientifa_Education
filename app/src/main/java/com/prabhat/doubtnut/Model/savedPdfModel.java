package com.prabhat.doubtnut.Model;

import java.util.List;

public class savedPdfModel {

    private List<String> PdfTittle;
    private List<String> savedPdf;


    public List<String> getPdfTittle() {
        return PdfTittle;
    }

    public List<String> getSavedPdf() {
        return savedPdf;
    }

    public savedPdfModel(List<String> pdfTittle, List<String> savedPdf) {
        PdfTittle = pdfTittle;
        this.savedPdf = savedPdf;
    }

    public savedPdfModel() {

    }
}
