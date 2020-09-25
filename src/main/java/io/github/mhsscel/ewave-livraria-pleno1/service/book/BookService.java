package io.github.mhsscel.bookjavaapi.service.book;

import io.github.mhsscel.bookjavaapi.dto.model.book.BookNoUrlDTO;
import io.github.mhsscel.bookjavaapi.dto.model.book.BookWithStatusDTO;
import io.github.mhsscel.bookjavaapi.model.BookModel;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;

public interface BookService {
  String uploadFile(MultipartFile file) throws IOException;

  BookNoUrlDTO saveBook(String title, String genre, String author);

  Resource download(String fileName) throws MalformedURLException;

  void deleteBook(Long id) throws IOException;

  List<BookWithStatusDTO> findAllBooks(String title, String author);

  List<BookNoUrlDTO> findEnabledBooks(String title, String author);

  Optional<BookModel> findById(Long id);

  Optional<BookModel> findByTitle(String title);

  void updateStatus(Long id, Boolean enable);
}
