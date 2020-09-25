package io.github.mhsscel.bookjavaapi.service.library;

import io.github.mhsscel.bookjavaapi.dto.model.book.BookDTO;
import io.github.mhsscel.bookjavaapi.model.BookModel;
import io.github.mhsscel.bookjavaapi.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Service
public class LibraryServiceImpl implements io.github.mhsscel.bookjavaapi.service.library.LibraryService {
  private final LibraryRepository libraryRepository;
  private final RatingRepository  ratingRepository;

  @Autowired
  public LibraryServiceImpl(LibraryRepository libraryRepository, RatingRepository ratingRepository)
  {
    this.libraryRepository = libraryRepository;
    this.ratingRepository = ratingRepository;
  }

  @Override
  public void addBookToLibrary(Long user_id, Long book_id)
  {
    libraryRepository.addBookToLibrary(user_id, book_id);
  }

  @Override
  public void deleteBookFromLibrary(Long user_id, Long book_id)
  {
    libraryRepository.deleteBookFromLibrary(user_id, book_id);
  }

  @Override
  public List<BookDTO> findAllBooks(UserModel user, String orderCriteria)
  {
    List<BookModel> userBooks = libraryRepository.findAllBooksInLibrary(user.getUserId(), orderCriteria);
    List<BookDTO> userDtoBooks = new ArrayList<>();

    if (!userBooks.isEmpty()) {

      for (BookModel book : userBooks) {

        String Url;
        if (book.getEnabled().equals(true)) {
          Url = ServletUriComponentsBuilder.fromCurrentContextPath()
              .path("/api/v1/library/download/") /// check this
              .path(book.getTitle()).toUriString();
        }
        else {
          Url = "N/A for download";
        }

        /*BookDTO bookDTO = new BookDTO()
            .setId(book.getBook_id())
            .setAuthor(book.getAuthor())
            .setGenre(book.getGenre())
            .setRating(ratingRepository.getAveRating(book.getId()))
            .setTitle(book.getTitle())
            .setUrl(Url);*/
        //userDtoBooks.add(bookDTO);
      }
    }

    return userDtoBooks;
  }

  @Override
  public Integer numberOfLibraries(Long bookId)
  {
    return libraryRepository.numberOfLibraries(bookId);
  }


  @Override
  public Boolean isInLibrary(Long userId, Long bookId)
  {
    return libraryRepository.isInLibrary(userId, bookId);
  }


}
