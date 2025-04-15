package com.comnord.bqsm.services;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class WordDocumentService {

    public List<String> readWordDocument(InputStream inputStream) throws IOException {
        List<String> paragraphs = new ArrayList<>();
        try (XWPFDocument document = new XWPFDocument(inputStream)) {
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                paragraphs.add(paragraph.getText());
            }
        }
        return paragraphs;
    }
}
