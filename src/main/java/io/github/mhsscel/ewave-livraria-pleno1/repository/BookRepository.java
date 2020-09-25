package io.github.mhsscel.bookjavaapi.repository;


import io.github.mhsscel.bookjavaapi.model.BookModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookModel, Long> {

}
