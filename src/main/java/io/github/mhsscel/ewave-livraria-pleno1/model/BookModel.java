package io.github.mhsscel.bookjavaapi.model;

import io.github.mhsscel.bookjavaapi.dto.model.book.BookDTO;
import lombok.*;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TBL_BOOKS")
public class BookModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long book_id;

    @Column(name = "title")
    private String title;

    @Column(name = "genre")
    private String genre;

    @Column(name = "author")
    private String author;

    @Column(name = "isEnabled")
    private Boolean enabled;

    public BookDTO convertEntityToDTO() {
        return new ModelMapper().map(this, BookDTO.class);
    }
}
