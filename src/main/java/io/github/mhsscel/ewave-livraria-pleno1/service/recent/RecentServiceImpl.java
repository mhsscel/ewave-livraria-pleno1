package io.github.mhsscel.bookjavaapi.service.recent;

import io.github.mhsscel.bookjavaapi.dto.model.book.BookRecentDTO;
import io.github.mhsscel.bookjavaapi.model.BookModel;
import io.github.mhsscel.bookjavaapi.service.rating.RatingServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecentServiceImpl implements io.github.mhsscel.bookjavaapi.service.recent.RecentService
{
  private final RecentRepository recentRepository;
  private final RatingServiceImpl ratingRepository;

  public RecentServiceImpl(RecentRepository recentRepository, RatingServiceImpl ratingRepository)
  {
    this.recentRepository = recentRepository;
    this.ratingRepository = ratingRepository;
  }

  @Override
  public void addToRecentList(Long userId, Long bookId){
    if (!recentRepository.containsBook(bookId)) {
      recentRepository.lastRed(userId, bookId);
    }
  }

  @Override
  public List<BookRecentDTO> findRecentRed(Long userId){
    List<BookModel> recent = recentRepository.findLastRed(userId);
    List<BookRecentDTO> dtoBooks = new ArrayList<>();
    for (BookModel book : recent) {

      BookRecentDTO bookDto = new BookRecentDTO()
          .setId(book.getId())
          .setTitle(book.getTitle())
          .setAuthor(book.getAuthor())
          .setGenre(book.getGenre())
          .setRating(ratingRepository.getAveRating(book.getId()))
          .setDate(recentRepository.findReadDate(userId, book.getId()));
      dtoBooks.add(bookDto);
    }
    return dtoBooks;
  }

  @Override
  public void deleteBookFromRecent(Long bookId)
  {
    if(recentRepository.containsBook(bookId)){
      recentRepository.deleteBook(bookId);
    }
  }
}
