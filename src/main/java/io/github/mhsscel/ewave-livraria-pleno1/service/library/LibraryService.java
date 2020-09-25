package io.github.mhsscel.bookjavaapi.service.library;

import io.github.mhsscel.bookjavaapi.dto.model.book.BookDTO;
import io.github.mhsscel.bookjavaapi.model.UserModel;

import java.util.List;

public interface LibraryService
{
  void addBookToLibrary(Long user_id, Long book_id);

  void deleteBookFromLibrary(Long user_id, Long book_id);

  List<BookDTO> findAllBooks(UserModel user, String orderCriteria);

  Integer numberOfLibraries(Long bookId);

  Boolean isInLibrary(Long userId, Long bookId);
}
