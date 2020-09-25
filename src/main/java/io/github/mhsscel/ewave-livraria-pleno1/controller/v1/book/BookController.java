package io.github.mhsscel.bookjavaapi.controller.v1.book;

import io.github.mhsscel.bookjavaapi.dto.model.book.BookNoUrlDTO;
import io.github.mhsscel.bookjavaapi.dto.model.book.BookWithStatusDTO;
import io.github.mhsscel.bookjavaapi.model.BookModel;
import io.github.mhsscel.bookjavaapi.model.UserModel;
import io.github.mhsscel.bookjavaapi.service.book.BookServiceImpl;
import io.github.mhsscel.bookjavaapi.service.rating.RatingServiceImpl;
import io.github.mhsscel.bookjavaapi.service.user.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/book")
public class BookController
{

  private final BookServiceImpl   bookService;
  private final UserServiceImpl   userService;
  private final RatingServiceImpl ratingService;

  @Autowired
  public BookController(BookServiceImpl bookService,
                        UserServiceImpl userService,
                        RatingServiceImpl ratingService)
  {
    this.bookService = bookService;
    this.userService = userService;
    this.ratingService = ratingService;
  }

  @PostMapping()
  public ResponseEntity<?> addBook(@RequestParam(value = "file", required = true) MultipartFile file,
                                   @RequestParam(value = "genre", required = true) String genre,
                                   Principal p) throws IOException

  {
    Optional<UserModel> currentUser = userService.findUserByUsername(p.getName());
    String fileName = bookService.uploadFile(file);

    if (currentUser.isPresent() && currentUser.get().getEnabled() == true && !bookService.findByTitle(fileName).isPresent()) {

      BookNoUrlDTO bookDto = bookService.saveBook(fileName, genre, currentUser.get().getName());
      BookModel b1 = bookService.findByTitle(fileName).get();
      //ratingService.rate(currentUser.get().getUserId(), b1.getId(), 0.0);
      return ResponseEntity.ok(bookDto);

    }
    return ResponseEntity.badRequest().body("This book already exists!");
  }

  @DeleteMapping("/{id}/remove")
  public ResponseEntity<?> deleteBook(@PathVariable Long id) throws IOException
  {
    if (bookService.findById(id).isPresent()) {
      bookService.deleteBook(id);
      return ResponseEntity.ok("Book was deleted");
    }
    return ResponseEntity.badRequest().body("Your account is temporary disabled");
  }

  @GetMapping()
  public ResponseEntity<?> findAll(@RequestParam(value = "title", required = false, defaultValue = "")
                                       String title,
                                   @RequestParam(value = "author", required = false, defaultValue = "")
                                       String author,
                                   Principal p)
  {
    Optional<UserModel> currentUser = userService.findUserByUsername(p.getName());
    if (currentUser.isPresent() && currentUser.get().getEnabled() == true) {
      if (currentUser.get().getRole().equalsIgnoreCase("AUTHOR")) {
        List<BookWithStatusDTO> allBooks = bookService.findAllBooks(title, author);
        if (!allBooks.isEmpty()) {
          return ResponseEntity.ok(allBooks);
        }
      }
      if (currentUser.get().getRole().equalsIgnoreCase("READER")) {
        List<BookNoUrlDTO> allBooks = bookService.findEnabledBooks(title, author);
        if (!allBooks.isEmpty()) {
          return ResponseEntity.ok(allBooks);
        }
      }
      return ResponseEntity.badRequest().body("No books were found.");
    }
    return ResponseEntity.badRequest().body("Your account is temporary disabled");
  }

  @PatchMapping("/{bookId}/enable")
  public ResponseEntity<?> enableBook(@PathVariable(value = "bookId", required = true) Long bookId,
                                      @RequestParam(value = "enable", required = true) Boolean enable,
                                      Principal principal)
  {
    Optional<UserModel> currentUser = userService.findUserByUsername(principal.getName());
    if (currentUser.isPresent() && currentUser.get().getEnabled() == true) {
      Optional<BookModel> book = bookService.findById(bookId);

      //check if the book is added by te current user
      if (book.isPresent() && book.get().getAuthor().equals(currentUser.get().getName())) {
        bookService.updateStatus(bookId, enable);
        return ResponseEntity.ok("Enable book set to: " + enable);
      }
      return ResponseEntity.badRequest().body("This book is added by another user!");
    }
    return ResponseEntity.badRequest().build();
  }

}


