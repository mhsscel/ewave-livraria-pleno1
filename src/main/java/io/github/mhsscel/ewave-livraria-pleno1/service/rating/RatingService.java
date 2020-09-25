package io.github.mhsscel.bookjavaapi.service.rating;

import java.util.Optional;

public interface RatingService
{
  Double getAveRating(Long book_id);

  void rate(Long user_id, Long book_id, Double rating);

  Optional<Double> getYourRating(Long user_id, Long book_id);

  void deleteRating(Long bookId); // used for testing
}

