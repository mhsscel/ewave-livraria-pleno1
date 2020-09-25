package io.github.mhsscel.bookjavaapi.controller.v1.library;

import io.github.mhsscel.bookjavaapi.dto.model.book.BookDTO;
import io.github.mhsscel.bookjavaapi.dto.model.book.BookRecentDTO;
import io.github.mhsscel.bookjavaapi.model.BookModel;
import io.github.mhsscel.bookjavaapi.model.UserModel;
import io.github.mhsscel.bookjavaapi.service.book.BookService;
import io.github.mhsscel.bookjavaapi.service.library.LibraryService;
import io.github.mhsscel.bookjavaapi.service.rating.RatingService;
import io.github.mhsscel.bookjavaapi.service.recent.RecentService;
import io.github.mhsscel.bookjavaapi.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/library")
public class LibraryController {

    private final UserService userService;
    private final BookService bookService;
    private final LibraryService libraryService;
    private final RatingService ratingService;
    private final RecentService recentService;


    @Autowired
    public LibraryController(UserService userService,
                             BookService bookService,
                             LibraryService libraryService,
                             RatingService ratingService,
                             RecentService recentService) {
        this.bookService = bookService;
        this.libraryService = libraryService;
        this.userService = userService;
        this.ratingService = ratingService;
        this.recentService = recentService;
    }

    @GetMapping()
    public ResponseEntity<?> getBooksFromUserLibrary(@RequestParam(
            value = "orderCriteria", required = false, defaultValue = " '' ")
                                                             String orderCriteria,
                                                     Principal p) {
        Optional<UserModel> currentUser = userService.findUserByUsername(p.getName());

        if (currentUser.isPresent() && currentUser.get().getEnabled() == true) {
            List<BookDTO> bookDTOList = libraryService.findAllBooks(currentUser.get(), orderCriteria);
            if (!bookDTOList.isEmpty()) {
                return ResponseEntity.ok(bookDTOList);
            }
            return ResponseEntity.ok("No books in this library.");  // was 400 changed it to 200
        }
        return ResponseEntity.badRequest().body("Your account is temporary disabled");
    }

    @PostMapping("/add")
    public ResponseEntity<?> addBookToUserLibrary(@RequestParam(value = "bookId") Long bookId,
                                                  Principal principal) {

        Optional<BookModel> bookToAdd = bookService.findById(bookId);
        Optional<UserModel> currentUser = userService.findUserByUsername(principal.getName());

        if (currentUser.isPresent() && currentUser.get().getEnabled() == true) {
            if (bookToAdd.isPresent()) {
                Long currentUserId = currentUser.get().getUserId();
                libraryService.addBookToLibrary(currentUserId, bookId);
                ratingService.rate(currentUserId, bookId, 0.0);
                return ResponseEntity.ok("Book added");
            }
            return ResponseEntity.badRequest().body("Book book is already in your library");
        }
        return ResponseEntity.badRequest().body("Your account is temporary disabled");
    }

    @DeleteMapping("/remove")
    public ResponseEntity<?> removeBookFromUserLibrary(@RequestParam(value = "bookId") Long bookId,
                                                       Principal principal) {

        Optional<UserModel> currentUser = userService.findUserByUsername(principal.getName());
        Optional<BookModel> bookToRemove = bookService.findById(bookId);

        if (currentUser.isPresent() && currentUser.get().getEnabled() && bookToRemove.isPresent()) {
            libraryService.deleteBookFromLibrary(currentUser.get().getUserId(), bookId);
            return ResponseEntity.ok("Book deleted");
        }
        return ResponseEntity.ok("Book not deleted");
    }

    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<?> downloadBook(@PathVariable String fileName,
                                          HttpServletRequest request,
                                          Principal p) throws IOException {
        Resource resource = bookService.download(fileName);
        Optional<BookModel> book = bookService.findByTitle(fileName);
        if (book.isPresent()) {
            if (book.get().getEnabled() == true) {
                String contentType = null;
                try {
                    contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
                } catch (IOException ex) {

                }
                if (contentType == null) {
                    contentType = "application/octet-stream";
                }
                Optional<UserModel> currentUser = userService.findUserByUsername(p.getName());
                if (currentUser.isPresent() && currentUser.get().getEnabled() == true) {
                    recentService.addToRecentList(currentUser.get().getUserId(),
                            book.get().getBook_id());

                    return ResponseEntity.ok()
                            .contentType(MediaType.parseMediaType(contentType))
                            .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                            .body(resource);
                }
                return ResponseEntity.badRequest().body("This book is temporary unavailable for read/download!");
            }
            return ResponseEntity.badRequest().body("This book is not present");
        }
        return ResponseEntity.badRequest().body("Bad book name");
    }


    @GetMapping("/numberOfLibraries")
    public ResponseEntity<?> numberOfLibraries(@RequestParam(value = "bookId") Long bookId) {
        /// check
        return ResponseEntity.ok(String.format("This book is added in %s libraries",
                libraryService.numberOfLibraries(bookId)));
    }

    @PatchMapping("/{bookId}/rate")
    public ResponseEntity<?> rateBook(@PathVariable(value = "bookId", required = true) Long bookId,
                                      @RequestParam(value = "rating", required = true, defaultValue = "0.0") Double rating,
                                      Principal p) {
        Optional<UserModel> currentUser = userService.findUserByUsername(p.getName());
        if (currentUser.isPresent() && currentUser.get().getEnabled() == true) {
            Long userId = currentUser.get().getUserId();
            Optional<BookModel> book = bookService.findById(bookId);
            if (book.isPresent()) {
                Optional<Double> yourRating = ratingService.getYourRating(userId, bookId);
                if (libraryService.isInLibrary(userId, bookId)) {
                    if (!yourRating.isPresent() || yourRating.get() == 0) {
                        ratingService.rate(userId, bookId, rating);
                        Double newRating = ratingService.getAveRating(bookId); // or  null !
                        return ResponseEntity.ok("New book rating is : " + newRating);
                    }
                    return ResponseEntity.badRequest().body("You already gave the book a score!");
                }
                return ResponseEntity.badRequest().body("This book is not in your library!");
            }
            return ResponseEntity.badRequest().body("Book not present!");
        }
        return ResponseEntity.badRequest().body("This user is temporary disabled!");
    }

    @GetMapping("/recent")
    public ResponseEntity<?> getRecentBooks() {
        return ResponseEntity.badRequest().body("Your account is temporary disabled");
    }
}
