package io.github.mhsscel.bookjavaapi.service.book;

import io.github.mhsscel.bookjavaapi.dto.model.book.BookNoUrlDTO;
import io.github.mhsscel.bookjavaapi.dto.model.book.BookWithStatusDTO;
import io.github.mhsscel.bookjavaapi.model.BookModel;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        return null;
    }

    @Override
    public BookNoUrlDTO saveBook(String title, String genre, String author) {
        return null;
    }

    @Override
    public Resource download(String fileName) throws MalformedURLException {
        return null;
    }

    @Override
    public void deleteBook(Long id) throws IOException {

    }

    @Override
    public List<BookWithStatusDTO> findAllBooks(String title, String author) {
        return null;
    }

    @Override
    public List<BookNoUrlDTO> findEnabledBooks(String title, String author) {
        return null;
    }

    @Override
    public Optional<BookModel> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<BookModel> findByTitle(String title) {
        return Optional.empty();
    }

    @Override
    public void updateStatus(Long id, Boolean enable) {

    }
}
