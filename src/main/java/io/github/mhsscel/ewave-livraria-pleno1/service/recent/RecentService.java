package io.github.mhsscel.bookjavaapi.service.recent;

import io.github.mhsscel.bookjavaapi.dto.model.book.BookRecentDTO;

import java.util.List;

public interface RecentService
{

  void addToRecentList(Long userId, Long bookId);

  List<BookRecentDTO> findRecentRed(Long userId);

  void deleteBookFromRecent(Long bookId);

}
